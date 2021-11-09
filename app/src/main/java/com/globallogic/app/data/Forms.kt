package com.globallogic.app.data

import com.google.gson.annotations.SerializedName

data class Forms (
	@SerializedName("name") val name : String,
	@SerializedName("url") val url : String
)