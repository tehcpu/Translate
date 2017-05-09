package ru.tehcpu.translate.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

import java.util.ArrayList;
import java.util.List;

import ru.tehcpu.translate.R;
import ru.tehcpu.translate.TranslateApplication;
import ru.tehcpu.translate.binding.MainView;
import ru.tehcpu.translate.core.Utils;
import ru.tehcpu.translate.databinding.FragmentMainBinding;
import ru.tehcpu.translate.model.Language;
import ru.tehcpu.translate.model.Translation;
import ru.tehcpu.translate.ui.LanguageActivity;
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
    private FragmentMainBinding binding;
    private Unregistrar KeyboardWatchdog;
    private Language l;

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
        binding = FragmentMainBinding.bind(view);
        binding.setData(new MainView(view));
        KeyboardWatchdog = KeyboardVisibilityEvent.registerEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (!isOpen) {
                            binding.sourceTextArea.clearFocus();
                            binding.getData().saveRequest();
                            binding.sourceTextWrapper
                                    .setBackground(ContextCompat.getDrawable(TranslateApplication.get(),
                                            R.drawable.background_field_source));
                        } else {
                            binding.sourceTextWrapper
                                    .setBackground(ContextCompat.getDrawable(TranslateApplication.get(),
                                            R.drawable.background_field_source_a));
                        }
                    }
                });

        binding.buttonFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LanguageActivity.class);
                i.putExtra("dir", 0);
                i.putExtra("lang", binding.getData().getTranslation().getDirection());
                startActivityForResult(i, 0);
            }
        });

        binding.buttonTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LanguageActivity.class);
                i.putExtra("dir", 1);
                i.putExtra("lang", binding.getData().getTranslation().getDirection());
                startActivityForResult(i, 1);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String dir;
        switch (resultCode) {
            case 100: l = new Gson().fromJson(data.getExtras().getString("language", null), Language.class);
                binding.getData().setDirectionFrom(l.getTitle());
                dir = binding.getData().getTranslation().getDirection();
                dir = l.getKey()+"-"+dir.split("-")[1];
                binding.getData().setDirection(dir);
                binding.getData().getTranslation().setDirection(dir);
                break;
            case 101: l = new Gson().fromJson(data.getExtras().getString("language", null), Language.class);
                binding.getData().setDirectionTo(l.getTitle());
                dir = binding.getData().getTranslation().getDirection();
                dir = dir.split("-")[0]+"-"+l.getKey();
                binding.getData().setDirection(dir);
                binding.getData().getTranslation().setDirection(dir);
                break;
            default:break;
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyboardWatchdog.unregister();
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
