package github.ouchadam.home

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class HomePresenter(
        private val homeData: HomeData,
        private val view: View
) {

    private val disposables = CompositeDisposable()

    fun startPresenting() {
        disposables += homeData.observe().subscribe {
            view.show(it)
        }
    }

    fun fetch() {
        disposables += homeData.fetch()
    }

    fun stopPresenting() {
        disposables.clear()
    }

    interface View {

        fun show(model: HomeViewModel)

    }

}