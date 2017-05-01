package ru.tehcpu.translate.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;

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
    long direction;

    @Column
    String fake_direction;

    // Oh SHI~

    public String getFake_direction() {
        return fake_direction;
    }

    public void setFake_direction(String fake_direction) {
        this.fake_direction = fake_direction;
    }

    @Column(defaultValue = "0")
    int favourite;

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

    public long getDirection() {
        return direction;
    }

    public void setDirection(long direction) {
        this.direction = direction;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    //

    public ArrayList<Language> getLanguage() {
        ArrayList<Language> languages = new ArrayList<>();
        Direction dir = SQLite.select().from(Direction.class).where(Language_Table.id.eq(this.direction)).querySingle();
        if (dir != null && dir.exists()) {
            languages.add(SQLite.select().from(Language.class).where(Language_Table.id.eq(dir.from)).querySingle());
            languages.add(SQLite.select().from(Language.class).where(Language_Table.id.eq(dir.to)).querySingle());
        }
        return languages;
    }
}