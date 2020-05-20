package com.example.kaspicourse.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kaspicourse.R
import com.example.kaspicourse.adapters.FavoriteMessageAdapter
import com.example.kaspicourse.adapters.KyzyktyKontentAdapter
import com.example.kaspicourse.models.MessageData
import com.example.kaspicourse.models.VideoObjects
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var recyclerView: RecyclerView
    private var kyzyktyKontentAdapter: KyzyktyKontentAdapter? = null
    private var videoObjects = mutableListOf<VideoObjects>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = view.findViewById(R.id.searchRecycler)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = kyzyktyKontentAdapter
    }

    private fun updateVideoList() {
        val videoURL1 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val videoTitle1 = "Kelinka Sabina 2, 2020"

        val videoURL2 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val videoTitle2 = "Kelinka Sabina 2, 2020"

        val videoURL3 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val videoTitle3 = "Kelinka Sabina 2, 2020"

        videoObjects = mutableListOf(
            VideoObjects(videoTitle1, videoURL1),
            VideoObjects(videoTitle2, videoURL2),
            VideoObjects(videoTitle3, videoURL3)
        )

        kyzyktyKontentAdapter?.setItems(videoObjects)
    }
}
