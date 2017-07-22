package de.exercicse.jrossbach.podcast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SearchPodcastFragment extends Fragment {

    EditText searchEditText;
    Button searchButton;

    public static SearchPodcastFragment newInstance(){
        return new SearchPodcastFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.search_podcast_fragment, container, false);
        searchEditText = rootView.findViewById(R.id.search_edit_text);
        searchEditText.setText("http://pc.argudiss.de");
        searchButton = rootView.findViewById(R.id.search_button);
        setUpSearch();
        return rootView;
    }

    private void setUpSearch(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchString = searchEditText.getText().toString().trim();
                if(!searchString.isEmpty()){
                    search(searchString);
                } else {
                    Toast.makeText(getActivity(), "Please enter a podcast URL", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void search(String searchString) {
        Toast.makeText(getActivity(), "Searching... "+ searchString, Toast.LENGTH_LONG).show();
        RetrieveXmlTask retrieveXmlTask = new RetrieveXmlTask(searchString);
        retrieveXmlTask.doInBackground(null);
        ((MainActivity)getActivity()).replaceCurrentFragment(PodcastListFragment.newInstance(retrieveXmlTask.getPodcastItems()));

    }
}
