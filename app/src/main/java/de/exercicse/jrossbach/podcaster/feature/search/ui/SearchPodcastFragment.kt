package de.exercicse.jrossbach.podcaster.feature.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import de.exercicse.jrossbach.podcaster.ui.MainActivity
import de.exercicse.jrossbach.podcaster.R
import de.exercicse.jrossbach.podcaster.base.ui.BaseFragment
import de.exercicse.jrossbach.podcaster.feature.search.ui.adapter.SavedSearchAdapter
import kotlinx.android.synthetic.main.search_podcast_fragment.*
import java.util.ArrayList

class SearchPodcastFragment : BaseFragment() {

    private var adapter: SavedSearchAdapter? = null
    private var savedSearchStrings: ArrayList<String> = ArrayList()

    override fun layoutId() = R.layout.search_podcast_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_edit_text.setText("http://pc.argudiss.de")
        //TODO: persist state after application died
        savedSearchStrings = savedInstanceState?.getStringArrayList(LAST_SEARCH_VALUES) ?: ArrayList()
        setUpSearch()
    }

    private fun setUpSearch() {
        adapter = SavedSearchAdapter()
        adapter?.items = savedSearchStrings
        saved_search_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter
        }
        adapter?.notifyDataSetChanged()

        search_button.setOnClickListener {
            val searchString = search_edit_text.text.toString().trim { it <= ' ' }
            if (searchString.isNotEmpty()) {
                search(searchString)
                if (!savedSearchStrings.contains(searchString)) savedSearchStrings.add(searchString)
            } else {
                Toast.makeText(activity, "Please enter a podcast URL", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(LAST_SEARCH_VALUES, savedSearchStrings)
    }

    private fun search(searchString: String) {
        childFragmentManager.beginTransaction()
            .add(R.id.search_fragment_container, PodcastListFragment.newInstance(searchString))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val LAST_SEARCH_VALUES = "last_search_values"
    }
}
