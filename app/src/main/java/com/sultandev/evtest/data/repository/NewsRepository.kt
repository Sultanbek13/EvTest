package com.sultandev.evtest.data.repository

import com.sultandev.evtest.data.model.News
import io.reactivex.rxjava3.core.Single

interface NewsRepository {

    fun getNews(): Single<News>

}