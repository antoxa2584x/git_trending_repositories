package com.git.trendingrepositories.presentation.compose.viewmodel

import androidx.lifecycle.ViewModel
import com.git.trendingrepositories.domain.model.search.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedScreensViewModel @Inject constructor() :
    ViewModel() {

    var repository: Repository? = null

}
