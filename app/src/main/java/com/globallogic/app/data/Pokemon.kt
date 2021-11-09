package com.globallogic.app.data

import Moves
import com.google.gson.annotations.SerializedName

data class Pokemon(@SerializedName("abilities") val abilities : List<Abilities>,
                 /*  @SerializedName("moves") val moves : List<Moves>,*/
                   @SerializedName("forms") val forms : List<Forms>,
                   @SerializedName("sprites") val sprites : Sprites,
                   @SerializedName("stats") val stats : List<Stats>)
