package com.sbsj.mydiary.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context,
    DB_NAME, null,
    DB_VERSION
) {
    companion object {
        private val DB_NAME = "MYDIARYINFO"
        private val DB_VERSION = 1
        private val TABLE_USER = "UserInfo"
        private val ID = "id"
        private val DEVICEID = "device_id"
        private val SNSID = "sns_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_USER" +
                "($ID Integer PRIMARY KEY," +
                "$DEVICEID TEXT," +
                "$SNSID TEXT)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

//    fun addUser(userInfo: UserInfo) : Boolean {
//        val db = this.writableDatabase
//        val value = ContentValues()
//
//        value.put(DEVICEID, userInfo.device_id)
//        value.put(SNSID, userInfo.sns_id)
//
//        val _success = db.insert(TABLE_USER, null, value)
//        db.close()
//
//        return (Integer.parseInt("$_success") != -1)
//    }

    fun getUser(sns_id : String) : UserInfo? {
        var db = readableDatabase
        val query = "SELECT * FROM $TABLE_USER WHERE $SNSID = " + sns_id
        val cursor =db.rawQuery(query, null)

        if(cursor.moveToFirst()) {
            val userInfo = UserInfo()
            userInfo.id = cursor.getInt(cursor.getColumnIndex(ID))
            userInfo.device_id = cursor.getString(cursor.getColumnIndex(DEVICEID))
            userInfo.sns_id = cursor.getString(cursor.getColumnIndex(sns_id))
            return userInfo
        } else return null
    }

    fun updateUser(userinfo: UserInfo) : Int {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put(ID, userinfo.id)
        value.put(DEVICEID, userinfo.device_id)
        value.put(SNSID, userinfo.sns_id)

        return db.update(TABLE_USER, value,"$ID=?", arrayOf(userinfo.id.toString()))
    }
}