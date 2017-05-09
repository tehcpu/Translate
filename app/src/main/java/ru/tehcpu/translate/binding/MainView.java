package ru.tehcpu.translate.binding;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.content.ContextCompat;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
    private final ImageButton cleanButton;
    private final LinearLayout actionButtons;
    String direction = "";
    String directionFrom = "";
    String directionTo = "";
    String source = "";
    String translated = "";
    int favourite = 0;
    Translation translation;
    View view;

    public MainView(View view) {
        this.view = view;
        this.cleanButton = (ImageButton) view.findViewById(R.id.buttonCleanSource);
        this.actionButtons = (LinearLayout) view.findViewById(R.id.actionButtons);

        Translation translation = DataProvider.getLastTranslation();
        this.direction = translation.getDirection();
        this.directionFrom = translation.getLanguage().get(0).getTitle();
        this.directionTo = translation.getLanguage().get(1).getTitle();
        if (Utils.getPrefs().getBoolean("hasBuff", false)) {
            this.source = translation.getSource();
            this.translated = translation.getTranslation();
            this.favourite = translation.getFavourite();
            cleanButton.setAlpha(1L);
            actionButtons.setAlpha(1L);
        }
        this.translation = new Translation(0L, source, translated, direction, 0);
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
        switch (view.getTag().toString()) {
            case "stw":
                view.findViewById(R.id.sourceTextArea).requestFocus();
                view.setBackground(ContextCompat.getDrawable(TranslateApplication.get(),
                            R.drawable.background_field_source_a));
                InputMethodManager imm = (InputMethodManager) TranslateApplication.get().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                break;
            default:
                setSource("");
                setTranslated("");
                setFavourite(0);
                view.requestFocus();
                Utils.savePrefs().putBoolean("hasBuff", false).commit();
        }
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
                setFavourite(0);
            }

            @Override
            public void error(Object result) {
                // TODO: 5/7/17 error stub
            }
        });
        // necessary reset
        if (s.length() == 0) {
            setTranslated("");
            getTranslation().setTranslation("");
            getTranslation().setSource("");
            actionButtons.setAlpha(0L);
            cleanButton.setAlpha(0L);
        } else {
            actionButtons.setAlpha(1L);
            cleanButton.setAlpha(1L);
        }
    }
}
