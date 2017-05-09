package ru.tehcpu.translate.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ru.tehcpu.translate.R;
import ru.tehcpu.translate.model.Translation;
import ru.tehcpu.translate.provider.DataProvider;

/**
 * Created by tehcpu on 4/23/17.
 */

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {
    private final HistoryListAdapter Instance;
    private List<Translation> mDataset;
    private boolean full = false;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout itemLayout;
        public ViewHolder(RelativeLayout v) {
            super(v);
            itemLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HistoryListAdapter(List<Translation> myDataset) {
        mDataset = myDataset;
        Instance = this;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HistoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView sourceText = (TextView) holder.itemLayout.findViewWithTag("source");
        TextView translationText = (TextView) holder.itemLayout.findViewWithTag("translation");
        TextView direction = (TextView) holder.itemLayout.findViewWithTag("direction");
        ImageButton favourite = (ImageButton) holder.itemLayout.findViewWithTag("favourite");

        //
        Translation item = mDataset.get(position);
        sourceText.setText(item.getSource());
        translationText.setText(item.getTranslation());
        direction.setText(item.getDirection());

        if ((position >= getItemCount() - 1) && !full) loadMore();
    }

    private void loadMore() {
        List<Translation> moreData = DataProvider.getHistory(mDataset.get(mDataset.size() - 1).getId(), false);
        if (moreData.size() == 0) full = true;
        mDataset.addAll(moreData);
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                Instance.notifyDataSetChanged();
            }
        };

        handler.post(r);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}