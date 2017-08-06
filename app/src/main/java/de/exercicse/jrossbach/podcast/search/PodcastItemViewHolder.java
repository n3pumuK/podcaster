package de.exercicse.jrossbach.podcast.search;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.exercicse.jrossbach.podcast.R;

/**
 * Created by jrossbach on 16.07.17.
 */

public class PodcastItemViewHolder<PodcastItemViewModel> extends RecyclerView.ViewHolder {

    ImageView itemImage;
    TextView itemTitleTextView;
    TextView itemCategoryTextView;
    CardView cardView;
    TextView itemDateTextView;


    public PodcastItemViewHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.item_card_view);
        itemImage = itemView.findViewById(R.id.item_image);
        itemTitleTextView = itemView.findViewById(R.id.item_title_text_view);
        itemCategoryTextView = itemView.findViewById(R.id.item_category_text_view);
        itemDateTextView = itemView.findViewById(R.id.item_date_text_view);
    }

}
