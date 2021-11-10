package com.globallogic.app.data

import com.google.gson.annotations.SerializedName

data class Sprites (
	@SerializedName("back_default") val back_default : String?,
	@SerializedName("front_default") val front_default : String?,
)