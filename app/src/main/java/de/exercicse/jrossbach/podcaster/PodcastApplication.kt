package de.exercicse.jrossbach.podcaster

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import de.exercicse.jrossbach.podcaster.di.DaggerApplicationComponent
import javax.inject.Inject

class PodcastApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .application(this)
            .context(applicationContext)
            .build()
            .inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}