package github.ouchadam.bonzo

import github.ouchadam.common.SchedulerPair
import github.ouchadam.common.schedulers
import github.ouchadam.common.subscribeAsLce
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.BehaviorSubject

class Presenter(
        private val homeService: HomeService,
        private val view: View,
        private val schedulerPair: SchedulerPair) {

    private val subject: BehaviorSubject<ViewModel> = BehaviorSubject.create()
    private val disposables = CompositeDisposable()

    fun startPresenting() {
        disposables += subject.subscribe { view.showContent(it) }

        disposables += homeService.fetch()
                .schedulers(schedulerPair)
                .subscribeAsLce(
                        onLoading = { view.showLoading() },
                        onContent = {
                            subject.onNext(it)
                        },
                        onError = { view.showError() }
                )
    }

    fun stopPresenting() {
        disposables.clear()
    }

    interface View {

        fun showLoading()

        fun showContent(model: ViewModel)

        fun showError()

    }

}