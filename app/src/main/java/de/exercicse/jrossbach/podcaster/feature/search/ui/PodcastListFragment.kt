package de.exercicse.jrossbach.podcaster.feature.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import de.exercicse.jrossbach.podcaster.R
import de.exercicse.jrossbach.podcaster.base.ui.BaseFragment
import de.exercicse.jrossbach.podcaster.feature.channel.ui.PodcastChannelFragment
import de.exercicse.jrossbach.podcaster.feature.player.ui.AudioPlayerFragment
import de.exercicse.jrossbach.podcaster.feature.search.ui.adapter.PodcastItemAdapter
import de.exercicse.jrossbach.podcaster.ui.MainActivity
import kotlinx.android.synthetic.main.podcast_list_fragment.*
import javax.inject.Inject

class PodcastListFragment : BaseFragment(), PodcastChannelView, PodCastItemClickListener {

    private lateinit var podcastItemAdapter: PodcastItemAdapter

    private lateinit var podCastUrl: String

    @Inject
    lateinit var presenter: SearchListPresenter

    override fun layoutId(): Int = R.layout.podcast_list_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        podCastUrl = arguments?.getString(INTENT_EXTRA_PODCAST_URL) ?: ""
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
        presenter.search(podCastUrl)
    }

    private fun initRecyclerView() {
        podcastItemAdapter = PodcastItemAdapter(this)
        podcast_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = podcastItemAdapter
        }
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

    override fun onItemsLoadedSuccessfully(podcastItemViewModels: List<PodcastItemViewModel>) {
        podcastItemAdapter.items = podcastItemViewModels
    }

    override fun showProgress(show: Boolean) {
        audio_progress_bar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onItemClick(podcastItemViewModel: PodcastItemViewModel) {
        childFragmentManager.beginTransaction()
            .add(R.id.podcast_list_container, AudioPlayerFragment.newInstance(podcastItemViewModel))
            .addToBackStack(null)
            .commit()
    }

    override fun showError(message: String) {
        error_view.apply {
            visibility = View.VISIBLE
            text = message
        }
    }

    override fun onChannelItemClicked(id: String) {
        (requireActivity() as MainActivity)
            .addFragment(PodcastChannelFragment.newInstance(id))
    }

    companion object {

        private const val INTENT_EXTRA_PODCAST_URL = "intent_extra_podcast_url"

        fun newInstance(url: String): PodcastListFragment {
            val fragment = PodcastListFragment()
            val args = Bundle()
            args.putString(INTENT_EXTRA_PODCAST_URL, url)
            fragment.arguments = args
            return fragment
        }
    }
}