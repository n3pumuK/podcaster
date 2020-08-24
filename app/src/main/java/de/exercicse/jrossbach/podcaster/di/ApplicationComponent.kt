package de.exercicse.jrossbach.podcaster.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import de.exercicse.jrossbach.podcaster.PodcastApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class, FragmentModule::class])
interface ApplicationComponent : AndroidInjector<PodcastApplication> {

    override fun inject(app: PodcastApplication)

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

    }
}