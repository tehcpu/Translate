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
    private static final String TAG = "[Utils]";

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

    public static String mapToQueryString(HashMap<String, String> map) {
        if (map != null) {
            StringBuilder string = new StringBuilder();

            for (HashMap.Entry<String, String> entry : map.entrySet()) {
                string.append("&");
                string.append(entry.getKey());
                string.append("=");
                string.append(entry.getValue());
            }

            return string.toString();
        } else {
            return "";
        }
    }

    public static String getUILanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static void loadLanguages(final Context context, final onLanguageCallback cb) {

        SharedPreferences sharedPref = context.getSharedPreferences("General", Context.MODE_PRIVATE);
        String lastLang = sharedPref.getString("ui", "notalanguage");

        String from = getUILanguage();

        if (!lastLang.equals(from) || SQLite.select().from(Language.class).count() == 0) {
            sharedPref.edit().putString("ui", from).apply();
            HashMap<String, String> params = new HashMap<>();
            params.put("ui", Utils.getUILanguage());
            Api.getInstance().request("getLangs", params, new Api.ApiCallback() {
                @Override
                public void onResponse(ArrayList<Language> languages, Object object) {
                    returnLanguages(cb);
                }

                @Override
                public void onError(int code) {
                    //
                }
            });
        } else {
            returnLanguages(cb);
        }
    }

    public static void returnLanguages(onLanguageCallback cb) {
        if (SQLite.select().from(Translation.class).count() == 0) {
            ArrayList<Language> languages = new ArrayList<>();
            String from = getUILanguage();

            Language English = SQLite.select().from(Language.class).where(Language_Table.key.eq("en")).querySingle();
            Language Russian = SQLite.select().from(Language.class).where(Language_Table.key.eq("ru")).querySingle();

            Log.d(TAG, from);

            Language fromObject = SQLite.select().from(Language.class).where(Language_Table.key.eq(from)).querySingle();
            if (fromObject == null || !fromObject.exists()) fromObject = English;

            languages.add(fromObject);
            languages.add(English);

            if (languages.get(0).getKey().equals("en")) languages.set(1, Russian);

            cb.callback(languages, null);
        } else {
            Translation translation = SQLite.select().from(Translation.class).where().orderBy(Translation_Table.id, false).querySingle();
            if (translation.getFake_direction() != null) {
                cb.callback(unFakeDirection(translation.getFake_direction()), translation);
            } else {
                cb.callback(translation.getLanguage(), translation);
            }
        }
    }

    public static void translate(final onTranslateCallback cb, String text, String lang) {
        HashMap<String, String> params = new HashMap<>();
        params.put("text", text);
        params.put("lang", lang);
        Api.getInstance().request("translate", params, new Api.ApiCallback() {
            @Override
            public void onResponse(ArrayList<Language> languages, Object object) {
                cb.callback(languages, (Translation) object);
            }

            @Override
            public void onError(int code) {
                //
            }
        });
    }

    public static ArrayList<Language> unFakeDirection(String fake) {
        ArrayList<Language> languages = new ArrayList<>();
        String[] dc = fake.split("-", -1);

        languages.add(SQLite.select().from(Language.class).where(Language_Table.key.eq(dc[0])).querySingle());
        languages.add(SQLite.select().from(Language.class).where(Language_Table.key.eq(dc[1])).querySingle());

        return languages;
    }

    public interface onLanguageCallback {
        void callback(ArrayList<Language> languages, Translation translation);
    }

    public interface onTranslateCallback {
        void callback(ArrayList<Language> languages, Translation translation);
    }
}
