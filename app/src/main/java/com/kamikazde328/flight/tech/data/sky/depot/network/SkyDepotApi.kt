package com.kamikazde328.flight.tech.data.sky.depot.network

import com.kamikazde328.flight.tech.data.sky.depot.model.request.DefaultRequestParams
import com.kamikazde328.flight.tech.data.sky.depot.model.request.PlaneRequestParams
import com.kamikazde328.flight.tech.data.sky.depot.model.request.SeatMapRequestParams
import com.kamikazde328.flight.tech.data.sky.depot.model.request.TblPaginatedRequestParams
import com.kamikazde328.flight.tech.data.sky.depot.model.request.TripsParams
import com.kamikazde328.flight.tech.data.sky.depot.model.response.Trips
import com.kamikazde328.flight.tech.data.sky.depot.model.response.TripsResultResponse
import com.kamikazde328.flight.tech.network.HttpClientDefaultFactory
import com.kamikazde328.flight.tech.network.JsonRpcApiClient.fetchData
import com.kamikazde328.flight.tech.network.http.HttpClient

@Suppress("unused")
class SkyDepotApi(
    httpClientDefaultFactory: HttpClientDefaultFactory = HttpClientDefaultFactory.getInstance(),
) {
    companion object {
        private const val BASE_URL = "https://testpos.api.skydepot.io"
        private const val DEFAULT_HTTP_METHOD = "POST"
        private var instance: SkyDepotApi? = null
        fun getInstance(): SkyDepotApi {
            return instance ?: SkyDepotApi().also { instance = it }
        }

    }

    private val httpClient: HttpClient = httpClientDefaultFactory.makeHttpClient()


    suspend fun showTrips(params: TripsParams): Result<Trips> {
        return fetchData<TripsParams, TripsResultResponse>("trips.show", params).map { it.data }
    }

    suspend fun getConfig(params: DefaultRequestParams): Result<Any?> {
        return fetchData("getConfig", params)
    }

    suspend fun getCurrencies(params: DefaultRequestParams): Result<Any?> {
        return fetchData("getCurrencies", params)
    }

    suspend fun getDiscounts(params: DefaultRequestParams): Result<Any?> {
        return fetchData("getDiscounts", params)
    }

    suspend fun getSeatMap(params: SeatMapRequestParams): Result<Any?> {
        return fetchData("getSeatMap", params)
    }

    suspend fun getGoodsTaxes(params: DefaultRequestParams): Result<Any?> {
        return fetchData("getGoodsTaxes", params)
    }

    suspend fun getMerchants(params: DefaultRequestParams): Result<Any?> {
        return fetchData("getMerchants", params)
    }

    suspend fun getTblPaginated(params: TblPaginatedRequestParams): Result<Any?> {
        return fetchData("getTblPaginated", params)
    }

    suspend fun getDicts(params: DefaultRequestParams): Result<Any?> {
        return fetchData("getDicts", params)
    }

    suspend fun getPlanes(params: PlaneRequestParams): Result<Any?> {
        return fetchData("getPlanes", params)
    }

    suspend fun getBarcodes(params: DefaultRequestParams): Result<Any?> {
        return fetchData("getBarcodes", params)
    }

    private suspend inline fun <reified Request, reified Response> fetchData(
        method: String,
        params: Request
    ): Result<Response> {
        return httpClient.fetchData(
            method = method,
            url = BASE_URL,
            params = params,
        )
    }
}

