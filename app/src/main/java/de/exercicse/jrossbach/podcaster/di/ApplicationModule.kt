package de.exercicse.jrossbach.podcaster.di

import dagger.Module
import dagger.Provides
import de.exercicse.jrossbach.podcaster.core.network.AuthorizationIntercepter
import de.exercicse.jrossbach.podcaster.feature.search.data.PodcastApi
import de.exercicse.jrossbach.podcaster.core.data.RetrofitProvider
import de.exercicse.jrossbach.podcaster.feature.search.domain.PodcastDataSource
import de.exercicse.jrossbach.podcaster.feature.search.ui.SearchListPresenter
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationIntercepter())
        .build()

    @Provides
    @Singleton
    fun retrofitProvider(okHttpClient: OkHttpClient) = RetrofitProvider(okHttpClient)

    @Provides
    fun podcastDataSource(podcastApi: PodcastApi) = PodcastDataSource(podcastApi)

    @Provides
    fun searchListPresenter(dataSource: PodcastDataSource) = SearchListPresenter(dataSource)
}