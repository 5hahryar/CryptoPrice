package com.shahryar.shared.data.source.remote

import com.shahryar.shared.data.CryptoPriceSettings
import com.shahryar.shared.data.model.Data
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.parameter.parametersOf

val client = HttpClient {
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Napier.v(message = message, tag = "KTOR")
            }
        }
        logger = Logger.DEFAULT
        level = LogLevel.INFO
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                explicitNulls = false
                coerceInputValues = true
            }
        )
    }

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = "pro-api.coinmarketcap.com"
            path("v1/")
            CryptoPriceSettings.getSetting(CryptoPriceSettings.KEYS.TOKEN)
                ?.let { parameters.append("CMC_PRO_API_KEY", it) }
        }
    }
}.also { Napier.base(DebugAntilog()) }

class PriceApi {
    suspend fun getPrices(): Data {
        return client.get("cryptocurrency/listings/latest").body()
    }
}

