package ru.tehcpu.translate.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tehcpu on 5/2/17.
 */

public class LanguageResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("langs")
    private LinkedHashMap<String, String> langs;

    public LanguageResponse() {}

    public LanguageResponse(int code, LinkedHashMap<String, String> langs) {
        this.code = code;
        this.langs = langs;
    }

    public ArrayList<Language> getLangs() {
        ArrayList<Language> languages = new ArrayList<>();
        for(Map.Entry<String, String> entry : langs.entrySet()) {
            languages.add(new Language(0L, entry.getKey(), entry.getValue()));
        }
        Collections.sort(languages, new Comparator<Language>() {
            public int compare(Language l1, Language l2) {
                return l1.getTitle().compareTo(l2.getTitle());
            }
        });
        return languages;
    }

    public void setLangs(LinkedHashMap<String, String> langs) {
        this.langs = langs;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
