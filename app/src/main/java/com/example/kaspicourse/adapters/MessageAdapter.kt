package com.example.kaspicourse.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kaspicourse.models.MessageData
import com.example.kaspicourse.R
import kotlinx.android.synthetic.main.message_items.view.*

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private val message = mutableListOf<MessageData>()
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
        View.OnCreateContextMenuListener,
        RecyclerView.ViewHolder(
            inflater.inflate(
                R.layout.message_items, parent, false
            )
        ) {

        private val tvInsert = itemView.tvInsert
        private val tvResult = itemView.tvResult

        fun bind(message: MessageData) {
            tvInsert.text = message.sendedMessage
            tvResult.text = message.receivedMessage

            tvInsert.setOnCreateContextMenuListener(this)
            tvResult.setOnCreateContextMenuListener(this)
        }

        @SuppressLint("RestrictedApi")
       // @RequiresApi(Build.VERSION_CODES.Q)
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val popupMenu = PopupMenu(context, v)
                popupMenu.inflate(R.menu.message_items)
                popupMenu.setForceShowIcon(true)
                popupMenu.show()
            } else {
                val menuBuilder = MenuBuilder(context)
                val menuInflater = MenuInflater(context)
                menuInflater.inflate(R.menu.message_items, menuBuilder)
                val popupMenuHelper = context?.let { MenuPopupHelper(it, menuBuilder) }
                popupMenuHelper?.setForceShowIcon(true)
                popupMenuHelper?.show()
            }
        }
    }
}
