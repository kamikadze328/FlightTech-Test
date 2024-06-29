package com.kamikazde328.flight.tech.data.sky.depot

import com.kamikazde328.flight.tech.data.sky.depot.api.SkyDepotApi
import com.kamikazde328.flight.tech.data.sky.depot.model.request.TripsParams
import com.kamikazde328.flight.tech.data.sky.depot.model.response.Trips
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TripRepository(
    private val skyDepotApi: SkyDepotApi = SkyDepotApi.getInstance(),
) {
    companion object {
        val DEFAULT_TRIP_PARAMS = TripsParams.EMPTY

        private var instance: TripRepository? = null
        fun getInstance(): TripRepository {
            return instance ?: TripRepository().also { instance = it }
        }
    }

    suspend fun showTrips(params: TripsParams = DEFAULT_TRIP_PARAMS): Result<Trips> =
        withContext(Dispatchers.IO) {
            return@withContext skyDepotApi.showTrips(params)
        }
}