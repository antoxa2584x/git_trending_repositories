package com.git.trendingrepositories.di

import android.content.Context
import com.git.trendingrepositories.data.remote.search.SearchService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    companion object {
        private const val BASE_URL = "https://api.github.com"
        private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
    }

    @Provides
    @Singleton
    internal fun provideCache(@ApplicationContext context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "api_cache")
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                val request: Request =
                    chain.request().newBuilder().addHeader(
                        "Authorization",
                        "Bearer USE_YOUR_OWN_API_KEY"
                    ).build()
                chain.proceed(request)
            })
            .cache(cache)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        .serializeNulls().create()
                )
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchService(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }
}
