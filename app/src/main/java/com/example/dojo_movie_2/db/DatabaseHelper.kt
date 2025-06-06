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

        private const val HISTORY_COLUMN_USER_PHONE = "user_phone"

        private const val TABLE_HISTORY = "history"
        private const val HISTORY_COLUMN_ID = "id"
        private const val HISTORY_COLUMN_TITLE = "title"
        private const val HISTORY_COLUMN_PRICE = "price"
        private const val HISTORY_COLUMN_QUANTITY = "quantity"

        private const val TABLE_USERS = "users"
        private const val USERS_COLUMN_ID = "id"
        private const val USERS_COLUMN_PHONE = "phone"
        private const val USERS_COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableHistory = """
            CREATE TABLE $TABLE_HISTORY (
                $HISTORY_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $HISTORY_COLUMN_TITLE TEXT,
                $HISTORY_COLUMN_PRICE INTEGER,
                $HISTORY_COLUMN_QUANTITY INTEGER,
                user_phone TEXT
            )
        """.trimIndent()

        val createTableUsers = """
            CREATE TABLE $TABLE_USERS (
                $USERS_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $USERS_COLUMN_PHONE TEXT UNIQUE,
                $USERS_COLUMN_PASSWORD TEXT
            )
        """.trimIndent()

        db.execSQL(createTableHistory)
        db.execSQL(createTableUsers)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun insertHistory(history: HistoryModel, userPhone: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(HISTORY_COLUMN_TITLE, history.title)
            put(HISTORY_COLUMN_PRICE, history.price)
            put(HISTORY_COLUMN_QUANTITY, history.quantity)
            put(HISTORY_COLUMN_USER_PHONE, userPhone)
        }
        return db.insert(TABLE_HISTORY, null, values)
    }

    fun getHistoryByUser(phone: String): List<HistoryModel> {
        val list = mutableListOf<HistoryModel>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_HISTORY,
            null,
            "$HISTORY_COLUMN_USER_PHONE = ?",
            arrayOf(phone),
            null,
            null,
            "$HISTORY_COLUMN_ID DESC"
        )
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(HISTORY_COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(HISTORY_COLUMN_TITLE))
                val price = getInt(getColumnIndexOrThrow(HISTORY_COLUMN_PRICE))
                val quantity = getInt(getColumnIndexOrThrow(HISTORY_COLUMN_QUANTITY))
                list.add(HistoryModel(id, title, price, quantity))
            }
            close()
        }
        return list
    }

    fun getAllHistory(): List<HistoryModel> {
        val list = mutableListOf<HistoryModel>()
        val db = readableDatabase
        val cursor = db.query(TABLE_HISTORY, null, null, null, null, null, "$HISTORY_COLUMN_ID DESC")
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(HISTORY_COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(HISTORY_COLUMN_TITLE))
                val price = getInt(getColumnIndexOrThrow(HISTORY_COLUMN_PRICE))
                val quantity = getInt(getColumnIndexOrThrow(HISTORY_COLUMN_QUANTITY))
                list.add(HistoryModel(id, title, price, quantity))
            }
            close()
        }
        return list
    }

    fun isPhoneExists(phone: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS WHERE $USERS_COLUMN_PHONE = ?", arrayOf(phone))
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }

    fun checkUser(phone: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $USERS_COLUMN_PHONE = ? AND $USERS_COLUMN_PASSWORD = ?",
            arrayOf(phone, password)
        )
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }

    fun addUser(phone: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(USERS_COLUMN_PHONE, phone)
            put(USERS_COLUMN_PASSWORD, password)
        }
        val result = db.insert(TABLE_USERS, null, values)
        return result != -1L
    }

}
