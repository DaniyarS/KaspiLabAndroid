package com.example.kaspicourse.ui

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.kaspicourse.R
import kotlinx.android.synthetic.main.view_videplayer.view.*

class VideoPlayerView
@JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    private var isSeekBarVisible = false
    private var isPlayButtonVisible = true
    private var isPauseButtonVisible = false
    private lateinit var runnable: Runnable
    private val handlerView: Handler? = null

    init {
        inflate(context, R.layout.view_videplayer, this)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.VideoPlayerView,
                defStyleAttr,
                0
            )
            isSeekBarVisible = typedArray.getBoolean(
                R.styleable.VideoPlayerView_isSeekBarVisible,
                isSeekBarVisible
            )
            seekBar.isVisible = isSeekBarVisible
            playButton.isVisible = isPlayButtonVisible
            pauseButton.isVisible = isPauseButtonVisible
            typedArray.recycle()
        }
    }

    fun play(url: String) {
        videoView.setVideoPath(url)
        videoView.start()
        videoView.setOnPreparedListener {
            seekBar.max = it.duration
            listenPlayerTrack()
        }
    }



    private fun listenPlayerTrack(){
        runnable = Runnable {
            seekBar.progress = videoView.currentPosition
            handlerView?.postDelayed(runnable, 100)
        }
        handlerView?.postDelayed(runnable, 100)
    }
}