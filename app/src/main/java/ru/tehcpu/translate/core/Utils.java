package ru.tehcpu.translate.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;

import java.util.Locale;

import ru.tehcpu.translate.R;
import ru.tehcpu.translate.TranslateApplication;
import ru.tehcpu.translate.model.Translation;

/**
 * Created by tehcpu on 4/22/17.
 */

public class Utils {
    private static final String TAG = "[ :: Utils :: ]";
    private static SharedPreferences GeneralPrefs;

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

    public static String getDefaultDirection() {
        String direction;
        switch (getUILanguage()) {
            case "en": direction = "en-ru";break;
            case "ru": direction = "ru-en";break;
            default: direction = getUILanguage()+"-en";
        }
        return direction;
    }

    // Prefs
    public static SharedPreferences getPrefs() {
        if (GeneralPrefs == null) GeneralPrefs = TranslateApplication.get()
                .getSharedPreferences("General", Context.MODE_PRIVATE);
        return GeneralPrefs;
    }

    public static SharedPreferences.Editor savePrefs() {
        return getPrefs().edit();
    }

}
