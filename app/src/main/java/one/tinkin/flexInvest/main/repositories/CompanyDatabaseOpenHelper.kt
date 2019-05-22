package one.tinkin.flexInvest.main.repositories

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import one.tinkin.flexInvest.COMPANY_TABLE_NAME
import one.tinkin.flexInvest.NOTES_DB_NAME
import org.jetbrains.anko.db.*

class CompanyDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, NOTES_DB_NAME, null, 1) {

  companion object {
    private var instance: CompanyDatabaseOpenHelper? = null

    @Synchronized
    fun getInstance(context: Context): CompanyDatabaseOpenHelper {
      if (instance == null) {
        instance = CompanyDatabaseOpenHelper(context.applicationContext)
      }

      return instance!!
    }
  }

  override fun onCreate(database: SQLiteDatabase) {

    database.createTable(COMPANY_TABLE_NAME, true,
        "id" to INTEGER + PRIMARY_KEY + UNIQUE,
        "name" to TEXT,
        "description" to TEXT,
        "image" to TEXT)

  }

  override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    database.dropTable(COMPANY_TABLE_NAME, ifExists = true)

  }
}

