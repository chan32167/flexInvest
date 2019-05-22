package one.tinkin.flexInvest.main.services

import io.reactivex.Observable
import one.tinkin.flexInvest.AUTHORIZATION_HEADER
import one.tinkin.flexInvest.AUTH_URL
import one.tinkin.flexInvest.BASE_URL
import one.tinkin.flexInvest.STRATEGIES_URL
import one.tinkin.flexInvest.main.services.representation.DataResponse
import one.tinkin.flexInvest.main.services.representation.LoginRequest
import one.tinkin.flexInvest.main.services.representation.LoginResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FlexInvestService {

  @POST(AUTH_URL)
  fun login(@Body loginRequest: LoginRequest): Observable<LoginResponse>

  @GET(STRATEGIES_URL)
  fun getData(@Header(AUTHORIZATION_HEADER) token: String): Observable<DataResponse>

  companion object {
    fun create(): FlexInvestService {

      val retrofit = Retrofit.Builder()
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl(BASE_URL)
          .build()

      return retrofit.create(FlexInvestService::class.java)
    }
  }
}
