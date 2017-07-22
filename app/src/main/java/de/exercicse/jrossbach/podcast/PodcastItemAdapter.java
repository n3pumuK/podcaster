package de.exercicse.jrossbach.podcast;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jrossbach on 15.07.17.
 */

public class PodcastItemAdapter extends RecyclerView.Adapter<PodcastItemViewHolder> {


    private List<PodcastItemVieModel> podcastItemVieModelList;

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
        notifyDataSetChanged();
    }
}
