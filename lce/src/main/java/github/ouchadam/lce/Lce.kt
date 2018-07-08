package github.ouchadam.lce

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

sealed class Lce<T, U> {

    data class Loading<T, U>(val content: T? = null) : Lce<T, U>()
    data class Content<T, U>(val content: T) : Lce<T, U>()
    data class Error<T, U>(val error: U) : Lce<T, U>()

}

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

fun <T> Single<T>.mapToLce(): Observable<Lce<T, Throwable>> {
    return toObservable().mapToLce()
}

fun <T> Observable<T>.mapToLce(): Observable<Lce<T, Throwable>> {
    return map { Lce.Content<T, Throwable>(it) as Lce<T, Throwable> }
            .startWith(Lce.Loading())
            .onErrorReturn { Lce.Error(it) }
}

typealias OnContent<T> = (T) -> Unit
typealias OnError = (Throwable) -> Unit
typealias OnLoading = () -> Unit
