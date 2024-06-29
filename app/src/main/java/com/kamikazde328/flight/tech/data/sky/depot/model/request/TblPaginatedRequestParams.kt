package com.kamikazde328.flight.tech.data.sky.depot.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TblPaginatedRequestParams(
    @SerialName("offset")
    val page: Int,
    @SerialName("limit")
    val limit: Int,
    @SerialName("tblName")
    val tableName: String,
    @SerialName("company")
    val tblName: String,
)