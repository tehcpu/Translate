package ru.tehcpu.translate.provider;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.tehcpu.translate.model.LanguageResponse;
import ru.tehcpu.translate.model.TranslationResponse;

/**
 * Created by tehcpu on 5/2/17.
 */

public class ApiProvider {
    private static Retrofit retrofit;

    private static final String TAG = "[ :: ApiProvider :: ]";
    private static final String API_VERSION = "v1.5";
    static final String API_KEY = "trnsl.1.1.20170316T190128Z.adc95438ef4b279b.6481823440c28fd726fe5319dddaa6ed70c74dcb";
    private static final String API_URL = "https://translate.yandex.net/api/"+API_VERSION+"/tr.json/";

    interface ApiEndpoint {
        @GET("getLangs")
        Call<LanguageResponse> getLang(@Query("ui") String lang, @Query("key") String key);

        @GET("translate")
        Call<TranslationResponse> translate(@Query("lang") String lang, @Query("text") String text, @Query("key") String key);
    }

    static Retrofit get() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
