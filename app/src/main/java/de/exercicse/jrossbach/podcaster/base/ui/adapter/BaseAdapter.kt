package de.exercicse.jrossbach.podcaster.base.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<ItemType, ViewHolderType : BaseViewHolder<ItemType>> :
    RecyclerView.Adapter<ViewHolderType>() {

    private val itemList: MutableList<ItemType> = ArrayList()

    var items: List<ItemType>
        get() = itemList
        set(items) {
            itemList.clear()
            itemList.addAll(items)
            notifyDataSetChanged()
        }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderType

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolderType, position: Int) {
        holder.bind(items[position])
    }
}