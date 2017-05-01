package ru.tehcpu.translate.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;

import ru.tehcpu.translate.core.Database;

/**
 * Created by tehcpu on 4/24/17.
 */

@Table(database = Database.class)
public class Direction extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    long from;

    @Column
    long to;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public ArrayList<Language> getLanguage() {
        ArrayList<Language> languages = new ArrayList<>();
        languages.add(SQLite.select().from(Language.class).where(Language_Table.id.eq(this.from)).querySingle());
        languages.add(SQLite.select().from(Language.class).where(Language_Table.id.eq(this.to)).querySingle());
        return languages;
    }
}