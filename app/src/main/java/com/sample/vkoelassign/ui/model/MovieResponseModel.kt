package com.sample.vkoelassign.ui.model

import com.sample.vkoelassign.network.StatusCode

data class MovieResponseModel(
    var page: Int = -1,
    var results: ArrayList<Result>? = null,
    var total_pages: Int = -1,
    var total_results: Int = -1,
    var msg: String = "",
    var statusCode: StatusCode = StatusCode.START,
)