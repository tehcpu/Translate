package ru.tehcpu.translate.provider;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.tehcpu.translate.core.Database;
import ru.tehcpu.translate.model.Language;
import ru.tehcpu.translate.model.LanguageResponse;
import ru.tehcpu.translate.model.Language_Table;
import ru.tehcpu.translate.model.Translation;
import ru.tehcpu.translate.model.TranslationResponse;
import ru.tehcpu.translate.model.Translation_Table;

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
                    // Truncate table first
                    DatabaseWrapper db = FlowManager.getDatabase(Database.class).getWritableDatabase();
                    ModelAdapter myAdapter = FlowManager.getModelAdapter(Language.class);
                    db.execSQL("DROP TABLE IF EXISTS " + myAdapter.getTableName());
                    db.execSQL(myAdapter.getCreationQuery());

                    // Fill with new data
                    ArrayList<Language> languages = response.body().getLangs();
                    FlowManager.getDatabase(Database.class).beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                            new ProcessModelTransaction.ProcessModel<Language>() {
                                @Override
                                public void processModel(Language language, DatabaseWrapper wrapper) {
                                    language.save();
                                }
                            })
                            .addAll(languages).build()).build().execute();
                } else {
                    // TODO: 5/2/17 error handling
                }
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

        text = "Hello world";

        Call<TranslationResponse> call = apiService.translate(lang, text, ApiProvider.API_KEY);
        final String finalText = text;
        call.enqueue(new Callback<TranslationResponse>() {
            @Override
            public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                if (response.code() == 200) {
                    Translation translation = new Translation();
                    translation.setSource(finalText);
                    String result = (response.body().getText().size() > 0)? response.body().getText().get(0): "";
                    translation.setTranslation(result);
                    translation.setDirection(response.body().getLang());
                    translation.save();
                } else {
                    // TODO: 5/3/17 errors
                }
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
