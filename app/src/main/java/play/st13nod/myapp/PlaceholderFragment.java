package play.st13nod.myapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    TextChangeListener mCallback;

    public interface TextChangeListener {
        public void onTextChange(String newText);
    }


    private static final String ARG_SECTION_NUMBER = "section_number";
    private EditText frameEditText;
    private Toast toast;

    private Button button;

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


        toast = Toast.makeText( getActivity(), "", Toast.LENGTH_SHORT);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);




        frameEditText = (EditText) rootView.findViewById(R.id.editText);
        button = (Button) rootView.findViewById(R.id.publish_button);


        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCallback(v);
             }
        });


        return rootView;
    }

    public void buttonCallback(View view) {

        String text = frameEditText.getText().toString();

        if (!text.isEmpty()) {
            toast("Sending frame named: " + text );
            mCallback.onTextChange(text);
        }else{
            toast("Cannot add empty frame :(");
        }

        frameCounter += 1;
        text = "frame" + frameCounter;
        frameEditText.setText(text);

    }
    private void toast(final String text) {
        toast.setText(text);
        toast.show();
    }



}
