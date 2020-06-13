package com.nejatboy.demoapp.model

data class BaseModel (

	val html_attributions : List<String>,
	val next_page_token : String,
	val results : List<Place>,
	val status : String
)