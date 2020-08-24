package de.exercicse.jrossbach.podcaster.feature.search.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.squareup.picasso.Picasso
import de.exercicse.jrossbach.podcaster.R
import de.exercicse.jrossbach.podcaster.base.ui.adapter.BaseViewHolder
import de.exercicse.jrossbach.podcaster.feature.search.ui.PodCastItemClickListener
import de.exercicse.jrossbach.podcaster.feature.search.ui.PodcastItemViewModel

class PodcastItemViewHolder(
    itemView: View,
    private val clickListener: PodCastItemClickListener
) : BaseViewHolder<PodcastItemViewModel>(itemView) {

    private val itemImage by lazy { itemView.findViewById<ImageView>(R.id.item_image) }
    private val itemTitleTextView by lazy { itemView.findViewById<TextView>(R.id.item_title_text_view) }
    private val itemCategoryTextView by lazy { itemView.findViewById<TextView>(R.id.item_category_text_view) }
    private val cardView by lazy { itemView.findViewById<CardView>(R.id.item_card_view) }
    private val itemDateTextView by lazy { itemView.findViewById<TextView>(R.id.item_date_text_view) }

    override fun bind(item: PodcastItemViewModel) {
        itemTitleTextView.text = item.title
        itemCategoryTextView.text = item.category
        itemDateTextView.text = item.publishingDate
        item.imageUrl?.let {
            Picasso.get()
                .load(it)
                .into(itemImage)
        }
        cardView.setOnClickListener { item.id?.let { it1 -> clickListener.onChannelItemClicked(it1) } }
    }

    companion object {

        fun create(
            viewGroup: ViewGroup,
            clickListener: PodCastItemClickListener
        ) =
            PodcastItemViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.podcast_item, viewGroup, false),
                clickListener
            )
    }
}