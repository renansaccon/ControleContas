package br.rs.edu.controlecontas.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.rs.edu.controlecontas.entity.Lancamento

class DataBase (context : Context) : SQLiteOpenHelper ( context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {

            db?.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, tipo TEXT, data TEXT, detalhe TEXT, valor DOUBLE)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "lancamentos"
        const val COD = 0
        const val TIPO = 1
        const val DATA = 2
        const val DETALHE = 3
        const val VALOR = 4
    }

    fun insert ( lancamento: Lancamento){
        val db = this.writableDatabase

        val lancar = ContentValues()
        lancar.put("tipo", lancamento.tipo)
        lancar.put("data", lancamento.data)
        lancar.put("detalhe", lancamento.detalhe)
        lancar.put("valor", lancamento.valor)

        db.insert(TABLE_NAME, null, lancar)
    }

    fun cursorList() : Cursor {
        val db = writableDatabase

        val registro = db.query(
            "lancamentos",
            null,
            null,
            null,
            null,
            null,
            null,
        )

        return registro
    }

    fun btSaldoOnClick(): Double? {
        val db = writableDatabase
        val sum = db.rawQuery("SELECT SUM(valor) FROM lancamentos", null)
        val saldo : Double

        if (sum.moveToNext()){
            saldo = sum.getDouble(0)
            return saldo
        }else {
            return null
        }
    }

    fun btDeleteOnClick (id : Int){
        val db = writableDatabase
        db.delete("lancamentos", "_id=${id}", null)
    }
}