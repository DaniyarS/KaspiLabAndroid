package com.example.kaspicourse

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorite_message_items.view.*

class FavoriteMessageAdapter : RecyclerView.Adapter<FavoriteMessageAdapter.ViewHolder>() {

    val msg1 = "Абай (Ибраһим) Құнанбаев (1845-1904) — ақын, ағартушы, жазба қазақ әдебиетінің, қазақ әдеби тілінің негізін қалаушы, философ, композитор"
    val msg2 = "Abai (Ibrahim) Kunanbayev (1845-1904) - poet, educator, founder of written Kazakh literature, Kazakh literary language, philosopher, composer"
    val msg3 = "Сәлем, қалайсың?"
    val msg4 = "Hello, how are you?"

//    val messages = listOf(MessageData(msg1, msg2), MessageData(msg3, msg4))

    private val message = mutableListOf<MessageData>(MessageData(msg1, msg2), MessageData(msg3, msg4))
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
    }

//    fun setItems(list: List<MessageData>) {
//        message.clear()
//        message.addAll(list)
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int = message.size

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.favorite_message_items, parent, false)) {
        private val tvInsert = itemView.tvSend
        private val tvResult = itemView.tvReceived

        fun bind(message: MessageData) {
            tvInsert.text = message.sendedMessage
            tvResult.text = message.receivedMessage
        }
    }
}
