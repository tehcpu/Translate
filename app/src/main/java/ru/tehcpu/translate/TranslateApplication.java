package ru.tehcpu.translate;

/**
 * Created by tehcpu on 4/2/17.
 */

public class TranslateApplication {
    private static TranslateApplication instance;

    public TranslateApplication(){
        super();
        instance = this;
    }

    public static TranslateApplication get() {
        return instance;
    }
}
