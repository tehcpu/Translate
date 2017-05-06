package ru.tehcpu.translate.core;

import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.view.View;

import java.util.Locale;

import ru.tehcpu.translate.R;

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
    public static void showSnack(View view) {
        Snackbar.make(view.getRootView().findViewById(R.id.generalWrapper), "werwer", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .show();
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
