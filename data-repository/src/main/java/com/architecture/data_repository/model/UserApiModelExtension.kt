package com.architecture.data_repository.model

import com.architecture.core.model.User
import com.architecture.data_remote.api.UserApiModel

fun UserApiModel.asUiModel() = User(
    email = email,
    name = "${name.first} ${name.last}",
    picture = picture.thumbnail
)
