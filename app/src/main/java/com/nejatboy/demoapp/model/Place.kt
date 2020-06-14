package com.nejatboy.demoapp.model

import java.io.Serializable

data class Place (

	val business_status : String,
	val geometry : Geometry,
	val icon : String,
	val id : String,
	val name : String,
	val place_id : String,
	val plus_code : PlusCode,
	val rating : Double,
	val reference : String,
	val scope : String,
	val types : List<String>,
	val user_ratings_total : Int,
	val vicinity : String
) : Serializable