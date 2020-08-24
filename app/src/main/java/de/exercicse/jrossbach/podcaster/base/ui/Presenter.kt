package de.exercicse.jrossbach.podcaster.base.ui

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class Presenter<ViewType> {
    private var disposable: Disposable? = null

    private var _view: ViewType? = null

    val view
        get() = _view!!

    fun attachView(view: ViewType) {
        this._view = view
    }

    fun detachView() {
        _view = null
        cancel()
    }

    protected fun <ReturnType> subscribe(
        single: Single<ReturnType>,
        onSuccess: (ReturnType) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        disposable = single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }

    private fun cancel() {
        disposable?.let {
            if (!it.isDisposed) it.dispose()
        }
        disposable = null
    }
}