package com.vladdrummer.uchebaandroid.api

import retrofit2.http.Query


object Repo: Api {

    private val api =
            RetrofitServiceGenerator.createService(
                    Api::class.java,
                    "http://10.0.2.2"
            )

    override suspend fun login(login: String, pass: String) = api.login(login, pass)
}