package github.ouchadam.auth

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