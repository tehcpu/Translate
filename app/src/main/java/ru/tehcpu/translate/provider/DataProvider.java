package ru.tehcpu.translate.provider;

import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.tehcpu.translate.model.Language;
import ru.tehcpu.translate.model.LanguageResponse;
import ru.tehcpu.translate.model.Translation;
import ru.tehcpu.translate.model.TranslationResponse;

/**
 * Created by tehcpu on 5/2/17.
 */

public class DataProvider {
    private static final String TAG = "[ == DataProvider == ]";
    private static DataProvider Instance;
    private final ApiProvider.ApiEndpoint apiService;

    public DataProvider() {
        apiService = ApiProvider.get().create(ApiProvider.ApiEndpoint.class);
    }

    public void getLangs(String ui) {
        Call<LanguageResponse> call = apiService.getLang(ui, ApiProvider.API_KEY);
        call.enqueue(new Callback<LanguageResponse>() {
            @Override
            public void onResponse(Call<LanguageResponse> call, Response<LanguageResponse> response) {
                if (response.code() == 200) {
                    ArrayList<Language> languages = response.body().getLangs();
                } else {
                    // TODO: 5/2/17 error handling
                }
                //SQLite.insert().
            }

            @Override
            public void onFailure(Call<LanguageResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public void translate(String text) {
        // TODO: 5/2/17 to code real lang detection
        String lang = "en-ru";

        text = "Hello world!";

        Call<TranslationResponse> call = apiService.translate(lang, text, ApiProvider.API_KEY);
        call.enqueue(new Callback<TranslationResponse>() {
            @Override
            public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {


                    Log.d(TAG, "Number of movies received: " + response.body().getText());


                //SQLite.insert().
            }

            @Override
            public void onFailure(Call<TranslationResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    //
    public static DataProvider get() {
        DataProvider localInstance = Instance;
        if (localInstance == null) {
            synchronized (DataProvider.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new DataProvider();
                }
            }
        }
        return localInstance;
    }
}
