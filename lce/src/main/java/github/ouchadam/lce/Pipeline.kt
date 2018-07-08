package github.ouchadam.lce

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject

class Pipeline<I, VM : ViewModel>(private val schedulerPair: SchedulerPair, initialValue: VM) {

    private val subject: BehaviorSubject<VM> = BehaviorSubject.createDefault(initialValue)

    fun observe(): Observable<VM> = subject

    fun execute(source: Single<I>, merger: (LceStatus, Lce<I, Throwable>, VM) -> VM): Disposable {
        return source
                .mapToLce()
                .zipWith(subject, BiFunction { upstream: Lce<I, Throwable>, current: VM ->
                    val currentStatus = current.status
                    val nextStatus = LceStatus.next(upstream, currentStatus)
                    merger(nextStatus, upstream, current)
                })
                .schedulers(schedulerPair)
                .subscribe {
                    subject.onNext(it)
                }
    }

}