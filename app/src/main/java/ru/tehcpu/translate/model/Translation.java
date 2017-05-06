package ru.tehcpu.translate.model;

import android.util.Log;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

import ru.tehcpu.translate.core.Database;

/**
 * Created by tehcpu on 4/24/17.
 */

@Table(database = Database.class)
public class Translation extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String source;

    @Column
    String translation;

    @Column
    String direction;

    @Column(defaultValue = "0")
    int favourite;

    public Translation() {
    }

    public Translation(long id, String source, String translation, String direction, int favourite) {
        this.id = id;
        this.source = source;
        this.translation = translation;
        this.direction = direction;
        this.favourite = favourite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    // For tabs

    public List<Language> getLanguage() {
        List<Language> langs = SQLite.select().from(Language.class)
                .where(Language_Table.key.eq(this.direction.split("-")[0]))
                .or(Language_Table.key.eq(this.direction.split("-")[1])).queryList();
        if (langs.size() < 2) langs.add(langs.get(0));
        return langs;
    }
}