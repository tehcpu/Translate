package ru.tehcpu.translate;

import android.app.Application;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.tehcpu.translate.provider.ApiProvider;
import ru.tehcpu.translate.model.Language;
import ru.tehcpu.translate.model.LanguageResponse;
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
        DataProvider.get().getLangs("ru");
        DataProvider.get().translate("qweqwe");

        // Debug
        // // Stetho.initializeWithDefaults(this);
    }
}
