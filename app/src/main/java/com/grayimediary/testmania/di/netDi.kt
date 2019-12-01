package com.grayimediary.testmania.di

import com.grayimediary.testmania.di.NetProperties.BASE_URL
import com.grayimediary.testmania.net.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetProperties {
    const val BASE_URL = "BASE_URL"
}

fun getBaseUrl() = "http://tst.std-259.ist.mospolytech.ru"

fun getGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

fun getLoggingInterceptor() =
    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

fun getClient(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

inline fun <reified T> createRetrofit(
    baseUrl: String,
    client: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): T = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(client)
    .addConverterFactory(gsonConverterFactory).build().create(T::class.java)

val apiModule = module {
    single(named(BASE_URL)) { getBaseUrl() }
    single { getGsonConverter() }
    single { getLoggingInterceptor() }
    single { getClient(get()) }
    single { createRetrofit<UserApi>(get(), get(), get()) }
}

