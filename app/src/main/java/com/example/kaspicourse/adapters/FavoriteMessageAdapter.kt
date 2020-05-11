package com.example.kaspicourse.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kaspicourse.ItemMoveCallbackListener
import com.example.kaspicourse.MessageData
import com.example.kaspicourse.OnStartDragListener
import com.example.kaspicourse.R
import kotlinx.android.synthetic.main.favorite_message_items.view.*
import java.util.*

class FavoriteMessageAdapter(private val startDragListener: OnStartDragListener) : RecyclerView.Adapter<FavoriteMessageAdapter.ViewHolder>(),
    ItemMoveCallbackListener.Listener {

    private val message = mutableListOf<MessageData>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(message[position])
        holder.delete(position)
        holder.itemView.btDrag.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                this.startDragListener.onStartDrag(holder)
            }
            return@setOnTouchListener true
        }
    }

    override fun getItemCount(): Int = message.size

    fun setItems(list: List<MessageData>) {
        message.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.favorite_message_items, parent, false)) {
        private val tvInsert = itemView.tvSend
        private val tvResult = itemView.tvReceived
        private val btDelete = itemView.btDelete

        fun bind(message: MessageData) {
            tvInsert.text = message.sendedMessage
            tvResult.text = message.receivedMessage

        }

        fun delete(index: Int) {
            btDelete.setOnClickListener {
                message.removeAt(index)
                notifyDataSetChanged()
            }
        }
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(message, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(message, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(itemViewHolder: ViewHolder) {
    }

    override fun onRowClear(itemViewHolder: ViewHolder) {
    }
}
