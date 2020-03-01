package com.quchen.flappycow.managers;

import android.graphics.Bitmap;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.sprites.Accessory;
import com.quchen.flappycow.sprites.Character;
import com.quchen.flappycow.sprites.Cow;

import static com.busybusy.dbc.Dbc.ensure;

public class AccessoryManager {
    public static int POINTS_TO_SIR = 1;
    public static int POINTS_TO_COOL = 2;

    public AccessoryManager() {
    }

    public void checkAccessoryFor(Character player, int point, Game game, GameView view) {

        if (player instanceof Cow) {
            if (point == POINTS_TO_SIR) {
                player.addAccessory(SpriteType.Accesory_Sir, game, view);
                Bitmap bitmaps1 = player.getAccessoryBitmap();
                Bitmap bitmaps2 = BitmapManager.getScaledBitmapAlpha8(game, R.drawable.accessory_sir);
                ensure(bitmaps1.sameAs(bitmaps2)).isTrue();
            } else if (point == POINTS_TO_COOL) {
                player.addAccessory(SpriteType.Accesory_Cool, game, view);
                Bitmap bitmapc1 = player.getAccessoryBitmap();
                Bitmap bitmapc2 = BitmapManager.getScaledBitmapAlpha8(game, R.drawable.accessory_sunglasses);
                ensure(bitmapc1.sameAs(bitmapc2)).isTrue();
            }
        }
    }

    public void revive(Character player, Game game, GameView view) {
        if (player instanceof Cow) {
            player.addAccessory(SpriteType.Accesory_Scumbag, game, view);
        }
    }
}
