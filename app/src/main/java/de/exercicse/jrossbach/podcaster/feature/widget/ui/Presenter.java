package de.exercicse.jrossbach.podcast;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public abstract class Presenter<ViewType> {

    CompositeDisposable compositeDisposable;
    protected ViewType view;

    public void attachView(ViewType view){
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    protected void subscribe(Completable completable, DisposableCompletableObserver disposableCompletableObserver){
        compositeDisposable.add(completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableCompletableObserver));
    }

    protected void subscribe(Single<?> single, DisposableSingleObserver disposableSingleObserver){
        compositeDisposable.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(disposableSingleObserver));
    }

    public void detachView(){
        cancelSubscription();
        view = null;
    }

    private void cancelSubscription() {
        if(compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
            compositeDisposable.dispose();
        }
    }
}
