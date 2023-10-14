package com.git.trendingrepositories.data.mappers

import com.git.trendingrepositories.data.local.model.OwnerEntity
import com.git.trendingrepositories.data.local.model.RepositoryEntity
import com.git.trendingrepositories.data.local.model.RepositoryWithLike
import com.git.trendingrepositories.data.remote.search.model.OwnerDto
import com.git.trendingrepositories.data.remote.search.model.RepositoryDto
import com.git.trendingrepositories.domain.model.search.Owner
import com.git.trendingrepositories.domain.model.search.Repository

fun RepositoryDto.toRepositoryEntity(): RepositoryEntity {
    return RepositoryEntity(
        id = this.id,
        fullName = this.fullName,
        dateCreated = this.dateCreated.time,
        name = this.name,
        description = this.description ?: "",
        forksCount = this.forksCount,
        htmlUrl = this.htmlUrl,
        language = this.language ?: "",
        owner = this.owner.toOwnerEntity(),
        stargazersCount = this.stargazersCount,
        url = this.url,
    )
}

fun RepositoryWithLike.toRepository(): Repository {
    return Repository(
        id = this.repository.id,
        fullName = this.repository.fullName,
        dateCreated = this.repository.dateCreated,
        name = this.repository.name,
        description = this.repository.description,
        forksCount = this.repository.forksCount,
        htmlUrl = this.repository.htmlUrl,
        language = this.repository.language,
        owner = this.repository.owner.toOwner(),
        stargazersCount = this.repository.stargazersCount,
        url = this.repository.url,
        isLiked = this.likedEntity?.isLiked == true,
    )
}

fun OwnerDto.toOwnerEntity(): OwnerEntity {
    return OwnerEntity(
        ownerUrl = this.url,
        avatarUrl = this.avatarUrl,
        ownerId = this.id,
        login = this.login
    )
}


fun OwnerEntity.toOwner(): Owner {
    return Owner(
        url = this.ownerUrl,
        avatarUrl = this.avatarUrl,
        id = this.ownerId,
        login = this.login
    )
}