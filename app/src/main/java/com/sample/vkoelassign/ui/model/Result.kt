package com.sample.vkoelassign.ui.model

data class Result(
    var adult: Boolean = false,
    var backdrop_path: String = "",
    var genre_ids: List<Int>? = null,
    var id: Int = -1,
    var original_language: String = "",
    var original_title: String = "",
    var overview: String = "",
    var popularity: Double? = null,
    var poster_path: String = "",
    var release_date: String = "",
    var title: String = "",
    var video: Boolean = false,
    var vote_average: Double? = null,
    var vote_count: Int = -1
)