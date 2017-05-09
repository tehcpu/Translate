package ru.tehcpu.translate.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;

import java.util.List;

import ru.tehcpu.translate.R;
import ru.tehcpu.translate.adapter.LanguageListAdpater;
import ru.tehcpu.translate.model.Language;
import ru.tehcpu.translate.provider.DataProvider;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class LanguageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private List<Language> data;
    private LanguageListAdpater mAdapter;
    public int direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_language);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        direction = bundle.getInt("dir");
        toolbar.setTitle(direction == 0 ? "Source language" : "Target language");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.languageList);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.hasFixedSize();

        mAdapter = new LanguageListAdpater(DataProvider.getRecentLanguages(), DataProvider.getLanguages(), this);

        //Apply this adapter to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
