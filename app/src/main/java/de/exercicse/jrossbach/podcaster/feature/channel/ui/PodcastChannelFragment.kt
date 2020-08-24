package de.exercicse.jrossbach.podcaster.feature.channel.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import de.exercicse.jrossbach.podcaster.R
import de.exercicse.jrossbach.podcaster.base.ui.BaseFragment
import de.exercicse.jrossbach.podcaster.feature.channel.data.PodcastResponse
import de.exercicse.jrossbach.podcaster.feature.channel.ui.adapter.PodcastEpisodesAdapter
import kotlinx.android.synthetic.main.podcast_channel_fragment_layout.*
import javax.inject.Inject

class PodcastChannelFragment : BaseFragment(), PodcastChannelView {

    @Inject
    internal lateinit var presenter: PodcastChannelPresenter
    private lateinit var episodesAdapter: PodcastEpisodesAdapter

    override fun layoutId() = R.layout.podcast_channel_fragment_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        episodesAdapter = PodcastEpisodesAdapter()
        channel_recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = episodesAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
        arguments?.getString(INTENT_EXTRA_CHANNEL_ID)?.let {
            presenter.loadChannel(it)
        }
    }

    override fun onChannelLoaded(podcast: PodcastResponse) {
        showProgress(false)
        Picasso.get().load(podcast.image).into(channel_image)
        episodesAdapter.items = podcast.episodes
    }

    override fun showProgress(show: Boolean) {
        channel_progress.visibility = if (show) VISIBLE else GONE
    }

    override fun showError(message: String) {
        showProgress(false)
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
            .show()
    }

    companion object {

        private const val INTENT_EXTRA_CHANNEL_ID = "intent_extra_channel_id"

        fun newInstance(id: String) = PodcastChannelFragment().apply {
            arguments = Bundle().apply {
                putString(INTENT_EXTRA_CHANNEL_ID, id)
            }
        }
    }
}