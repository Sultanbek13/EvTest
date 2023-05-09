package com.sultandev.evtest.di


import com.sultandev.evtest.BuildConfig
import com.sultandev.evtest.data.api.NewsApi
import com.sultandev.evtest.data.repository.NewsRepository
import com.sultandev.evtest.data.repository.impl.NewsRepositoryImpl
import com.sultandev.evtest.presentation.ui.news.NewsViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(client)
        .build()

    fun provideClient(): OkHttpClient = OkHttpClient.Builder()
        .build()

    fun provideApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    single { provideRetrofit(get()) }
    single { provideClient() }
    single { provideApi(get()) }
    single<NewsRepository> { NewsRepositoryImpl(get()) }

}

val viewModelModule = module {

    viewModel { NewsViewModel(get()) }

}