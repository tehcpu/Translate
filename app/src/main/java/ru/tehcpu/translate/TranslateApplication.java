package ru.tehcpu.translate;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;

import ru.tehcpu.translate.core.Utils;
import ru.tehcpu.translate.model.Language;
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

        // Debug
        // Stetho.initializeWithDefaults(this);
    }
}
