package de.exercicse.jrossbach.podcast.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.exercicse.jrossbach.podcast.R;

public class SavedSearchAdapter extends RecyclerView.Adapter<SavedSearchAdapter.SavedSearchViewHolder> {

    private List<String> savedSearchStrings = new ArrayList<>();

    @Override
    public SavedSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_search_item, parent, false);
        return new SavedSearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SavedSearchViewHolder holder, int position) {
        holder.itemNameTextView.setText(savedSearchStrings.get(position));
    }

    @Override
    public int getItemCount() {
        return savedSearchStrings.size();
    }

    public void setItems(List<String> savedSearchStrings){
        this.savedSearchStrings = savedSearchStrings;
    }


    public class SavedSearchViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.saved_search_item_card_view)
        CardView itemCardView;
        @BindView(R.id.saved_search_item_text_view)
        TextView itemNameTextView;

        public SavedSearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.saved_search_item_card_view)
        void onClickSavedSearch(){
            //TODO: decide on proper behaviour
        }
    }
}
