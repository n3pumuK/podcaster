package de.exercicse.jrossbach.podcast.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.exercicse.jrossbach.podcast.R;


public class PodcastItemAdapter extends RecyclerView.Adapter<PodcastItemViewHolder> {

    private List<PodcastItemViewModel> podcastItemViewModelList = new ArrayList<>();
    private PodcastChannelView podcastChannelView;

    public PodcastItemAdapter(PodcastChannelView podcastChannelView) {
        this.podcastChannelView = podcastChannelView;
    }

    @Override
    public PodcastItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.podcast_item, parent, false);
        return new PodcastItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PodcastItemViewHolder holder, int position) {
        final PodcastItemViewModel podcastItemViewModel = podcastItemViewModelList.get(position);
        holder.itemTitleTextView.setText(podcastItemViewModel.getTitle());
        holder.itemCategoryTextView.setText(podcastItemViewModel.getCategory());
        holder.itemDateTextView.setText(podcastItemViewModel.getPublishingDate());
        String imageUrl = podcastItemViewModel.getImageUrl();
        if (imageUrl != null) {
            Glide.with(holder.cardView)
                    .load(imageUrl)
                    .into(holder.itemImage);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                podcastChannelView.onItemClick(podcastItemViewModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return podcastItemViewModelList.size();
    }

    public void setItems(List<PodcastItemViewModel> podcastItems) {
        this.podcastItemViewModelList = podcastItems;
    }
}
