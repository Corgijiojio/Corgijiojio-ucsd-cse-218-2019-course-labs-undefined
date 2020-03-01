/**
 * Saves achievements and score in shared preferences.
 * You should use a SQLite DB instead, but I'm too lazy to chance it now.
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.managers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Message;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.quchen.flappycow.Game;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util.MyHandler;
import com.quchen.flappycow.enumType.AchievementType;

public class AccomplishmentManager {

    /**
     * Points needed for a gold medal
     */
    public static int GOLD_POINTS = 3;

    /**
     * Points needed for a silver medal
     */
    public static int SILVER_POINTS = 2;

    /**
     * Points needed for a bronze medal
     */
    public static int BRONZE_POINTS = 1;

    public static final String SAVE_NAME = "ACCOMBLISHMENTS";

    public static final String ONLINE_STATUS_KEY = "online_status";

    public static final String KEY_POINTS = "points";
    public static final String ACHIEVEMENT_KEY_50_COINS = "achievement_survive_5_minutes";
    public static final String ACHIEVEMENT_KEY_TOASTIFICATION = "achievement_toastification";
    public static final String ACHIEVEMENT_KEY_BRONZE = "achievement_bronze";
    public static final String ACHIEVEMENT_KEY_SILVER = "achievement_silver";
    public static final String ACHIEVEMENT_KEY_GOLD = "achievement_gold";

    public boolean achievement_50_coins;
    public boolean achievement_toastification;
    public boolean achievement_bronze;
    public boolean achievement_silver;
    public boolean achievement_gold;

    private PointManager pointManager;
    private static Game game;

    public AccomplishmentManager(PointManager pointManager, Game game) {
        this.pointManager = pointManager;
        this.game = game;
    }

    /**
     * Stores the score and achievements locally.
     * <p>
     * The accomblishments will be saved local via SharedPreferences.
     * This makes it very easy to cheat.
     *
     * @param activity activity that is needed for shared preferences
     */
    public void saveLocal(Activity activity) {
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();

        if (pointManager.getPoint() > saves.getInt(KEY_POINTS, 0)) {
            editor.putInt(KEY_POINTS, pointManager.getPoint());
        }
        if (achievement_50_coins) {
            editor.putBoolean(ACHIEVEMENT_KEY_50_COINS, true);
        }
        if (achievement_toastification) {
            editor.putBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, true);
        }
        if (achievement_bronze) {
            editor.putBoolean(ACHIEVEMENT_KEY_BRONZE, true);
        }
        if (achievement_silver) {
            editor.putBoolean(ACHIEVEMENT_KEY_SILVER, true);
        }
        if (achievement_gold) {
            editor.putBoolean(ACHIEVEMENT_KEY_GOLD, true);
        }

        editor.commit();
    }

    /**
     * Uploads accomplishments to Google Play Services
     *
     * @param activity
     * @param apiClient
     */
    public void submitScore(Activity activity, GoogleApiClient apiClient) {
        Games.Leaderboards.submitScore(apiClient, activity.getResources().getString(R.string.leaderboard_highscore), pointManager.getPoint());

        if (this.achievement_50_coins) {
            Games.Achievements.unlock(apiClient, activity.getResources().getString(R.string.achievement_50_coins));
        }
        if (this.achievement_toastification) {
            Games.Achievements.unlock(apiClient, activity.getResources().getString(R.string.achievement_toastification));
        }
        if (this.achievement_bronze) {
            Games.Achievements.unlock(apiClient, activity.getResources().getString(R.string.achievement_bronze));
        }
        if (this.achievement_silver) {
            Games.Achievements.unlock(apiClient, activity.getResources().getString(R.string.achievement_silver));
        }
        if (this.achievement_gold) {
            Games.Achievements.unlock(apiClient, activity.getResources().getString(R.string.achievement_gold));
        }

        AccomplishmentManager.savesAreOnline(activity);

        Toast.makeText(activity.getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
    }

    /**
     * reads the local stored data
     *
     * @param activity activity that is needed for shared preferences
     * @return local stored score and achievements
     */
    public static AccomplishmentManager getLocal(Activity activity) {
        AccomplishmentManager box = new AccomplishmentManager(new PointManager(), game);
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);

        box.pointManager.setPoint(saves.getInt(KEY_POINTS, 0));
        box.achievement_50_coins = saves.getBoolean(ACHIEVEMENT_KEY_50_COINS, false);
        box.achievement_toastification = saves.getBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, false);
        box.achievement_bronze = saves.getBoolean(ACHIEVEMENT_KEY_BRONZE, false);
        box.achievement_silver = saves.getBoolean(ACHIEVEMENT_KEY_SILVER, false);
        box.achievement_gold = saves.getBoolean(ACHIEVEMENT_KEY_GOLD, false);

        return box;
    }

    /**
     * marks the data as online
     *
     * @param activity activity that is needed for shared preferences
     */
    public static void savesAreOnline(Activity activity) {
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();
        editor.putBoolean(ONLINE_STATUS_KEY, true);
        editor.commit();
    }

    /**
     * marks the data as offline
     *
     * @param activity activity that is needed for shared preferences
     */
    public static void savesAreOffline(Activity activity) {
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();
        editor.putBoolean(ONLINE_STATUS_KEY, false);
        editor.commit();
    }

    /**
     * checks if the last data is already uploaded
     *
     * @param activity activity that is needed for shared preferences
     * @return wheater the last data is already uploaded
     */
    public static boolean isOnline(Activity activity) {
        return activity.getSharedPreferences(SAVE_NAME, 0).getBoolean(ONLINE_STATUS_KEY, true);
    }

    public void achieve(AchievementType achievementType) {
        if (achievementType == AchievementType.TOASTIFICATION) {
            game.accomplishmentManager.achievement_toastification = true;
            if (game.getApiClient().isConnected()) {
                Games.Achievements.unlock(game.getApiClient(), game.getView().getResources().getString(R.string.achievement_toastification));
            } else {
                game.handler.sendMessage(Message.obtain(game.handler, 1, R.string.toast_achievement_toastification, MyHandler.SHOW_TOAST));
            }
        } else if (achievementType == AchievementType.COINS_50) {
            achievement_50_coins = true;
            if (game.getApiClient().isConnected()) {
                Games.Achievements.unlock(game.getApiClient(), game.getResources().getString(R.string.achievement_50_coins));
            } else {
                game.handler.sendMessage(Message.obtain(game.handler, 1, R.string.toast_achievement_50_coins, MyHandler.SHOW_TOAST));
            }
        } else if (achievementType == AchievementType.BRONZE) {
            achievement_bronze = true;
            if (game.getApiClient().isConnected()) {
                Games.Achievements.unlock(game.getApiClient(), game.getResources().getString(R.string.achievement_bronze));
            } else {
                game.handler.sendMessage(Message.obtain(game.handler, MyHandler.SHOW_TOAST, R.string.toast_achievement_bronze, MyHandler.SHOW_TOAST));
            }
        } else if (achievementType == AchievementType.SILVER) {
            achievement_bronze = true;
            if (game.getApiClient().isConnected()) {
                Games.Achievements.unlock(game.getApiClient(), game.getResources().getString(R.string.achievement_bronze));
            } else {
                game.handler.sendMessage(Message.obtain(game.handler, MyHandler.SHOW_TOAST, R.string.toast_achievement_bronze, MyHandler.SHOW_TOAST));
            }
        }else if (achievementType == AchievementType.GOLD) {
            achievement_bronze = true;
            if (game.getApiClient().isConnected()) {
                Games.Achievements.unlock(game.getApiClient(), game.getResources().getString(R.string.achievement_bronze));
            } else {
                game.handler.sendMessage(Message.obtain(game.handler, MyHandler.SHOW_TOAST, R.string.toast_achievement_bronze, MyHandler.SHOW_TOAST));
            }
        }
    }


}