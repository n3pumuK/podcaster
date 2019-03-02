package de.exercicse.jrossbach.podcast

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

abstract class Presenter<ViewType: Any> {

    private var compositeDisposable: CompositeDisposable? = null
    internal var view: ViewType? = null

    fun attachView(view: ViewType) {
        this.view = view
        compositeDisposable = CompositeDisposable()
    }

    fun subscribe(completable: Completable, disposableCompletableObserver: DisposableCompletableObserver) {
        compositeDisposable?.add(
            completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableCompletableObserver))
    }

    fun <ReturnType> subscribe(single: Single<ReturnType>,
        onSuccess: (ReturnType) -> Unit,
        onError: (Throwable) -> Unit) {
        compositeDisposable?.add(
            single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
        )
    }

    fun detachView() {
        cancelSubscription()
        view = null
    }

    private fun cancelSubscription() {
        compositeDisposable?.let {
            if (!it.isDisposed) {
                it.clear()
                it.dispose()
            }
        }
    }
}
