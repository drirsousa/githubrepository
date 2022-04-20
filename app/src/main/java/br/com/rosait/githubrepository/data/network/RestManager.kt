package br.com.rosait.githubrespositories.data.network

import br.com.rosait.githubrepository.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestManager {
    companion object {

        private val gson: Gson by lazy { GsonBuilder().create() }

        private val okHttp: OkHttpClient by lazy {
            val client = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) {
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                client.addInterceptor(logInterceptor)
            }
            client.build()
        }

        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttp)
                .build()
        }
    }
}