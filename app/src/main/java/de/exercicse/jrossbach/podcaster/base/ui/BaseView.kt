package de.exercicse.jrossbach.podcaster.base.ui

interface BaseView {
    fun showProgress(show: Boolean)
    fun showError(message: String)
}