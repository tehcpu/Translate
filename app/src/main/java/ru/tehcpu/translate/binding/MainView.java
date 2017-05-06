package ru.tehcpu.translate.binding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Objects;

import ru.tehcpu.translate.TranslateApplication;
import ru.tehcpu.translate.model.Translation;
import ru.tehcpu.translate.model.Translation_Table;
import ru.tehcpu.translate.provider.DataProvider;

/**
 * Created by tehcpu on 5/6/17.
 */

public class MainView extends BaseObservable {
    String direction;
    String directionFrom;
    String directionTo;
    String source;
    String translated;
    int favourite;
    Translation translation;

    public MainView() {
        Translation translation = DataProvider.getLastTranslation();
        this.direction = translation.getDirection();
        this.directionFrom = translation.getLanguage().get(0).getTitle();
        this.directionTo = translation.getLanguage().get(1).getTitle();
        this.source = translation.getSource();
        this.translated = translation.getTranslation();
        this.favourite = translation.getFavourite();
        this.translation = translation;
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

    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        if (getTranslation().getSource().length() >= count) {
            Translation current = null;
            if (String.valueOf(s).length() > 1 && getTranslation().getSource().length() >= count)
                current =
                        (String.valueOf(s).substring(0, count)
                                .startsWith(getTranslation().getSource()
                                        .substring(0, count))) ? getTranslation() : null;
            if (getTranslation().getSource().length() < String.valueOf(s).length()) {
                DataProvider.get().translate(getTranslation().getDirection().split("-")[1], s.toString(), current, new DataProvider.ProviderCallback() {
                    @Override
                    public void success(Object result) {
                        Translation translationObj = (Translation) result;
                        setTranslation(translationObj);
                        setDirection(translationObj.getDirection());
                        setDirectionFrom(translationObj.getLanguage().get(0).getTitle());
                        setDirectionTo(translationObj.getLanguage().get(1).getTitle());
                        setTranslated(translationObj.getTranslation());
                        setFavourite(translationObj.getFavourite());

                        Toast.makeText(TranslateApplication.get().getApplicationContext(), translationObj.getDirection(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void error(Object result) {

                    }
                });
            } else {
                getTranslation().setSource(String.valueOf(s));
            }
            Log.d("123456", String.valueOf(getTranslation().getId()));
//        }
    }
}
