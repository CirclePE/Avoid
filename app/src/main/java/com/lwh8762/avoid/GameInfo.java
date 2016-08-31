package com.lwh8762.avoid;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by W on 2016-07-07.
 */
public class GameInfo {

    public static int maxLevel = 0;
    public static int curLevel = 1;
    private static SharedPreferences prefs;

    public static void setPreference(SharedPreferences prefs) {
        GameInfo.prefs = prefs;
    }

    public static int getCurrentLevel() {
        int level = 0;
        try {
            level = prefs.getInt("level", 0);
            if (level == 0) {
                setCurrentLevel(1);
                level = 1;
            }
        }catch (Exception e) {
            setCurrentLevel(1);
            return 1;
        }
        return level;
    }

    public static void setCurrentLevel(int level) {
        SharedPreferences.Editor editor = prefs.edit();
        if(level<curLevel) {
            Log.e("Alert","Less level setted.");
        }
        editor.putInt("level",level);
        editor.commit();
    }
}
