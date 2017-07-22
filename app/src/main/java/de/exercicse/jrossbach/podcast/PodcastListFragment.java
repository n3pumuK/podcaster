package de.exercicse.jrossbach.podcast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jrossbach on 15.07.17.
 */

public class PodcastListFragment extends Fragment {

    RecyclerView recyclerView;
    PodcastItemAdapter adapter;
    List<PodcastItemVieModel> itemList;

    public static PodcastListFragment newInstance(List itemsList){
        PodcastListFragment fragment = new PodcastListFragment();
        fragment.setItems(itemsList);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.podcast_list_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.podcast_recycler_view);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        adapter = new PodcastItemAdapter();
        adapter.setItems(itemList);
        recyclerView.setAdapter(adapter);
    }

    private void setItems(List<PodcastItemVieModel> items){
        if(items != null) {
            itemList = items;
        }
    }

    private void initItems(){

    }
}
