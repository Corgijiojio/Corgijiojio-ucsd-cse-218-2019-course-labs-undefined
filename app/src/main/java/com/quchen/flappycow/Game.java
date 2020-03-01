/**
 * The Game
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.quchen.flappycow.Util.MyHandler;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.behaviors.Movable;
import com.quchen.flappycow.behaviors.TapBehavior;
import com.quchen.flappycow.managers.AccessoryManager;
import com.quchen.flappycow.managers.AccomplishmentManager;
import com.quchen.flappycow.managers.CoinManager;
import com.quchen.flappycow.managers.CollisionManager;
import com.quchen.flappycow.managers.ItemGenerator;
import com.quchen.flappycow.managers.PointManager;
import com.quchen.flappycow.managers.SpriteEventManager;
import com.quchen.flappycow.managers.SpriteFactory;
import com.quchen.flappycow.managers.SpriteType;
import com.quchen.flappycow.sprites.Button;
import com.quchen.flappycow.sprites.Character;
import com.quchen.flappycow.sprites.Coin;
import com.quchen.flappycow.sprites.Item;
import com.quchen.flappycow.sprites.Obstacle;
import com.quchen.flappycow.sprites.ObstacleBlock;
import com.quchen.flappycow.sprites.Plane;
import com.quchen.flappycow.sprites.Tutorial;

import java.util.Iterator;

public class Game extends BaseGameActivity {
    /**
     * Name of the SharedPreference that saves the medals
     */
    public static final String coin_save = "coin_save";

    /**
     * Key that saves the medal
     */
    public static final String coin_key = "coin_key";

    private static final int GAMES_PER_AD = 3;
    /**
     * Counts number of played games
     */
    public static int gameOverCounter = 1;
    public InterstitialAd interstitial;

    /**
     * Will play songs like:
     * nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan
     * nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan
     * nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan
     * nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan
     * Does someone know the second verse ???
     */
    public static MediaPlayer musicPlayer = null;

    /**
     * Whether the music should play or not
     */
    public boolean musicShouldPlay = false;

    /**
     * Time interval (ms) you have to press the backbutton twice in to exit
     */
    private static final long DOUBLE_BACK_TIME = 1000;

    /**
     * Saves the time of the last backbutton press
     */
    private long backPressed;

    /**
     * To do UI things from different threads
     */
    public MyHandler handler;

    /**
     * Hold all accomplishments
     */
    public AccomplishmentManager accomplishmentManager;
    public SpriteEventManager spriteEventManager;
    public PointManager pointManager;
    public CollisionManager collisionManager;
    public AccessoryManager accessoryManager;
    public ItemGenerator itemGenerator;
    public boolean isRandom = true;
    public static final float groundHeight = (1f * /*45*/ 35) / 720;

    /** The view that handles all kind of stuff */


    /**
     * The amount of collected coins
     */
    public int coins;

    /**
     * This will increase the revive price
     */
    public int numberOfRevive = 1;

    /**
     * The dialog displayed when the game is over
     */
    public GameOverDialog gameOverDialog;

    /**
     * MVP initialization
     */
    public GameModel model;
    public GameView view;
    public boolean tutorialIsShown = true;
    private Character player;
    private Plane background;
    private Plane frontground;
    private Tutorial tutorial;
    private Button pauseButton;
    public GameEngine engine;
    public int count = 0;
    private static Game instance;
    private Movable tapBehavior;
    private int collisionTolerance;
    private CoinManager coinManager;

    public static Game getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pointManager = new PointManager();
        coinManager = new CoinManager();
        accomplishmentManager = new AccomplishmentManager(pointManager, this);
        spriteEventManager = new SpriteEventManager(this);
        collisionManager = new CollisionManager(this);
        accessoryManager = new AccessoryManager();
        itemGenerator = new ItemGenerator();

        view = new GameView(this);
        model = new GameModel(this);
        model.setPresentor(this);
        player = model.getPlayer();
        tapBehavior = new TapBehavior(view, player.getWidth(), player.getHeight());
        background = model.getBackground();
        frontground = model.getFrontground();
        tutorial = model.getTutorial();
        pauseButton = model.getPauseButton();
        engine = new GameEngine();

        gameOverDialog = new GameOverDialog(this);
        handler = new MyHandler(this);
        setContentView(view);
        initMusicPlayer();
        initSharedPreferences();
        if (gameOverCounter % GAMES_PER_AD == 0) {
            setupAd();
        }
        instance = this;
        collisionTolerance = getResources().getDisplayMetrics().heightPixels / 50;
    }


    /**
     * Initializes the player with the nyan cat song
     * and sets the position to 0.
     */
    public void initMusicPlayer() {
        if (musicPlayer == null) {
            // to avoid unnecessary reinitialisation
            musicPlayer = MediaPlayer.create(this, R.raw.nyan_cat_theme);
            musicPlayer.setLooping(true);
            musicPlayer.setVolume(MainActivity.volume, MainActivity.volume);
        }
        musicPlayer.seekTo(0);    // Reset song to position 0
    }

    private void initSharedPreferences() {
        SharedPreferences saves = this.getSharedPreferences(coin_save, 0);
        this.coins = saves.getInt(coin_key, 0);
    }

    /**
     * Pauses the view and the music
     */
    @Override
    protected void onPause() {
        engine.pause();
        if (musicPlayer.isPlaying()) {
            musicPlayer.pause();
        }
        super.onPause();
    }

    /**
     * Resumes the view (but waits the view waits for a tap)
     * and starts the music if it should be running.
     * Also checks whether the Google Play Services are available.
     */
    @Override
    protected void onResume() {
        if (tutorialIsShown) {
            view.drawFirst(model, true);
        } else view.draw(model, true);
        if (musicShouldPlay) {
            musicPlayer.start();
        }
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) != ConnectionResult.SUCCESS) {
            Toast.makeText(this, "Please check your Google Services", Toast.LENGTH_LONG).show();
        }
        super.onResume();
    }

    /**
     * Prevent accidental exits by requiring a double press.
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - backPressed < DOUBLE_BACK_TIME) {
            super.onBackPressed();
        } else {
            backPressed = System.currentTimeMillis();
            Toast.makeText(this, getResources().getString(R.string.on_back_press), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Sends the handler the command to show the GameOverDialog.
     * Because it needs an UI thread.
     */
    public void gameOver() {
        if (gameOverCounter % GAMES_PER_AD == 0) {
            handler.sendMessage(Message.obtain(handler, MyHandler.SHOW_AD));
        } else {
            handler.sendMessage(Message.obtain(handler, MyHandler.GAME_OVER_DIALOG));
        }

    }

    public CoinManager getCoinManager() {
        return coinManager;
    }

    public void increaseCoin() {
        coinManager.increaseCoin();
    }

    /**
     * What should happen, when an obstacle is passed?
     */
    public void increasePoints() {
        pointManager.increasePoint();
    }

    public GoogleApiClient getApiClient() {
        return mHelper.getApiClient();
    }

    /**
     * Shows the GameOverDialog when a message with code 0 is received.
     */


    @Override
    public void onSignInFailed() {
    }

    @Override
    public void onSignInSucceeded() {
    }

    private void setupAd() {
        MobileAds.initialize(this, getResources().getString(R.string.ad_app_id));

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getResources().getString(R.string.ad_unit_id));

        AdRequest adRequest = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest);
        interstitial.setAdListener(new MyAdListener());
    }

    public void increaseNumOfRevive() {
        numberOfRevive++;
    }

    private class MyAdListener extends AdListener {
        public void onAdClosed() {
            handler.sendMessage(Message.obtain(handler, MyHandler.GAME_OVER_DIALOG));
        }
    }


    public ObstacleBlock createBlock() {
        Obstacle spider = (Obstacle) SpriteFactory.create(SpriteType.Spider, this, view);
        Obstacle log = (Obstacle) SpriteFactory.create(SpriteType.Log, this, view);
        int height = this.getResources().getDisplayMetrics().heightPixels;
        int gap = 1100;
        int random = 0;
        if (isRandom) {
            gap = height / 4 - this.getSpeedX();
            if (gap < height / 5) {
                gap = height / 5;
            }
            random = (int) (Math.random() * height * 2 / 5);
        }
        int y1 = (height / 10) + random - spider.getHeight();
        int y2 = (height / 10) + random + gap;
        spider.moveTo(this.getResources().getDisplayMetrics().widthPixels, y1);
        log.moveTo(this.getResources().getDisplayMetrics().widthPixels, y2);
        return new ObstacleBlock(spider, log);
    }

    /**
     * Checks whether an obstacle is passed.
     */
    public void checkPasses() {
        for (ObstacleBlock o : model.obstacles) {
            if (o.isPassed(model.getPlayer()) && !o.isAlreadyPassed) {
                count++;
                this.spriteEventManager.notifyPass(model.getPlayer(), o, model.powerUps, view);
            }
        }
    }

    /**
     * Checks whether the obstacles or powerUps are out of range and deletes them
     */
    public void checkOutOfRange() {
        for (int i = 0; i < model.obstacles.size(); i++) {
            if (model.obstacles.get(i).isOutOfRange()) {
                model.obstacles.remove(i);
                i--;
            }
        }
        for (int i = 0; i < model.powerUps.size(); i++) {
            if (this.model.powerUps.get(i).isOutOfRange()) {
                this.model.powerUps.remove(i);
                i--;
            }
        }
    }

    /**
     * Update sprite movements
     */
    public void move() {
        for (ObstacleBlock o : model.obstacles) {
            o.setSpeedX(-getSpeedX());
            o.move();
        }
        for (Item p : model.powerUps) {
            p.move();
        }

        model.updateBackgroundSpeed();
        background.move();
        model.updateFrontgroundSpeed();
        frontground.move();
        pauseButton.move();
        player.move();
    }

    /**
     * Checks collisions and performs the action
     */
    public void checkCollisionWithObstacle() {
        for (ObstacleBlock o : model.obstacles) {
            if (o.isColliding(model.getPlayer(), collisionTolerance)) {
                eventCollisionWithObstacle(player, o);
            }
        }
    }

    public void eventCollisionWithObstacle(Character player, ObstacleBlock o) {
        playerDeadFall();
    }

    public void checkCollisionWithItem() {
        Iterator<Item> iterator = model.powerUps.iterator();
        while (iterator.hasNext()) {
            Item next = iterator.next();
            if (next.isColliding(player, collisionTolerance)) {
                next.onCollision();
                if (next instanceof Coin) {
                    increaseCoin();
                } else {
                    eventTransformToNyanCat();
                }
                iterator.remove();
            }
        }
    }

    public void eventTransformToNyanCat() {
        Coordinate coordinate = player.getCoordinate();
        player = (Character) SpriteFactory.create(SpriteType.NyanCat, this, view);
        player.setCoordinate(coordinate);
        model.setPlayer(player);
        musicShouldPlay = true;
        musicPlayer.start();
    }


    public void checkCollisionWithEdge() {
        if (player.isTouchingEdge(groundHeight, view)) {
            engine.pause();
            player.dead();
            gameOver();
        }
    }

    /**
     * if no obstacle is present a new one is created
     */
    public void createObstacle() {
        if (model.obstacles.size() < 1) {
            model.obstacles.add(createBlock());
        }
    }

    /**
     * return the speed of the obstacles/cow
     */
    private int getSpeedX() {
        // 16 @ 720x1280 px
        int speedDefault = view.getWidth() / 45;

        // 1,2 every 4 points @ 720x1280 px
        int speedIncrease = (int) (view.getWidth() / 600f * (pointManager.point / 4));

        int speed = speedDefault + speedIncrease;

        return Math.min(speed, 2 * speedDefault);
    }

    /**
     * Let the player fall to the ground
     */
    public void playerDeadFall() {
        //require(model.getPlayer().isDead()).isTrue();
        player.dead();
        do {
            player.move();
            view.draw(model, true);
            // sleep
            try {
                Thread.sleep(engine.UPDATE_INTERVAL / 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!player.isTouchingEdge(groundHeight, view));
        //ensure(model.getPlayer().isTouchingGround(groundHeight,view)).isTrue();
    }

    public void judegeEvent(MotionEvent event) {
        if (tutorialIsShown) {
            // dismiss tutorial
            tutorialIsShown = false;
            engine.resume();
            if (tapBehavior != null) {
                playerOnTap(tapBehavior);
            } else {
                playerOnTap();
            }
        } else if (engine.isPaused()) {
            engine.resume();
        } else if (pauseButtonIsTouching((int) event.getX(), (int) event.getY()) && !engine.isPaused()) {
            engine.pause();
        } else {
            if (tapBehavior != null) {
                playerOnTap(tapBehavior);
            } else {
                playerOnTap();
            }
        }
    }

    /**
     * judge play is dead
     */
    public boolean playerIsDead() {
        return player.isDead();
    }

    /**
     * Player Ontap
     */

    public void playerOnTap(Movable tapBehavior) {
        player.onTap(tapBehavior);
    }

    public void playerOnTap() {
        player.onTap();
    }

    /**
     * pauseButton is tounching
     */

    public boolean pauseButtonIsTouching(int x, int y) {
        return pauseButton.isTouching(x, y);
    }

    /**
     * return player width
     */

    public int getPlayerWidth() {
        return model.getPlayerWidth();
    }

    /**
     * return player width
     */

    public int getPlayerHeight() {
        return model.getPlayerHeight();
    }

    /**
     * return Game model
     */

    public GameModel getModel() {
        return this.model;
    }

    /**
     * return count
     */

    public int getCount() {
        return this.count;
    }

    /**
     * return view
     */

    public GameView getView() {
        return this.view;
    }

    public void setPlayer(Character player) {
        this.player = player;
    }

    public AccomplishmentManager getAccomplishmentManager() {
        return accomplishmentManager;
    }

    public SpriteEventManager getSpriteEventManager() {
        return spriteEventManager;
    }

    public PointManager getPointManager() {
        return pointManager;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    public AccessoryManager getAccessoryManager() {
        return accessoryManager;
    }

    public ItemGenerator getItemGenerator() {
        return itemGenerator;
    }

    public boolean isRandom() {
        return isRandom;
    }

    public static float getGroundHeight() {
        return groundHeight;
    }

    public int getCoins() {
        return coins;
    }

    public int getNumberOfRevive() {
        return numberOfRevive;
    }

    public GameOverDialog getGameOverDialog() {
        return gameOverDialog;
    }

    public boolean isTutorialIsShown() {
        return tutorialIsShown;
    }

    public Character getPlayer() {
        return player;
    }

    public Plane getBackground() {
        return background;
    }

    public Plane getFrontground() {
        return frontground;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public Button getPauseButton() {
        return pauseButton;
    }

    public GameEngine getEngine() {
        return engine;
    }
}
