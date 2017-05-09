package ru.tehcpu.translate.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageButton;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Comparator;
        import java.util.HashSet;
        import java.util.List;

        import ru.tehcpu.translate.R;
        import ru.tehcpu.translate.TranslateApplication;
        import ru.tehcpu.translate.model.Language;
        import ru.tehcpu.translate.model.Translation;
        import ru.tehcpu.translate.provider.DataProvider;
import ru.tehcpu.translate.ui.LanguageActivity;

/**
 * Created by tehcpu on 5/9/17.
 */

public class LanguageListAdpater extends RecyclerView.Adapter {
    private final LanguageListAdpater Instance;
    private final LanguageActivity activityInstance;
    public ArrayList<Language> mDataset = new ArrayList<>();

    public LanguageListAdpater(List<Language> recent, List<Language> all, LanguageActivity languageActivity) {
        activityInstance = languageActivity;
        if (recent.size() > 0) {
            mDataset.add(new Language(-100L, "", "Recently used"));
            mDataset.addAll(recent);
        }
        mDataset.add(new Language(-200L, "", "All languages"));
        mDataset.addAll(all);
        Instance = this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_language, parent, false);
        return new LanguageListItem(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LanguageListItem dsh = (LanguageListItem) holder;
        RelativeLayout wrapper = dsh.wrapper;
        TextView title =  dsh.title;

        Language item = mDataset.get(position);

        title.setText(item.getTitle());

        if (item.getId() < 0) {
            title.setTextSize(12L);
            title.setAllCaps(true);
            title.setTextColor(ContextCompat.getColor(TranslateApplication.get(), R.color.buttonImages));
        } else {
            title.setTextSize(16L);
            title.setAllCaps(false);
            title.setTextColor(ContextCompat.getColor(TranslateApplication.get(), R.color.titleColor));
            dsh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("qweqwe", String.valueOf(position));
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("language", new Gson().toJson(mDataset.get(position)));
                    activityInstance.setResult(activityInstance.direction+100, returnIntent);
                    activityInstance.finish();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class LanguageListItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected RelativeLayout wrapper;
        protected TextView title;

        public LanguageListItem(View view) {
            super(view);
            this.wrapper = (RelativeLayout) view.findViewWithTag("wrapper");
            this.title = (TextView) view.findViewWithTag("title");
        }

        @Override
        public void onClick(View view) {

        }
    }
}