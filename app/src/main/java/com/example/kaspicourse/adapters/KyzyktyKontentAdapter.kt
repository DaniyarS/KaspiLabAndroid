package com.example.kaspicourse.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.kaspicourse.R
import com.example.kaspicourse.models.MessageData
import com.example.kaspicourse.models.VideoObjects
import com.example.kaspicourse.ui.VideoPlayerView
import kotlinx.android.synthetic.main.view_videplayer.view.*

class KyzyktyKontentAdapter () : RecyclerView.Adapter<KyzyktyKontentAdapter.ViewHolder>() {

    private val videoObjects = mutableListOf<VideoObjects>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KyzyktyKontentAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = videoObjects.size ?: 0

    override fun onBindViewHolder(holder: KyzyktyKontentAdapter.ViewHolder, position: Int) {
        holder.bind(videoObjects[position])
    }

    fun setItems(list: List<VideoObjects>) {
        videoObjects.addAll(list)
        notifyDataSetChanged()
    }



    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.search_fragment_items, parent, false)) {
        @SuppressLint("ResourceType")
        val videoView: VideoPlayerView = itemView.findViewById(R.layout.view_videplayer)
        private val title: TextView = itemView.titleTextView

        fun bind(post: VideoObjects) {
            videoView.playButton.setOnClickListener {
                videoView.play(post.videoURL)
            }

            videoView.titleTextView.text = post.title
        }
    }
}