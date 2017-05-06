package ru.tehcpu.translate.ui.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ru.tehcpu.translate.R;
import ru.tehcpu.translate.binding.MainView;
import ru.tehcpu.translate.core.Utils;
import ru.tehcpu.translate.databinding.FragmentMainBinding;
import ru.tehcpu.translate.model.Language;
import ru.tehcpu.translate.model.Translation;
import ru.tehcpu.translate.ui.component.SquareButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MainFragment extends Fragment {

    private static MainFragment Instance;
    private String lastTranslatedText = "";
    private Language directionTo;
    private View view;

    public MainFragment() {
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
        view = inflater.inflate(R.layout.fragment_main, container, false);
        FragmentMainBinding.bind(view).setData(new MainView());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final SquareButton fromButton = (SquareButton) view.findViewById(R.id.buttonFrom);
        final SquareButton toButton = (SquareButton) view.findViewById(R.id.buttonTo);
        final EditText sourceText = (EditText) view.findViewById(R.id.sourceTextArea);
        final TextView translatedText = (TextView) view.findViewById(R.id.translatedText);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static MainFragment getInstance() {
        MainFragment localInstance = Instance;
        if (localInstance == null) {
            synchronized (MainFragment.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new MainFragment();
                }
            }
        }
        return localInstance;
    }
}
