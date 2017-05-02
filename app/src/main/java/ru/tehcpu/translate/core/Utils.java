package ru.tehcpu.translate.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import ru.tehcpu.translate.model.Language;
import ru.tehcpu.translate.model.Language_Table;
import ru.tehcpu.translate.model.Translation;
import ru.tehcpu.translate.model.Translation_Table;

/**
 * Created by tehcpu on 4/22/17.
 */

public class Utils {
    private static final String TAG = "[ :: Utils :: ]";

    // UI
    public static void invalidateTabs(TabLayout tabLayout, int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) tabLayout.getTabAt(i).getIcon().setAlpha(100);
        tabLayout.getTabAt(position).getIcon().setAlpha(255);
    }

    // Graphics
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    // Other
    public static String getUILanguage() {
        return Locale.getDefault().getLanguage();
    }

}
