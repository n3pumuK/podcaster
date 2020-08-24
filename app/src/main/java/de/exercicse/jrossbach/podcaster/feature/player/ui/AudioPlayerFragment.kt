package de.exercicse.jrossbach.podcaster.feature.player.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import de.exercicse.jrossbach.podcaster.R
import de.exercicse.jrossbach.podcaster.feature.player.domain.AudioPlaybackService
import de.exercicse.jrossbach.podcaster.feature.search.ui.PodcastItemView
import de.exercicse.jrossbach.podcaster.feature.search.ui.PodcastItemViewModel
import kotlinx.android.synthetic.main.audio_player_fragment.*

class AudioPlayerFragment : Fragment(), PodcastItemView, Player.EventListener {

    private var exoPlayer: SimpleExoPlayer? = null
    private var podcastItemViewModel: PodcastItemViewModel? = null
    private var playWhenReady = false
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.audio_player_fragment, container, false)
        podcastItemViewModel = arguments?.getParcelable(INTENT_EXTRA_PODCAST_ITEM)
        return rootView
    }

    private fun initPlayer(mediaUri: Uri) {
        val audioPlaybackIntent = Intent(requireActivity(), AudioPlaybackService::class.java)
        audioPlaybackIntent.data = mediaUri
        requireActivity().startService(audioPlaybackIntent)
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            exoPlayer = ExoPlayerFactory.newSimpleInstance(requireActivity())

            // Set the ExoPlayer.EventListener to this activity.
            exoPlayer?.addListener(this)

            // Prepare the MediaSource.
            val userAgent = Util.getUserAgent(activity, getString(R.string.app_name))
            val mediaSource: MediaSource = ExtractorMediaSource(mediaUri, DefaultDataSourceFactory(
                requireActivity(), userAgent), DefaultExtractorsFactory(), null, null)
            exoPlayer?.prepare(mediaSource)
            exoPlayer?.playWhenReady = true
            player_view.useController = false //set to true or false to see controllers
            player_view.requestFocus()
            player_view.player = exoPlayer
        }
    }

    private fun onPlayerInitialized(exoPlayer: SimpleExoPlayer) {
        player_view.player = exoPlayer
    }

    private fun releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer!!.currentPosition
            currentWindow = exoPlayer!!.currentWindowIndex
            playWhenReady = exoPlayer!!.playWhenReady
            exoPlayer!!.release()
            exoPlayer = null
        }
    }

    override fun onStart() {
        super.onStart()
        initPlayer(Uri.parse(podcastItemViewModel!!.url))
    }

    override fun onStop() {
        releasePlayer()
        super.onStop()
    }

    override fun onItemsLoadedSuccessfully(podcastItemViewModels: List<PodcastItemViewModel>) {}

    override fun showProgress(show: Boolean) {
        audio_progress_bar.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onItemClick(podcastItemViewModel: PodcastItemViewModel) {}
    override fun showError(message: String) {}
    override fun onLoadingChanged(isLoading: Boolean) {}
    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {}
    override fun play() {}

    companion object {
        private const val INTENT_EXTRA_PODCAST_ITEM = "intent_extra_podcast_item"

        fun newInstance(podcastItem: PodcastItemViewModel) =
            AudioPlayerFragment().apply {
                arguments = Bundle().apply { putParcelable(INTENT_EXTRA_PODCAST_ITEM, podcastItem) }
            }
    }
}