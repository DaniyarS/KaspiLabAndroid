package com.example.kaspicourse.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.core.view.isVisible
import com.example.kaspicourse.R
import kotlinx.android.synthetic.main.view_videplayer.view.*

@SuppressLint("SetTextI18n")
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
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar?.progress = videoView.currentPosition
                timeTextView.text = "${seekBar?.progress}" + "${videoView.duration}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                timeTextView.text = "${seekBar?.progress}" + "${videoView.duration}"
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                videoView.seekTo(seekBar?.progress ?: 0)
                videoView.start()
            }
        })
    }

    fun play(url: String) {
        val path = Uri.parse(url)
        videoView.setVideoPath(url/*context.assets.open("sample.mp4").toString()/*path*/*/)
        videoView.start()
        playButton.isVisible = false

        seekBar.max = videoView.duration
        listenPlayerTrack()
    }


    private fun listenPlayerTrack() {
        runnable = Runnable {
            seekBar.progress = videoView.currentPosition
            handlerView?.postDelayed(runnable, 100)
        }
        handlerView?.postDelayed(runnable, 100)
    }

}