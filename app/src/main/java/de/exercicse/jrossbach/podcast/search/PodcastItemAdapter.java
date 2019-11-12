package de.exercicse.jrossbach.podcast.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.exercicse.jrossbach.podcast.R;


public class PodcastItemAdapter extends RecyclerView.Adapter<PodcastItemAdapter.PodcastItemViewHolder> {

    private List<PodcastItemViewModel> podcastItemViewModelList = new ArrayList<>();
    private PodcastChannelView podcastChannelView;

    PodcastItemAdapter(PodcastChannelView podcastChannelView) {
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
        holder.initView(podcastItemViewModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return podcastItemViewModelList.size();
    }

    void setItems(List<PodcastItemViewModel> podcastItems) {
        this.podcastItemViewModelList = podcastItems;
    }

    class PodcastItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_image)
        ImageView itemImage;
        @BindView(R.id.item_title_text_view)
        TextView itemTitleTextView;
        @BindView(R.id.item_category_text_view)
        TextView itemCategoryTextView;
        @BindView(R.id.item_card_view)
        CardView cardView;
        @BindView(R.id.item_date_text_view)
        TextView itemDateTextView;
        PodcastItemViewModel podcastItemViewModel;

        PodcastItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void initView(PodcastItemViewModel podcastItemViewModel){
            this.podcastItemViewModel = podcastItemViewModel;
            itemTitleTextView.setText(podcastItemViewModel.getTitle());
            itemCategoryTextView.setText(podcastItemViewModel.getCategory());
            itemDateTextView.setText(podcastItemViewModel.getPublishingDate());
            String imageUrl = podcastItemViewModel.getImageUrl();
            if (imageUrl != null) {
                Glide.with(cardView)
                        .load(imageUrl)
                        .into(itemImage);
            }
        }

        @OnClick(R.id.item_card_view)
        void onItemClick(){
            podcastChannelView.onItemClick(podcastItemViewModel);
        }

    }
}
