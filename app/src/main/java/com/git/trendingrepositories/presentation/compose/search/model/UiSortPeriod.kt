package com.git.trendingrepositories.presentation.compose.search.model

import androidx.annotation.StringRes
import com.git.trendingrepositories.R
import com.git.trendingrepositories.domain.model.enums.SortPeriod

sealed class UiSortPeriod(@StringRes val title: Int, val sortPeriod: SortPeriod) {
    object LastDay : UiSortPeriod(R.string.last_day, SortPeriod.LAST_DAY)
    object LastWeek : UiSortPeriod(R.string.last_week, SortPeriod.LAST_WEEK)
    object LastMonth : UiSortPeriod(R.string.last_month, SortPeriod.LAST_MONTH)

    companion object {
        fun UiSortPeriod.getNext(): UiSortPeriod {
            return when (this) {
                LastDay -> LastWeek
                LastWeek -> LastMonth
                LastMonth -> LastDay
            }
        }
    }
}