package com.kamikazde328.flight.tech.data.sky.depot.db

import android.provider.BaseColumns

object TripsProductContract {
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "TripsProduct"
        const val ID = BaseColumns._ID
        const val COLUMN_NAME_TRIP_ID = "trip_id"
        const val COLUMN_NAME_QUANTITY = "quantity"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_COST = "cost"
        const val COLUMN_NAME_PHYSICAL = "physical"
    }

    const val SQL_CREATE_ENTRIES = """
        CREATE TABLE ${FeedEntry.TABLE_NAME} (
            ${FeedEntry.ID} INTEGER PRIMARY KEY,
            ${FeedEntry.COLUMN_NAME_TRIP_ID} TEXT,
            ${FeedEntry.COLUMN_NAME_QUANTITY} TEXT,
            ${FeedEntry.COLUMN_NAME_TITLE} TEXT,
            ${FeedEntry.COLUMN_NAME_COST} TEXT,
            ${FeedEntry.COLUMN_NAME_PHYSICAL} TEXT)
    """

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"
}