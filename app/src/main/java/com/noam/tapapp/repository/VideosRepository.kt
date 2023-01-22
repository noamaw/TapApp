package com.noam.tapapp.repository

import com.noam.tapapp.data.api.VideoResponse
import com.noam.tapapp.data.api.YoutubeService
import com.noam.tapapp.utils.detectYoutubeUrls
import common.BaseApiResponse
import common.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityRetainedScoped
class VideosRepository @Inject constructor(private val apiService: YoutubeService, private val defaultDispatcher: CoroutineDispatcher) : BaseApiResponse() {
    suspend fun getVideos() : NetworkResult<VideoResponse> {
        return withContext(defaultDispatcher){safeApiCall { apiService.getVideos() }}
    }

}