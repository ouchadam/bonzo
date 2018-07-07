package github.ouchadam.modules.api

import github.ouchadam.modules.api.models.api.ApiBalanceResponse
import io.reactivex.Single

interface BalanceService {

    fun readBalance(accountId: String): Single<ApiBalanceResponse>

}