package com.chua.billeasetask.domain.repository

import com.chua.billeasetask.domain.model.Authorization
import com.chua.billeasetask.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthorizationRepository {

    suspend fun login(): Flow<Resource<Authorization>>

}