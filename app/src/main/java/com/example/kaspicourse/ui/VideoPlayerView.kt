package com.example.kaspicourse.ui

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.SeekBar
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
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    videoView.seekTo(seekBar?.progress ?: 0)
                    videoView.start()
                }
            })
        }
    }

    fun play(url: String) {
        val path = Uri.parse(url)
        videoView.setVideoURI(path)
        videoView.start()
        isPlayButtonVisible = false
        isPauseButtonVisible = true
        Thread.sleep(5000)
        isPauseButtonVisible = false
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