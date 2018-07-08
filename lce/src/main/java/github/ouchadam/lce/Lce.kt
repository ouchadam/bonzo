package github.ouchadam.lce

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

fun <T> Single<T>.subscribeAsLce(onLoading: OnLoading = {}, onContent: OnContent<T> = {}, onError: OnError = {}): Disposable {
    return toObservable().subscribeAsLce(onLoading, onContent, onError)
}

fun Completable.subscribeAsLce(onLoading: OnLoading = {}, onContent: OnContent<Unit> = {}, onError: OnError = {}): Disposable {
    return andThen(Observable.just(Unit)).subscribeAsLce(onLoading, onContent, onError)
}

fun <T> Observable<T>.subscribeAsLce(onLoading: OnLoading = {}, onContent: OnContent<T> = {}, onError: OnError = {}): Disposable {
    return map { Lce.Content<T, Throwable>(it) as Lce<T, Throwable> }
            .startWith(Lce.Loading())
            .onErrorReturn { Lce.Error(it) }
            .subscribeBy(onNext = {
                when (it) {
                    is Lce.Loading -> onLoading()
                    is Lce.Content -> onContent(it.content)
                    is Lce.Error -> onError(it.error)
                }
            })
}

typealias OnContent<T> = (T) -> Unit
typealias OnError = (Throwable) -> Unit
typealias OnLoading = () -> Unit
