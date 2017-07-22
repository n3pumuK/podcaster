package de.exercicse.jrossbach.podcast.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.exercicse.jrossbach.podcast.R;


public class PodcastItemAdapter extends RecyclerView.Adapter<PodcastItemViewHolder> {


    private List<PodcastItemVieModel> podcastItemVieModelList = new ArrayList<>();

    @Override
    public PodcastItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.podcast_item, parent, false);
        return new PodcastItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PodcastItemViewHolder holder, int position) {
        holder.itemImageUrl = podcastItemVieModelList.get(position).imageUrl;
        holder.itemTitel = podcastItemVieModelList.get(position).title;
    }

    @Override
    public int getItemCount() {
        return podcastItemVieModelList.size();
    }

    public void setItems(List<PodcastItemVieModel> podcastItems){
        this.podcastItemVieModelList = podcastItems;
    }
}
