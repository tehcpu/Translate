package ru.tehcpu.translate.core;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ru.tehcpu.translate.TranslateApplication;

/**
 * Created by tehcpu on 4/24/17.
 */

public class Api {
    private static Api Instance;
    private static String ApiVersion = "v1.5";
    private static String ApiKey = "trnsl.1.1.20170316T190128Z.adc95438ef4b279b.6481823440c28fd726fe5319dddaa6ed70c74dcb";
    private static String ApiBase = "https://translate.yandex.net/api/"+ApiVersion+"/tr.json/";
    private static String TAG = "[ API ]";

    public void Api() {
        Instance = this;
    }

    public void request(final String method, HashMap<String, String> params, final ApiCallback cb) {
        String url = ApiBase+method+"?key="+ApiKey+ Utils.mapToQueryString(params);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse -- "+response.toString());
                if (response.has("code")) {
                    try {
                        if (response.getInt("code") == 200) {
                            cb.onResponse();
                            switch (method) {
                                case "getLangs": processGetLangs(response); break;
                                case "detect": processDetect(response); break;
                                case "translate": processTranslate(response); break;
                                default:break;
                            }
                        } else {
                            cb.onError(response.getInt("code"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    public void processGetLangs(JSONObject response) {

    }

    public void processDetect(JSONObject response) {

    }

    public void processTranslate(JSONObject response) {

    }

    public class ApiCallback {
        public void onResponse() {

        }
        public void onError(int code) {

        }
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
