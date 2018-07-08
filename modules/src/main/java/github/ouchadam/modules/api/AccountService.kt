package github.ouchadam.modules.api

import github.ouchadam.modules.api.models.api.ApiAccountResponse
import io.reactivex.Single

interface AccountService {

    fun readAccounts(): Single<List<ApiAccountResponse>>

}