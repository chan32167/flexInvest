package one.tinkin.flexInvest.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import one.tinkin.flexInvest.*
import one.tinkin.flexInvest.main.adapters.CompanyAdapter
import one.tinkin.flexInvest.main.repositories.CompanyRepository
import one.tinkin.flexInvest.main.repositories.model.Company
import one.tinkin.flexInvest.main.services.representation.LoginRequest
import one.tinkin.flexInvest.main.services.representation.ResultResponse


class MainActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    rv_data.layoutManager = LinearLayoutManager(this)
    loadCompanyData()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val inflater = menuInflater
    inflater.inflate(R.menu.menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.getItemId()) {
      R.id.action_refresh -> getRemoteData()
      // action with ID action_settings was selected
      R.id.action_signOut -> signOut()
      else -> {
      }
    }
    return true
  }

  private fun loadCompanyData() {
    getLocalData()
    checkTokenAndRefreshIfNecessary();
    getRemoteData();
  }

  private fun getRemoteData() {
    disposableCompany = FlexInvestServe.getData(sharedPref!!.getString(TOKEN_KEY, "")!!).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { result -> processData(result.results) },
            { error -> makeToast(error.message) }
        )
  }

  private fun processData(data: List<ResultResponse>) {
    val companies = data.map { Company(it.id, it.name, it.description, it.image) }
    rv_data.adapter = CompanyAdapter(companies, this)
    CompanyRepository(this).deleteAndcreateAll(companies)
    makeToast("Refreshed data from remote")
  }

  fun getLocalData() {
    rv_data.adapter = CompanyAdapter(CompanyRepository(this).findAll(), this)
  }

  private fun checkTokenAndRefreshIfNecessary() {
    with(sharedPref!!) {
      if (System.currentTimeMillis() - this.getLong(TOKEN_CREATION_KEY, 0) > 180000) {
        disposableLogin = FlexInvestServe.login(LoginRequest(this.getString(USERNAME_KEY, "")!!, this.getString(PASSWORD_KEY, "")!!)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                  run {
                    this.edit().putLong(TOKEN_CREATION_KEY, System.currentTimeMillis()).apply()
                    this.edit().putString(TOKEN_KEY, result.token).apply()
                  }
                },
                { error -> makeToast(error.message) }
            )
      }
    }
  }

  private fun signOut() {
    val intent = Intent(this, LoginActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
    sharedPref!!.edit().putBoolean(LOGGED_IN_KEY, false)
    startActivity(intent)
  }

}
