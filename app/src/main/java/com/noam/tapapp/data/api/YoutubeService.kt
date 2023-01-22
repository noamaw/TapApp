package com.noam.tapapp.data.api

import retrofit2.Response
import retrofit2.http.GET

interface YoutubeService {

    @GET("results?search_query=ronaldo")
    suspend fun getVideos(): Response<VideoResponse>
}