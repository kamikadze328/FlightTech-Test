package com.kamikazde328.flight.tech.data.sky.depot.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.kamikazde328.flight.tech.MyApp
import com.kamikazde328.flight.tech.data.sky.depot.model.response.TripsProduct


@Suppress("unused")
class TripsProductDbApi(
    context: Context = MyApp.getInstance(),
) {
    companion object {
        private var instance: TripsProductDbApi? = null
        fun getInstance(): TripsProductDbApi {
            return instance ?: TripsProductDbApi().also { instance = it }
        }
    }

    private val dbHelper = TripsProductDbHelper(context)


    fun insert(tripsProduct: TripsProduct) {
        val db = dbHelper.writableDatabase

        val value = tripsProduct.mapToDb() ?: return

        db?.insert(value)
    }

    fun insertAll(tripsProducts: List<TripsProduct>) {
        val db = dbHelper.writableDatabase
        val productDbs = tripsProducts.mapNotNull { it.mapToDb() }
        productDbs.forEach { db?.insert(it) }
    }

    private fun SQLiteDatabase.insert(value: ContentValues) {
        insert(TripsProductContract.FeedEntry.TABLE_NAME, null, value)
    }

    fun getAll(): List<TripsProduct> {
        val db = dbHelper.readableDatabase
        db.query(
            /* table = */ TripsProductContract.FeedEntry.TABLE_NAME,
            /* columns = */ null,
            /* selection = */ null,
            /* selectionArgs = */ null,
            /* groupBy = */ null,
            /* having = */ null,
            /* orderBy = */ null,
        ).use {
            return buildList {
                with(it) {
                    while (moveToNext()) {
                        add(readFromDb())
                    }
                }
            }
        }
    }

    private fun Cursor.readFromDb(): TripsProduct {
        return TripsProduct(
            id = getColumnValue(TripsProductContract.FeedEntry.ID),
            tripId = getColumnValue(TripsProductContract.FeedEntry.COLUMN_NAME_TRIP_ID),
            quantity = getColumnValue(TripsProductContract.FeedEntry.COLUMN_NAME_QUANTITY),
            title = getColumnValue(TripsProductContract.FeedEntry.COLUMN_NAME_TITLE),
            cost = getColumnValue(TripsProductContract.FeedEntry.COLUMN_NAME_COST),
            physical = getColumnValue(TripsProductContract.FeedEntry.COLUMN_NAME_PHYSICAL),
        )
    }

    private fun Cursor.getColumnValue(columnName: String): String? {
        val columnIndex = getColumnIndex(columnName).takeIf { it != -1 } ?: return null
        return getString(columnIndex)
    }

    private fun TripsProduct.mapToDb(): ContentValues? {
        return ContentValues().apply {
            put(TripsProductContract.FeedEntry.ID, id ?: return null)
            put(TripsProductContract.FeedEntry.COLUMN_NAME_TRIP_ID, tripId)
            put(TripsProductContract.FeedEntry.COLUMN_NAME_QUANTITY, quantity)
            put(TripsProductContract.FeedEntry.COLUMN_NAME_TITLE, title)
            put(TripsProductContract.FeedEntry.COLUMN_NAME_COST, cost)
            put(TripsProductContract.FeedEntry.COLUMN_NAME_PHYSICAL, physical)
        }
    }

}