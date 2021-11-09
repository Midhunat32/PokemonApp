package com.globallogic.app.data


import com.google.gson.annotations.SerializedName

data class Pokemon(@SerializedName("abilities") val abilities : List<Abilities>,
                   @SerializedName("name") val name : String,
                   @SerializedName("forms") val forms : List<Forms>,
                   @SerializedName("sprites") val sprites : Sprites,
                   @SerializedName("stats") val stats : List<Stats>,
                   @SerializedName("moves") val moves : List<Moves>)
