package com.example.kaspicourse

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorite_message_items.view.*

class FavoriteMessageAdapter : RecyclerView.Adapter<FavoriteMessageAdapter.ViewHolder>() {

    private val message = mutableListOf<MessageData>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMessageAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: FavoriteMessageAdapter.ViewHolder, position: Int) {
        holder.bind(message[position])
        holder.delete(position)
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
}
