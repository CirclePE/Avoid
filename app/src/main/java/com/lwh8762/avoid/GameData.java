package com.lwh8762.avoid;

import android.graphics.Bitmap;

/**
 * Created by W on 2016-07-15.
 */
public class GameData {
    public static Bitmap playerBitmap = null;

    public static void setPlayerBitmap(Bitmap bmp) {
        playerBitmap = bmp;
    }

    public static Bitmap getPlayerBitmap() {
        return Bitmap.createBitmap(playerBitmap);
    }
}
