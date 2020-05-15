package com.example.kaspicourse.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.kaspicourse.MessageData
import com.example.kaspicourse.R
import kotlinx.android.synthetic.main.message_dialog.*
import kotlinx.android.synthetic.main.message_items.view.*

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private val message = mutableListOf<MessageData>()
    private var myDialog: Dialog? = null
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = message.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(message[position])
    }

    fun setItems(list: List<MessageData>) {
        message.clear()
        message.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(
        R.layout.message_items, parent, false)) {
        private val tvInsert = itemView.tvInsert
        private val tvResult = itemView.tvResult
        private var messageDialog: CardView? = null

        fun bind(message: MessageData) {
            messageDialog = itemView.findViewById(R.id.messageDialog)
            tvInsert.text = message.sendedMessage
            tvResult.text = message.receivedMessage
            myDialog?.let { it ->
                myDialog = context?.let { Dialog(it) }
                it.setContentView(R.layout.message_dialog)
                it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }

            tvInsert.setOnClickListener {
                myDialog!!.show()
                myDialog!!.tvFavorite.setOnClickListener {
                    Toast.makeText(context,"Clicked", Toast.LENGTH_SHORT).show()
                }
            }

            tvResult.setOnClickListener {
                myDialog!!.show()
            }
        }
    }
}
