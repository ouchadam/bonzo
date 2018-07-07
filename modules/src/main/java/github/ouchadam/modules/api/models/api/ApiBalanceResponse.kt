package github.ouchadam.modules.api.models.api

import com.squareup.moshi.Json

data class ApiBalanceResponse(
        @Json(name = "balance")
        val balanceInMinor: Long,

        @Json(name = "currency")
        val currency: String,

        @Json(name = "spend_today")
        val spendTodayInMinor:  Long
)