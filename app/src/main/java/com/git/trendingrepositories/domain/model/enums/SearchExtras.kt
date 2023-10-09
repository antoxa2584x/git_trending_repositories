package com.git.trendingrepositories.domain.model.enums

enum class SortPeriod {
    LAST_DAY, LAST_WEEK, LAST_MONTH;
}

enum class SortOrder(val value: String) {
    STARS("stars")
}

enum class ResultsOrder(val value: String) {
    DESC("desc"), ASC("asc")
}