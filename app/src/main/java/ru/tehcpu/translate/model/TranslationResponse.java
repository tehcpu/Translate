package ru.tehcpu.translate.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tehcpu on 5/2/17.
 */

public class TranslationResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("lang")
    private String lang;

    @SerializedName("text")
    private ArrayList<String> text;

    public TranslationResponse() {
    }

    public TranslationResponse(int code, String lang, ArrayList<String> text) {
        this.code = code;
        this.lang = lang;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }
}
