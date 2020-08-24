package de.exercicse.jrossbach.podcaster.core.domain

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidResourceProvider
@Inject constructor(context: Context) {

    private var resources: Resources = context.resources

    fun getString(@StringRes resourceId: Int) = resources.getString(resourceId)

}