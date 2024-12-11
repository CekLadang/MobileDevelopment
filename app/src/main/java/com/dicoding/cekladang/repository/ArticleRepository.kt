package com.dicoding.cekladang.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.cekladang.BuildConfig
import com.dicoding.cekladang.data.remote.response.ArticlesItem
import com.dicoding.cekladang.data.remote.retrofit.ApiService
import com.dicoding.cekladang.helper.Result

class ArticleRepository(
    private val apiService: ApiService,
) {

    fun getSearchNews(): LiveData<Result<List<ArticlesItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getArticle("us", "health", BuildConfig.API_KEY)
            if (response.status == "ok") {
                emit(Result.Success(response.articles?.filterNotNull() ?: emptyList()))
            } else {
                emit(Result.Error("error di repository"))
            }
        } catch (e: Exception) {
            Log.d("NewsRepository", "getSearchNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }
}