package com.chua.billeasetask.data.remote

import com.chua.billeasetask.data.remote.dto.AuthorizationResponse
import retrofit2.Response
import retrofit2.http.GET

interface AuthorizationApi {

    @GET("v3/e50a3df8-49fd-4b3f-ab05-b0937fedcf6e")
    suspend fun login(): Response<AuthorizationResponse>

    companion object {
        const val BASE_URL = "https://run.mocky.io/"
    }
}