package de.exercicse.jrossbach.podcaster.feature.search.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.exercicse.jrossbach.podcaster.R
import de.exercicse.jrossbach.podcaster.base.ui.adapter.BaseViewHolder

class SavedSearchViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {
    private val itemNameTextView by lazy { itemView.findViewById<TextView>(R.id.saved_search_item_text_view) }

    override fun bind(item: String) { itemNameTextView.text = item }

    companion object {

        fun create(viewGroup: ViewGroup) =
            SavedSearchViewHolder(LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.saved_search_item, viewGroup, false))
    }
}