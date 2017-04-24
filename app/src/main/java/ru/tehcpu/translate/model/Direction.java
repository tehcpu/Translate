package ru.tehcpu.translate.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;

import ru.tehcpu.translate.core.Database;

/**
 * Created by tehcpu on 4/24/17.
 */

@Table(database = Database.class)
public class Direction {

    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    long from;

    @Column
    long to;

}