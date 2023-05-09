package com.sultandev.evtest.data.api

import com.sultandev.evtest.data.model.News
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface NewsApi {

    @GET("everything?q=tesla&sortBy=publishedAt")
    fun getNews(@Query("from") date: String, @Query("apiKey") key: String): Single<News>

}
