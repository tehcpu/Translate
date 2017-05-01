package ru.tehcpu.translate;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by tehcpu on 4/2/17.
 */

public class TranslateApplication extends Application {
    private RequestQueue queue;
    private static TranslateApplication Instance;

    public TranslateApplication(){
        super();
        Instance = this;
    }

    public static TranslateApplication get() {
        return Instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);

        // Debug
        // Stetho.initializeWithDefaults(this);
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(getApplicationContext());
        }
        return queue;
    }


    public void pushRequest(JsonObjectRequest request) {
        getRequestQueue().add(request);
    }

    //
}
