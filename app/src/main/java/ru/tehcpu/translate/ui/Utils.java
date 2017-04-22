package ru.tehcpu.translate.ui;

import android.support.design.widget.TabLayout;

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
}
