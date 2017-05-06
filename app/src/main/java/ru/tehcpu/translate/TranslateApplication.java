package ru.tehcpu.translate;

import android.app.Application;
import com.raizlabs.android.dbflow.config.FlowManager;

import ru.tehcpu.translate.provider.DataProvider;

/**
 * Created by tehcpu on 4/2/17.
 */

public class TranslateApplication extends Application {
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

        // testing
        DataProvider.get().getLangs("en");


        // Debug
        // Stetho.initializeWithDefaults(this);
    }
}
