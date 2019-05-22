package one.tinkin.flexInvest

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import one.tinkin.flexInvest.main.services.FlexInvestService

val FlexInvestServe by lazy {
  FlexInvestService.create()
}

/**
 * Main Application
 */
class MainApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    Fresco.initialize(this);
  }

}