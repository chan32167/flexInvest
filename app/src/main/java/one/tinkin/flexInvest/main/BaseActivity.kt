package one.tinkin.flexInvest.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.Disposable
import one.tinkin.flexInvest.PREFS_FILENAME


open class BaseActivity : AppCompatActivity() {

  var disposableLogin: Disposable? = null
  var disposableCompany: Disposable? = null

  var sharedPref: SharedPreferences? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    sharedPref = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);
  }

  override fun onPause() {
    super.onPause()
    disposableLogin?.dispose()
    disposableCompany?.dispose()
  }

  fun makeToast(text: String?) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
  }

  override fun onBackPressed() {
  }

}
