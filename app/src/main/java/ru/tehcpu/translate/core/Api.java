package ru.tehcpu.translate.core;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import ru.tehcpu.translate.TranslateApplication;
import ru.tehcpu.translate.model.Direction;
import ru.tehcpu.translate.model.Direction_Table;
import ru.tehcpu.translate.model.Language;
import ru.tehcpu.translate.model.Language_Table;
import ru.tehcpu.translate.model.Translation;
import ru.tehcpu.translate.model.Translation_Table;

/**
 * Created by tehcpu on 4/24/17.
 */

public class Api {
    private static Api Instance;
    private static String ApiVersion = "v1.5";
    private static String ApiKey = "trnsl.1.1.20170316T190128Z.adc95438ef4b279b.6481823440c28fd726fe5319dddaa6ed70c74dcb";
    private static String ApiBase = "https://translate.yandex.net/api/"+ApiVersion+"/tr.json/";
    private static String TAG = "[API]";

    public void Api() {
        Instance = this;
    }

    public void request(final String method, final HashMap<String, String> params, final ApiCallback cb) {
        String txt = "";
        if (params.containsKey("text")) {
            txt = params.get("text");
            try {
                params.put("text", URLEncoder.encode(txt, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String url = ApiBase+method+"?key="+ApiKey+ Utils.mapToQueryString(params);

        final String finalTxt = txt;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse -- "+response.toString());
                if (response.has("code")) {
                    try {
                        if (response.getInt("code") != 200) {
                            int error = response.getInt("code");
                            //@TODO error handle
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //cb.onResponse();
                switch (method) {
                    case "getLangs": processGetLangs(response, cb); break;
                    case "detect": processDetect(response); break;
                    case "translate": processTranslate(response, cb, finalTxt); break;
                    default:break;
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                cb.onError(error.networkResponse.statusCode+100);
            }
        });

        TranslateApplication.get().pushRequest(jsObjRequest);
    }

    public void processGetLangs(JSONObject response, ApiCallback cb) {
        // flush old records
        Delete.table(Language.class);
        Delete.table(Direction.class);
        if (response.has("langs")) {
            try {
                HashMap<String, Long> languages = new HashMap<>();
                JSONObject langs = response.getJSONObject("langs");
                try {
                    Iterator<String> temp = langs.keys();
                    while (temp.hasNext()) {
                        String key = temp.next();
                        String value = langs.getString(key);
                        Language language = new Language();
                        language.setKey(key);
                        language.setTitle(value);
                        language.save();
                        languages.put(language.getKey(), language.getId());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray dirs = response.getJSONArray("dirs");
                for (int i=0; i < dirs.length(); i++) {
                    String dir = (String) dirs.get(i);
                    String[] dc = dir.split("-", -1);

                    Direction direction = new Direction();
                    direction.setFrom(languages.get(dc[0]));
                    direction.setTo(languages.get(dc[1]));
                    direction.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        cb.onResponse(null, null);
    }

    public void processDetect(JSONObject response) {
        // :c
    }

    public void processTranslate(JSONObject response, ApiCallback cb, String text) {
        String dir = null;
        try {
            dir = response.getString("lang");
            String[] dc = dir.split("-", -1);
            Language from = SQLite.select().from(Language.class).where(Language_Table.key.eq(dc[0])).querySingle();
            Language to = SQLite.select().from(Language.class).where(Language_Table.key.eq(dc[1])).querySingle();
            Log.d(TAG, to+"=====");
            Direction direction = SQLite.select().from(Direction.class).where(Direction_Table.from.eq(from.getId())).and(Direction_Table.to.eq(to.getId())).querySingle();
            Translation translation = new Translation();

            translation.setSource(text);
            translation.setTranslation(response.getJSONArray("text").getString(0));
            ArrayList<Language> languages = new ArrayList<>();

            languages.add(from);
            languages.add(to);


            if (direction != null) { translation.setDirection(direction.getId()); } else { translation.setFake_direction(dir); languages = Utils.unFakeDirection(dir); Log.d(TAG, to+"+++++");}
            if (!translation.getSource().equals(SQLite.select().from(Translation.class).where().orderBy(Translation_Table.id, false).querySingle().getSource())) translation.save();

            cb.onResponse(languages, translation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface ApiCallback {
        public void onResponse(ArrayList<Language> languages, Object object);

        public void onError(int code);
    }

    //

    public static Api getInstance() {
        Api localInstance = Instance;
        if (localInstance == null) {
            synchronized (Api.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new Api();
                }
            }
        }
        return localInstance;
    }
}
