package github.ouchadam.modules.api

import github.ouchadam.modules.api.models.api.ApiAccountsResponse
import io.reactivex.Single

interface AccountService {

    fun readAccounts(): Single<ApiAccountsResponse>

}