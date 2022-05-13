package com.shahryar.shared.data.source.remote

import com.shahryar.shared.data.CryptoPriceSettings
import com.shahryar.shared.data.model.Data
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

class PriceApi {

    private val client = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
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
//            url("https://pro-api.coinmarketcap.com/v1/") {
//                CryptoPriceSettings.getSetting(CryptoPriceSettings.KEYS.TOKEN)
//                    ?.let { parameters.append("CMC_PRO_API_KEY", it) }
//            }
        }
    }
    private val address = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"

    suspend fun getPrices(): Data {
        return client.get("cryptocurrency/listings/latest").body()
    }
}
