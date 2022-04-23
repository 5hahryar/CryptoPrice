package com.shahryar.shared.data.source.remote

import com.shahryar.shared.data.CryptoPriceSettings
import com.shahryar.shared.data.model.Data
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PriceApi {
    private val client = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

        install(JsonFeature) {
            serializer =
                KotlinxSerializer(kotlinx.serialization.json.Json { ignoreUnknownKeys = true })
        }

        defaultRequest {
            parameter("CMC_PRO_API_KEY", CryptoPriceSettings.getSetting(CryptoPriceSettings.KEYS.TOKEN))
        }
    }
    private val address = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"

    suspend fun getPrices(): Data {
        return client.get(address)
    }
}
