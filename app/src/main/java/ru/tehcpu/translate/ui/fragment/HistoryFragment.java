package ru.tehcpu.translate.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.tehcpu.translate.R;
import ru.tehcpu.translate.TranslateApplication;
import ru.tehcpu.translate.adapter.HistoryPagerAdapter;
import ru.tehcpu.translate.ui.MainActivity;
import ru.tehcpu.translate.ui.fragment.history.HistoryFavourite;
import ru.tehcpu.translate.ui.fragment.history.HistoryMain;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HistoryFragment extends Fragment {
    //
    private static HistoryFragment Instance;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
    final ViewPager historyViewPager = (ViewPager) view.findViewById(R.id.historyPager);
       historyViewPager.setAdapter(new HistoryPagerAdapter(getActivity().getApplication().getApplicationContext(), getFragmentManager()));
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.historyTabs);
        tabLayout.setupWithViewPager(historyViewPager);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //

    public static HistoryFragment getInstance() {
        HistoryFragment localInstance = Instance;
        if (localInstance == null) {
            synchronized (HistoryFragment.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new HistoryFragment();
                }
            }
        }
        return localInstance;
    }
}
