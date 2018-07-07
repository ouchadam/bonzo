package github.ouchadam.common

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class SchedulerPair(
        val subscribeOn: Scheduler = AndroidSchedulers.mainThread(),
        val observeOn: Scheduler = Schedulers.io()
)

fun <T> Single<T>.schedulers(schedulers: SchedulerPair): Single<T> =
        this.subscribeOn(schedulers.subscribeOn).observeOn(schedulers.observeOn)

fun Completable.schedulers(schedulers: SchedulerPair): Completable =
        this.subscribeOn(schedulers.subscribeOn).observeOn(schedulers.observeOn)
