package com.example.kaspicourse.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kaspicourse.R
import com.example.kaspicourse.models.VideoObjects
import com.example.kaspicourse.ui.VideoPlayerView
import kotlinx.android.synthetic.main.search_fragment_items.view.*
import kotlinx.android.synthetic.main.view_videplayer.view.*

class KyzyktyKontentAdapter : RecyclerView.Adapter<KyzyktyKontentAdapter.ViewHolder>() {

    private var videoObjects = mutableListOf<VideoObjects>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KyzyktyKontentAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = videoObjects.size

    override fun onBindViewHolder(holder: KyzyktyKontentAdapter.ViewHolder, position: Int) {
        holder.bind(videoObjects[position])
    }

    fun setItems(list: List<VideoObjects>) {
        videoObjects.addAll(list)
        notifyDataSetChanged()
        Log.d("msg", "msr")
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.search_fragment_items, parent, false)) {

        fun bind(post: VideoObjects) {
            val videoPlayerView: VideoPlayerView = itemView.videoPlayerView
            videoPlayerView.titleTextView.text = post.title
            videoPlayerView.playButton.setOnClickListener {
                videoPlayerView.play(post.videoURL)
            }
        }
    }
}