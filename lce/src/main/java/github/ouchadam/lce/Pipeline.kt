package github.ouchadam.lce

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject

class Pipeline<I, VM : ViewModel>(private val schedulerPair: SchedulerPair, private val initialValue: VM) {

    private val subject: BehaviorSubject<VM> = BehaviorSubject.createDefault(initialValue)

    fun observe(): Observable<VM> = subject

    fun execute(source: Single<I>, merger: (LceStatus, Lce<I, Throwable>, VM) -> VM): Disposable {
        return source
                .toObservable()
                .map { Lce.Content<I, Throwable>(it) as Lce<I, Throwable> }
                .onErrorReturn { Lce.Error(it) }
                .zipWith(subject, BiFunction { upstream: Lce<I, Throwable>, current: VM ->
                    val currentStatus = current.status
                    val nextStatus = LceStatus.next(upstream, currentStatus)
                    merger(nextStatus, upstream, current)
                })
                .startWith(createInitialLoadingModel(merger))
                .schedulers(schedulerPair)
                .subscribe {
                    subject.onNext(it)
                }
    }

    private fun createInitialLoadingModel(merger: (LceStatus, Lce<I, Throwable>, VM) -> VM): VM {
        val currentStatus = initialValue.status
        val upstream = Lce.Loading<I, Throwable>()
        val nextStatus = LceStatus.next(upstream, currentStatus)
        return merger(nextStatus, upstream, initialValue)
    }

}

sealed class Lce<T, U> {

    data class Loading<T, U>(val content: T? = null) : Lce<T, U>()
    data class Content<T, U>(val content: T) : Lce<T, U>()
    data class Error<T, U>(val error: U) : Lce<T, U>()

}