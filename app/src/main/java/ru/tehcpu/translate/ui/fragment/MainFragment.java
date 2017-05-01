package ru.tehcpu.translate.ui.fragment;

import android.content.Context;
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
import ru.tehcpu.translate.core.Utils;
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final SquareButton fromButton = (SquareButton) view.findViewById(R.id.buttonFrom);
        final SquareButton toButton = (SquareButton) view.findViewById(R.id.buttonTo);
        final EditText sourceText = (EditText) view.findViewById(R.id.sourceTextArea);
        final TextView translatedText = (TextView) view.findViewById(R.id.translatedText);

        Utils.loadLanguages(getActivity(), new Utils.onLanguageCallback() {
            @Override
            public void callback(ArrayList<Language> languages, Translation translation) {
                fromButton.setText(languages.get(0).getTitle());
                toButton.setText(languages.get(1).getTitle());
                directionTo = languages.get(1);
                if (translation != null) {
                    sourceText.setText(translation.getSource());
                    translatedText.setText(translation.getTranslation());
                }
            }
        });

        sourceText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Utils.translate(new Utils.onTranslateCallback() {
                    @Override
                    public void callback(ArrayList<Language> languages, Translation translation) {
                        fromButton.setText(languages.get(0).getTitle());
                        toButton.setText(languages.get(1).getTitle());
                        translatedText.setText(translation.getTranslation());
                    }
                }, sourceText.getText().toString(), (directionTo != null) ? directionTo.getKey() : "ru");
            }
        });

        sourceText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SPACE) {
                    Utils.translate(new Utils.onTranslateCallback() {
                        @Override
                        public void callback(ArrayList<Language> languages, Translation translation) {
                            fromButton.setText(languages.get(0).getTitle());
                            toButton.setText(languages.get(1).getTitle());
                            translatedText.setText(translation.getTranslation());
                        }
                    }, sourceText.getText().toString(), directionTo.getKey());
                }
                return false;
            }
        });
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
