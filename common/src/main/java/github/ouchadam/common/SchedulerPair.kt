package github.ouchadam.common

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

data class SchedulerPair(
        val subscribeOn: Scheduler,
        val observeOn: Scheduler
)

fun <T> Single<T>.schedulers(schedulers: SchedulerPair): Single<T> =
        this.subscribeOn(schedulers.subscribeOn).observeOn(schedulers.observeOn)
