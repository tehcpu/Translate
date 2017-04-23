package ru.tehcpu.translate.ui;

import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.DisplayMetrics;

import ru.tehcpu.translate.ui.fragment.HistoryFragment;
import ru.tehcpu.translate.ui.fragment.MainFragment;
import ru.tehcpu.translate.ui.fragment.SettingsFragment;

/**
 * Created by tehcpu on 4/22/17.
 */

public class Utils {
    public static void invalidateTabs(TabLayout tabLayout, int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) tabLayout.getTabAt(i).getIcon().setAlpha(100);
        tabLayout.getTabAt(position).getIcon().setAlpha(255);
    }

    // Graphic
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
