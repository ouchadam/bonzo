package github.ouchadam.modules.api.models.api

import com.squareup.moshi.Json

data class ApiAccountsResponse(
        @Json(name = "accounts")
        val accounts: List<ApiAccountResponse>
)

data class ApiAccountResponse(
        @Json(name = "id")
        val id: String,

        @Json(name = "description")
        val description: String,

        @Json(name = "created")
        val createdDate: String
)