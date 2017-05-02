package ru.tehcpu.translate.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import ru.tehcpu.translate.core.Database;

/**
 * Created by tehcpu on 4/24/17.
 */

@Table(database = Database.class)
public class Language extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String key;

    @Column
    String title;

    public Language() {}

    public Language(long id, String key, String title) {
        this.id = id;
        this.key = key;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}