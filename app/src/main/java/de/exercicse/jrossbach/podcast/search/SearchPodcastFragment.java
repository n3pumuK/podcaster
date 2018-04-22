package de.exercicse.jrossbach.podcast.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.exercicse.jrossbach.podcast.MainActivity;
import de.exercicse.jrossbach.podcast.R;

import java.util.ArrayList;
import java.util.List;


public class SearchPodcastFragment extends Fragment {

    @BindView(R.id.search_edit_text)
    EditText searchEditText;
    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.saved_search_recycler_view)
    RecyclerView savedSearchRecyclerView;

    private SavedSearchAdapter adapter;
    private List<String> savedSearchStrings = new ArrayList<>();

    public static SearchPodcastFragment newInstance() {
        return new SearchPodcastFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.search_podcast_fragment, container, false);
        ButterKnife.bind(this, rootView);
        searchEditText.setText("http://pc.argudiss.de");
        //TODO: persist state after application died
        if (savedInstanceState != null) {
            savedSearchStrings = savedInstanceState.getParcelable("last_search_strings");
        }
        setUpSearch();
        return rootView;
    }

    private void setUpSearch() {
        adapter = new SavedSearchAdapter();
        savedSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setItems(savedSearchStrings);
        savedSearchRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchString = searchEditText.getText().toString().trim();
                if (!searchString.isEmpty()) {
                    search(searchString);
                    if (!savedSearchStrings.contains(searchString)) {
                        savedSearchStrings.add(searchString);
                    }

                } else {
                    Toast.makeText(getActivity(), "Please enter a podcast URL", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
     //   outState.putParcelable("last_search_strings", Parcels.wrap(savedSearchStrings));
    }

    private void search(String searchString) {
        PodcastListFragment podcastListFragment = PodcastListFragment.newInstance(searchString);
        ((MainActivity) getActivity()).replaceCurrentFragment(podcastListFragment);
    }
}
