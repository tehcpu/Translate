package ru.tehcpu.translate.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.tehcpu.translate.ui.fragment.history.HistoryFavourite;
import ru.tehcpu.translate.ui.fragment.history.HistoryMain;

/**
 * Created by tehcpu on 4/23/17.
 */

public class HistoryPagerAdapter extends FragmentPagerAdapter {
    Context ctxt=null;

    public HistoryPagerAdapter(Context ctxt, FragmentManager mgr) {
        super(mgr);
        this.ctxt=ctxt;
    }

    @Override
    public int getCount() {
        return(2);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return(HistoryMain.getInstance());
            case 1: return(HistoryFavourite.getInstance());
            default: break;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "History";
            case 1: return "Favourites";
            default: break;
        }
        return null;
    }
}