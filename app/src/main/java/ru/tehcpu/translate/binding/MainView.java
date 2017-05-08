package ru.tehcpu.translate.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Objects;

import ru.tehcpu.translate.R;
import ru.tehcpu.translate.TranslateApplication;
import ru.tehcpu.translate.core.Utils;
import ru.tehcpu.translate.model.Translation;
import ru.tehcpu.translate.model.Translation_Table;
import ru.tehcpu.translate.provider.DataProvider;

/**
 * Created by tehcpu on 5/6/17.
 */

public class MainView extends BaseObservable {
    String direction = "";
    String directionFrom = "";
    String directionTo = "";
    String source = "";
    String translated = "";
    int favourite = 0;
    Translation translation;

    public MainView() {
        Translation translation = DataProvider.getLastTranslation();
        this.direction = translation.getDirection();
        this.directionFrom = translation.getLanguage().get(0).getTitle();
        this.directionTo = translation.getLanguage().get(1).getTitle();
        if (Utils.getPrefs().getBoolean("hasBuff", false)) {
            this.source = translation.getSource();
            this.translated = translation.getTranslation();
            this.favourite = translation.getFavourite();
        }
        this.translation = new Translation(0L, "", "", "en-ru", 0);
    }

    @Bindable
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
        notifyPropertyChanged(BR.direction);
    }

    @Bindable
    public String getDirectionFrom() {
        return directionFrom;
    }

    public void setDirectionFrom(String directionFrom) {
        this.directionFrom = directionFrom;
        notifyPropertyChanged(BR.directionFrom);
    }

    @Bindable
    public String getDirectionTo() {
        return directionTo;
    }

    public void setDirectionTo(String directionTo) {
        this.directionTo = directionTo;
        notifyPropertyChanged(BR.directionTo);
    }

    @Bindable
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
        notifyPropertyChanged(BR.source);
    }

    @Bindable
    public String getTranslated() {
        return translated;
    }

    public void setTranslated(String translated) {
        this.translated = translated;
        notifyPropertyChanged(BR.translated);
    }

    @Bindable
    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int fav) {
        favourite = fav;
        notifyPropertyChanged(BR.favourite);
    }

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    public void saveRequest() {
        if (getTranslation().getSource().length() > 0 &&
                !DataProvider.getLastTranslation().getSource().equals(getTranslation().getSource())) {
            getTranslation().save();

            // New translation
            getTranslation().setId(0L);
            Utils.savePrefs().putBoolean("hasBuff", true).commit();
        }
    }

    public void onClick(View view) {
        setSource("");
        setTranslated("");
        setFavourite(0);
        view.findViewById(R.id.sourceTextArea).requestFocus();
        Utils.savePrefs().putBoolean("hasBuff", false).commit();
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        getTranslation().setSource(s.toString());
        DataProvider.get().translate(getTranslation(), new DataProvider.ProviderCallback() {
            @Override
            public void success(Object result) {
                Translation translationObj = (Translation) result;
                setTranslation(translationObj);
                setDirection(translationObj.getDirection());
                setDirectionFrom(translationObj.getLanguage().get(0).getTitle());
                setDirectionTo(translationObj.getLanguage().get(1).getTitle());
                setTranslated(translationObj.getTranslation());
                setFavourite(translationObj.getFavourite());
            }

            @Override
            public void error(Object result) {
                // TODO: 5/7/17 error stub
            }
        });
    }
}
