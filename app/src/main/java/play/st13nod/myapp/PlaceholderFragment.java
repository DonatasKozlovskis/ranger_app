package play.st13nod.myapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    TextChangeListener mCallback;

    // interface to communicate with container activity
    public interface TextChangeListener {
        public void onTextChange(String newText);
    }

    private static final String ARG_SECTION_NUMBER = "section_number";

    //ui objects
    private EditText frameEditText;
    private Button button;
    private ImageButton buttonSpeak;

    //toast object
    private Toast toast;

    private MyNode myNode;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private int frameCounter = 0;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TextChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myNode = new MyNode();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);



        toast = Toast.makeText( getActivity(), "", Toast.LENGTH_SHORT);

        frameEditText = (EditText) rootView.findViewById(R.id.editText);
        button = (Button) rootView.findViewById(R.id.publish_button);
        buttonSpeak = (ImageButton) rootView.findViewById(R.id.btnSpeak);

        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCallback(v);
             }
        });
        buttonSpeak.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        return rootView;
    }

    public void buttonCallback(View view) {

        String text = frameEditText.getText().toString();

        if (!text.isEmpty()) {
            toast("Sending frame named: " + text );
            myNode.publishStringName(text);
            mCallback.onTextChange(text);
        }else{
            toast("Cannot add empty frame :(");
        }

        frameCounter += 1;
        text = "frame" + frameCounter;
        frameEditText.setText(text);

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            toast( getString(R.string.speech_not_supported));
        }
    }

    private void toast(final String text) {
        toast.setText(text);
        toast.show();
    }

    /**
     * Receiving speech input
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    frameEditText.setText(result.get(0));
                }
                break;
            }

        }
    }


}
