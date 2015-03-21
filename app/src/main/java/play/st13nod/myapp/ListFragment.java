package play.st13nod.myapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by st13nod on 20/03/15.
 */
public class ListFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private ArrayAdapter<String> placesAdapter;
    private Toast toast;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListFragment newInstance(int sectionNumber) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        placesAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_goto, // The name of the layout ID.
                        R.id.list_item_goto_textview, // The ID of the textview to populate.
                        new ArrayList<String>());


        toast = Toast.makeText( getActivity(), "", Toast.LENGTH_SHORT);
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_goto);
        listView.setAdapter(placesAdapter);
        // We'll call our MainActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                final String text2 =  placesAdapter.getItem(position);
                final String text = ((TextView)view).getText().toString();
                placesAdapter.remove(text2);
                toast("You removed: " + text2 );
            }
        });



        return rootView;
    }
    private void toast(final String text) {
        toast.setText(text);
        toast.show();
    }
    public void addListItem (String text) {
        placesAdapter.add(text);
    }
}

