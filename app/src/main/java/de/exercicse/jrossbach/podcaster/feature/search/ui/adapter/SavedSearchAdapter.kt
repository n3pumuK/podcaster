package de.exercicse.jrossbach.podcaster.feature.search.ui.adapter

import android.view.ViewGroup
import de.exercicse.jrossbach.podcaster.base.ui.adapter.BaseAdapter

class SavedSearchAdapter : BaseAdapter<String, SavedSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedSearchViewHolder {
        return SavedSearchViewHolder.create(parent)
    }
}