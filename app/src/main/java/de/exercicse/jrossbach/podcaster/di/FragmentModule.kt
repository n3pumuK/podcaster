package de.exercicse.jrossbach.podcaster.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.exercicse.jrossbach.podcaster.feature.channel.ui.PodcastChannelFragment
import de.exercicse.jrossbach.podcaster.feature.player.ui.AudioPlayerFragment
import de.exercicse.jrossbach.podcaster.feature.search.ui.PodcastListFragment
import de.exercicse.jrossbach.podcaster.feature.search.ui.SearchPodcastFragment

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun podcastListFragment(): PodcastListFragment

    @ContributesAndroidInjector
    abstract fun audioPlayerFragment(): AudioPlayerFragment

    @ContributesAndroidInjector
    abstract fun searchPodcastFragment(): SearchPodcastFragment

    @ContributesAndroidInjector
    abstract fun podcastChannelFragment(): PodcastChannelFragment
}