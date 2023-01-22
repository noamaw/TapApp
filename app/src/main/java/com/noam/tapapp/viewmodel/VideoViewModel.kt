package com.noam.tapapp.viewmodel

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noam.tapapp.data.api.VideoResponse
import com.noam.tapapp.repository.VideosRepository
import com.noam.tapapp.utils.detectYoutubeUrls
import common.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel  @Inject constructor(
    private val videoRepository: VideosRepository) : ViewModel() {

    init {
        getUrls()
    }



    val snapshotStateList = SnapshotStateList<VideoResponse>()
    val snapshotUrlsStateList = SnapshotStateList<String>()

    private fun getVideos() = viewModelScope.launch {
        when (val result = videoRepository.getVideos()) {
            is NetworkResult.Success -> {
                Log.d("TAG", "getVideos: ${result.data}")
                result.data?.let { snapshotStateList.addAll(listOf(it)) }
            }
            else -> {

            }
        }


    }

    private fun getUrls() = viewModelScope.launch {
        when (val result = videoRepository.getVideos()) {
            is NetworkResult.Success -> {
                Log.d("TAG", "getVideos: ${result.data}")
                result.data?.let { snapshotUrlsStateList.addAll(detectYoutubeUrls(it.url)) }
            }
            else -> {
                Log.d("TAG", "getVideos: ${result.message}")
            }
        }


    }


}