package com.quchen.flappycow.managers;

import android.content.Context;
import android.graphics.Bitmap;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.behaviors.BasicMoveBehavior;
import com.quchen.flappycow.behaviors.CenterMoveBehavior;
import com.quchen.flappycow.behaviors.CharacterMoveBehavior;
import com.quchen.flappycow.behaviors.Coordinate;
import com.quchen.flappycow.behaviors.Movable;
import com.quchen.flappycow.behaviors.Speed;
import com.quchen.flappycow.behaviors.StationaryMoveBehavior;
import com.quchen.flappycow.sprites.Accessory;
import com.quchen.flappycow.sprites.Coin;
import com.quchen.flappycow.sprites.Cow;
import com.quchen.flappycow.sprites.NyanCat;
import com.quchen.flappycow.sprites.Obstacle;
import com.quchen.flappycow.sprites.Button;
import com.quchen.flappycow.sprites.Pikachu;
import com.quchen.flappycow.sprites.Plane;
import com.quchen.flappycow.sprites.Rainbow;
import com.quchen.flappycow.sprites.Character;
import com.quchen.flappycow.sprites.Sprite;
import com.quchen.flappycow.sprites.Toast;
import com.quchen.flappycow.sprites.Tutorial;

public enum SpriteType {
    Cow {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getScaledBitmapAlpha8(context, R.drawable.cow);
            Movable movable = new CharacterMoveBehavior(view);
            Coordinate coordinate = new Coordinate(0, context.getResources().getDisplayMetrics().heightPixels / 2);
            Speed speed = new Speed(0, 0);
            return create(context, view, bitmap, 4, 8, movable, coordinate, speed, (short) 3);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            Character ret = new Cow(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime);
            ret.setTapSound(MusicManager.soundPool.load(context, R.raw.cow, 1));
            return ret;
        }
    },

    Pikachu {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getScaledBitmapAlpha8(context, R.drawable.pikachu);
            Movable movable = new CharacterMoveBehavior(view);
            Coordinate coordinate = new Coordinate(0, context.getResources().getDisplayMetrics().heightPixels / 2);
            Speed speed = new Speed(0, 0);
            return create(context, view, bitmap, 2, 6, movable, coordinate, speed, (short) 3);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            Character ret = new Pikachu(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime);
            ret.setTapSound(MusicManager.soundPool.load(context, R.raw.pika, 1));
            return ret;
        }
    },

    NyanCat {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getScaledBitmapAlpha8(context, R.drawable.nyan_cat);
            Movable movable = new CharacterMoveBehavior(view);
            Coordinate coordinate = new Coordinate(0, context.getResources().getDisplayMetrics().heightPixels / 2);
            Speed speed = new Speed(0, 0);
            return create(context, view, bitmap, 2, 1, movable, coordinate, speed, (short) 1);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            Rainbow rainbow = new Rainbow(BitmapManager.getScaledBitmapAlpha8(context, R.drawable.rainbow), 3, 4, new BasicMoveBehavior(view), new Coordinate(0, 0), new Speed(0, 0), frameTime);
            Character ret = new NyanCat(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime, rainbow);
            return ret;
        }
    },

    Coin {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getScaledBitmapAlpha8(context, R.drawable.coin);
            Movable movable = new BasicMoveBehavior(view);
            Coordinate coordinate = new Coordinate(view.getWidth() * 4 / 5, 0 - bitmap.getHeight());
            Speed speed = new Speed(-view.getSpeedX(), (int) (view.getSpeedX() * (Math.random() + 0.5)));
            return create(context, view, bitmap, 1, 12, movable, coordinate, speed, (short) 1);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            int collideSound = MusicManager.load((Game) context, R.raw.coin, 1);
            int passSound = -1;
            return new Coin(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime, collideSound, passSound);
        }
    },

    Toast {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getScaledBitmapAlpha8(context, R.drawable.toast);
            Movable movable = new BasicMoveBehavior(view);
            Coordinate coordinate = new Coordinate(view.getWidth() * 4 / 5, 0 - bitmap.getHeight());
            Speed speed = new Speed(-view.getSpeedX(), (int) (view.getSpeedX() * (Math.random() + 0.5)));
            return create(context, view, bitmap, 1, 1, movable, coordinate, speed, (short) 1);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            int collideSound = -1;
            int passSound = -1;
            return new Toast(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime, collideSound, passSound);
        }
    },

    Background {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getDownScaledBitmapAlpha8(context, R.drawable.bg);
            Movable movable = new BasicMoveBehavior(view);
            Coordinate coordinate = new Coordinate(0, 0);
            Speed speed = new Speed(0, 0);
            return create(context, view, bitmap, 1, 1, movable, coordinate, speed, (short) 1);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            return new Plane(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime);
        }
    },

    Frontground {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getDownScaledBitmapAlpha8(context, R.drawable.fg);
            Movable movable = new BasicMoveBehavior(view);
            Coordinate coordinate = new Coordinate(0, 0);
            Speed speed = new Speed(0, 0);
            return create(context, view, bitmap, 1, 1, movable, coordinate, speed, (short) 1);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            return new Plane(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime);
        }
    },

    PauseButton {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getDownScaledBitmapAlpha8(context, R.drawable.pause_button);
            Movable movable = new StationaryMoveBehavior(view);
            Coordinate coordinate = new Coordinate(0, 0);
            Speed speed = new Speed(0, 0);
            return create(context, view, bitmap, 1, 1, movable, coordinate, speed, (short) 1);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            return new Button(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime);
        }
    },

    Tutorial {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getDownScaledBitmapAlpha8(context, R.drawable.tutorial);
            Movable movable = new CenterMoveBehavior(view);
            Coordinate coordinate = new Coordinate(0, 0);
            Speed speed = new Speed(0, 0);
            return create(context, view, bitmap, 1, 1, movable, coordinate, speed, (short) 1);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            return new Tutorial(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime);
        }
    },

    Spider {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getScaledBitmapAlpha8(context, R.drawable.spider_full);
            Movable movable = new BasicMoveBehavior(view);
            Coordinate coordinate = new Coordinate(0, 0);
            Speed speed = new Speed(0, 0);
            return create(context, view, bitmap, 1, 1, movable, coordinate, speed, (short) 1);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            int collideSound = MusicManager.load(context, R.raw.crash, 1);
            int passSound = MusicManager.load(context, R.raw.pass, 1);
            return new Obstacle(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime, collideSound, passSound);
        }
    },

    Log {
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getScaledBitmapAlpha8(context, R.drawable.log_full);
            Movable movable = new BasicMoveBehavior(view);
            Coordinate coordinate = new Coordinate(0, 0);
            Speed speed = new Speed(0, 0);
            return create(context, view, bitmap, 1, 1, movable, coordinate, speed, (short) 1);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            int collideSound = MusicManager.load(context, R.raw.crash, 1);
            int passSound = MusicManager.load(context, R.raw.pass, 1);
            return new Obstacle(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime, collideSound, passSound);
        }
    },

    Accesory_Sir {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getScaledBitmapAlpha8(context, R.drawable.accessory_sir);
            Movable movable = new BasicMoveBehavior(view);
            Coordinate coordinate = new Coordinate(0, 0);
            Speed speed = new Speed(0, 0);
            return create(context, view, bitmap, 1, 1, movable, coordinate, speed, (short) 1);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            return new Accessory(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime);
        }
    },

    Accesory_Cool {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getScaledBitmapAlpha8(context, R.drawable.accessory_sunglasses);
            Movable movable = new BasicMoveBehavior(view);
            Coordinate coordinate = new Coordinate(0, 0);
            Speed speed = new Speed(0, 0);
            return create(context, view, bitmap, 1, 1, movable, coordinate, speed, (short) 1);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            return new Accessory(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime);
        }
    },

    Accesory_Scumbag {
        @Override
        public Sprite create(Context context, GameView view) {
            Bitmap bitmap = BitmapManager.getScaledBitmapAlpha8(context, R.drawable.accessory_scumbag);
            Movable movable = new BasicMoveBehavior(view);
            Coordinate coordinate = new Coordinate(0, 0);
            Speed speed = new Speed(0, 0);
            return create(context, view, bitmap, 1, 1, movable, coordinate, speed, (short) 1);
        }

        @Override
        public Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime) {
            return new Accessory(bitmap, rowNr, colNr, movable, coordinate, speed, frameTime);
        }
    },

    ;

    public abstract Sprite create(Context context, GameView view);

    public abstract Sprite create(Context context, GameView view, Bitmap bitmap, int rowNr, int colNr, Movable movable, Coordinate coordinate, Speed speed, short frameTime);


}
