package one.tinkin.flexInvest

import android.content.Context
import one.tinkin.flexInvest.main.repositories.CompanyDatabaseOpenHelper

val Context.database: CompanyDatabaseOpenHelper
  get() = CompanyDatabaseOpenHelper.getInstance(applicationContext)