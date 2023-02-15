package com.chua.billeasetask.data.mapper

import com.chua.billeasetask.data.remote.dto.AuthorizationResponse
import com.chua.billeasetask.domain.model.Authorization

fun AuthorizationResponse.toAuthorization(): Authorization {
    return Authorization(
        success = success,
        message = message
    )
}