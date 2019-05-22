package one.tinkin.flexInvest.main.services.representation

import com.google.gson.annotations.SerializedName

data class ResultResponse(val id: Long, @SerializedName("thumbnail_150") val image: String, val name: String, val description: String)

