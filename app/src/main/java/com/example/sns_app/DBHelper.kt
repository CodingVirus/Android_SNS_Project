package com.example.sns_app

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class DBHelper(
    val context: Context?,

): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "USER_AUTH"

        //Table
        const val TABLE_NAME = "USER"
        const val UID = "UID"
        const val COL_EMAIL = "EMAIL"
        const val COL_PW = "PW"
        const val COL_NAME = "NAME"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // USER 라는 이름의 테이블을 생성하고 column 은 ID, PW, NAME 3개를 생성했습니다.
        // UID 는 SQLite 를 사용하기 위해서 필수적으로 필요한 column
        var sql: String = "CREATE TABLE IF NOT EXISTS " +
                "$TABLE_NAME ($UID integer primary key autoincrement, " +
                "$COL_EMAIL text, $COL_PW text, $COL_NAME text);"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS $TABLE_NAME"

        db.execSQL(sql)
        onCreate(db)
    }

    // allUsers 리스트에 getter 를 만들어서 db 에 저장되어있는 모든 유저 정보를 가져옵니다.
    val allUsers:List<User>
        @SuppressLint("Range")
        get() {
            val users = ArrayList<User>()
            val selectQueryHandler = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler,null)
            if(cursor.moveToFirst()){
                do{
                    val user = User()
                    user.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                    user.pw = cursor.getString(cursor.getColumnIndex(COL_PW))
                    user.name = cursor.getString(cursor.getColumnIndex(COL_NAME))

                    users.add(user)
                }while(cursor.moveToNext())
            }
            db.close()
            return users
        }

    fun checkIdExist(id: String): Boolean{
        val db = this.readableDatabase

        // 리턴 받고자 하는 컬럼 값의 array
        val projection = arrayOf(UID)

        // where "id"=id and "password"=password 구문 적용하는 부분
        val selection= "$COL_EMAIL = ?"
        val selectionArgs = arrayOf(id)

        // 정렬조건 지정
        val cursor = db.query(
            TABLE_NAME, // 테이블
            projection, // 리턴 받고자 하는 컬럼
            selection, // where 조건
            selectionArgs, // where 조건에 해당하는 값의 배열
            null, // 그룹 조건
            null, // having 조건
            null // orderby 조건 지정
        )
        // 반환된 cursor 값이 존재하면 아이디 중복(true), 존재하지 않으면 아이디 생성가능(false)
        return cursor.count > 0
    }

    // db 에 새로운 유저를 추가하는 메소드(회원가입)
    fun addUser(user: User){
        if(checkIdExist(user.email)) {
            Toast.makeText(this.context, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show()
            return
        }
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_EMAIL, user.email)
        values.put(COL_PW, user.pw)
        values.put(COL_NAME, user.name)

        db.insert(TABLE_NAME, null, values)
        db.close()
        Toast.makeText(this.context,"회원가입 성공", Toast.LENGTH_SHORT).show()
    }
    // 로그인 메소드
    fun login(user: User) : Boolean{
        val db = this.readableDatabase

        val projection = arrayOf(UID)

        val selection = "$COL_EMAIL = ? AND $COL_PW = ?"
        val selectionArgs = arrayOf(user.email, user.pw)

        val cursor = db.query(
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        return cursor.count > 0
    }
    // 유저 정보 업데이트 메소드
    fun updateUser(user: User): Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_EMAIL, user.email)
        values.put(COL_PW, user.pw)
        values.put(COL_NAME, user.name)

        return db.update(TABLE_NAME, values, "$COL_EMAIL=?", arrayOf(user.email))
    }

//    fun select(db: SQLiteDatabase, txt:String) : String?{
//        var sql = " SELECT * FROM MYTABLE " +
//                "   WHERE TXT='${txt}' "
//        var result = db.rawQuery(sql, null)
//
//        var str:String? = ""
//        while (result.moveToNext()){
//            str += "번호 :" +result.getString(result.getColumnIndex("SEQ")) + "\n" +
//                    "데이터 :" + result.getString(result.getColumnIndex("TXT"))
//        }
//
//        return str
//    }

    fun select(user: User) : Boolean{
        val db = this.readableDatabase

        val projection = arrayOf(UID)

        val selection = "$COL_EMAIL = ? AND $COL_PW = ?"
        val selectionArgs = arrayOf(user.email, user.pw)

        val cursor = db.query(
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        return cursor.count > 0
    }

    // 유저 삭제 메소드
    fun deleteUser(user: User){
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "$COL_EMAIL=?", arrayOf(user.email))
        db.close()
    }
}