package com.git.trendingrepositories.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikedEntity(val isLiked: Boolean, @PrimaryKey val repositoryId: Int)