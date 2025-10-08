package com.orizzonter.app.core.network

import com.orizzonter.app.core.models.LoginRequest
import com.orizzonter.app.core.models.LoginResponse
import com.orizzonter.app.core.models.RegisterRequest
import com.orizzonter.app.core.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<LoginResponse>

    @GET("user")
    suspend fun getCurrentUser(): Response<User>

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>
}