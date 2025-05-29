package com.example.dojo_movie_2.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.dojo_movie_2.model.HistoryModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "DojoMovie.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_HISTORY = "history"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_QUANTITY = "quantity" // tambah kolom quantity
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableHistory = """
            CREATE TABLE $TABLE_HISTORY (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_PRICE INTEGER,
                $COLUMN_QUANTITY INTEGER
            )
        """.trimIndent()
        db.execSQL(createTableHistory)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        onCreate(db)
    }

    fun insertHistory(history: HistoryModel): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, history.title)
            put(COLUMN_PRICE, history.price)
            put(COLUMN_QUANTITY, history.quantity)
        }
        return db.insert(TABLE_HISTORY, null, values)
    }

    fun getAllHistory(): List<HistoryModel> {
        val list = mutableListOf<HistoryModel>()
        val db = readableDatabase
        val cursor = db.query(TABLE_HISTORY, null, null, null, null, null, "$COLUMN_ID DESC")
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(COLUMN_TITLE))
                val price = getInt(getColumnIndexOrThrow(COLUMN_PRICE))
                val quantity = getInt(getColumnIndexOrThrow(COLUMN_QUANTITY))
                list.add(HistoryModel(id, title, price, quantity))
            }
            close()
        }
        return list
    }
}
