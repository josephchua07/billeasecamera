package com.chua.billeasetask.data.repository

import com.chua.billeasetask.data.mapper.toAuthorization
import com.chua.billeasetask.data.remote.AuthorizationApi
import com.chua.billeasetask.domain.model.Authorization
import com.chua.billeasetask.domain.repository.AuthorizationRepository
import com.chua.billeasetask.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationRepositoryImpl @Inject constructor(
    private val api: AuthorizationApi
) : AuthorizationRepository {

    override suspend fun login(): Flow<Resource<Authorization>> {
        return flow {
            emit(Resource.Loading(true))

            val auth: Authorization? = try {
                val response = api.login()
                if (response.isSuccessful) {
                    response.body()?.toAuthorization()
                } else {
                    emit(Resource.Error(response.body()?.message ?: "Error"))
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Error"))
                null
            }

            auth?.let {
                emit(Resource.Success(it))
            }
            emit(Resource.Loading(false))
        }
    }
}