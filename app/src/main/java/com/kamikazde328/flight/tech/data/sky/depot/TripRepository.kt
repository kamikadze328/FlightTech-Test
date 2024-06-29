package com.kamikazde328.flight.tech.data.sky.depot

import com.kamikazde328.flight.tech.data.sky.depot.db.TripsProductDbApi
import com.kamikazde328.flight.tech.data.sky.depot.model.request.TripsParams
import com.kamikazde328.flight.tech.data.sky.depot.model.response.TripsProduct
import com.kamikazde328.flight.tech.data.sky.depot.network.SkyDepotApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TripRepository(
    private val networkDataSource: SkyDepotApi = SkyDepotApi.getInstance(),
    private val localDataSource: TripsProductDbApi = TripsProductDbApi.getInstance(),
) {
    companion object {
        val DEFAULT_TRIP_PARAMS = TripsParams.EMPTY

        private var instance: TripRepository? = null
        fun getInstance(): TripRepository {
            return instance ?: TripRepository().also { instance = it }
        }
    }

    suspend fun showTrips(
        params: TripsParams = DEFAULT_TRIP_PARAMS
    ): List<TripsProduct> = withContext(Dispatchers.IO) {
        val networkResult = networkDataSource.showTrips(params)
        val productList = networkResult.getOrNull()?.tripsProducts.orEmpty()

        if (networkResult.isSuccess) {
            localDataSource.insertAll(productList)
        }
        return@withContext localDataSource.getAll()
    }
}