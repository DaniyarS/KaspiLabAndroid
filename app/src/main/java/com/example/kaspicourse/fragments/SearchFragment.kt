package com.example.kaspicourse.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kaspicourse.R
import com.example.kaspicourse.adapters.KyzyktyKontentAdapter
import com.example.kaspicourse.models.VideoObjects
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val kyzyktyKontentAdapter = KyzyktyKontentAdapter()
    private var videoObjects = mutableListOf<VideoObjects>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateVideoList()
        val linearLayoutManager = LinearLayoutManager(activity?.applicationContext)
        searchRecycler.layoutManager = linearLayoutManager
        searchRecycler.adapter = kyzyktyKontentAdapter
    }

    private fun updateVideoList() {
        val videoURL1 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val videoTitle1 = "Kelinka Sabina 1, 2017"

        val videoURL2 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        val videoTitle2 = "Побег из аула 2, 2018"

        val videoURL3 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
        val videoTitle3 = "Kelinka Sabina 3, 2020"

        videoObjects = mutableListOf(
            VideoObjects(videoTitle1, videoURL1),
            VideoObjects(videoTitle2, videoURL2),
            VideoObjects(videoTitle3, videoURL3)
        )

        kyzyktyKontentAdapter.setItems(videoObjects)
    }
}
