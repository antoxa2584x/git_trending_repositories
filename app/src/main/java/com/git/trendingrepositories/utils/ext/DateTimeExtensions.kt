package com.git.trendingrepositories.utils.ext

import com.git.trendingrepositories.domain.model.enums.SortPeriod
import org.joda.time.LocalDate


fun SortPeriod.toLocalDateString(format:String="yyyy-MM-dd"): String {
    val localDate = LocalDate.now()

    return when (this) {
        SortPeriod.LAST_DAY -> localDate.minusDays(2)
        SortPeriod.LAST_WEEK -> localDate.minusWeeks(1)
        SortPeriod.LAST_MONTH -> localDate.minusMonths(1)
    }.toString(format)
}

fun SortPeriod.toLocalDateLong(): Long {
    val localDate = LocalDate.now()

    return when (this) {
        SortPeriod.LAST_DAY -> localDate.minusDays(1)
        SortPeriod.LAST_WEEK -> localDate.minusWeeks(1)
        SortPeriod.LAST_MONTH -> localDate.minusMonths(1)
    }.toDate().time
}