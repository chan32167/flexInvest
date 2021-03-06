package one.tinkin.flexInvest.main

import android.content.Intent
import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import one.tinkin.flexInvest.*
import one.tinkin.flexInvest.main.services.representation.LoginRequest


class LoginActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    checkIfAlreadyLoggedIn()
    setContentView(R.layout.activity_login)
    bt_login.setOnClickListener {
      disposableLogin = FlexInvestServe.login(LoginRequest(et_username.text.toString(), et_password.text.toString())).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(
              { result -> processData(result.token) },
              { makeToast(getString(R.string.error_login)) }
          )
    }
  }

  private fun processData(token: String) {
    with(sharedPref!!.edit()) {
      putString(TOKEN_KEY, token)
      putLong(TOKEN_CREATION_KEY, System.currentTimeMillis())
      putString(USERNAME_KEY, et_username.text.toString())
      putString(PASSWORD_KEY, et_password.text.toString())
      putBoolean(LOGGED_IN_KEY, true)
      apply()
    }
    navigateToMainActivity()

  }

  private fun checkIfAlreadyLoggedIn() {
    if(sharedPref!!.getBoolean(LOGGED_IN_KEY, false)) {
      navigateToMainActivity()
    }
  }

  private fun navigateToMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
    startActivity(intent)
  }

}
