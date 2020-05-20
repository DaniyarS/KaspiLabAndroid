package com.example.kaspicourse.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kaspicourse.ItemMoveCallbackListener
import com.example.kaspicourse.adapters.FavoriteMessageAdapter
import com.example.kaspicourse.models.MessageData
import com.example.kaspicourse.OnStartDragListener
import com.example.kaspicourse.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(R.layout.fragment_favorite), OnStartDragListener {

    private lateinit var recyclerView: RecyclerView
    private var favoriteMessageAdapter: FavoriteMessageAdapter? = null
    private var message = mutableListOf<MessageData>()
    private lateinit var touchHelper: ItemTouchHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = view.findViewById(R.id.favoriteRecycler)
        recyclerView.layoutManager = linearLayoutManager
        favoriteMessageAdapter = FavoriteMessageAdapter(this) {position ->
            favoriteMessageAdapter?.removeItem(position)
            Snackbar.make(favoriteContainer, R.string.snackbar, 3000).setAction(R.string.snackbar_action){

            }.show()
        }
        recyclerView.adapter = favoriteMessageAdapter
        updateFavorite()
        favoriteMessageAdapter?.let {
            val callback: ItemTouchHelper.Callback = ItemMoveCallbackListener(it)
            touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(recyclerView)
        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        if (viewHolder != null) {
            touchHelper.startDrag(viewHolder)
        }
    }

    private fun updateFavorite() {
        val msg1 = "Абай (Ибраһим) Құнанбаев (1845-1904) — ақын, ағартушы, жазба қазақ әдебиетінің," +
                " қазақ әдеби тілінің негізін қалаушы, философ, композитор"
        val msg2 = "Abai (Ibrahim) Kunanbayev (1845-1904) - poet, educator, founder of written Kazakh" +
                " literature, Kazakh literary language, philosopher, composer"
        val msg3 = "Сәлем, қалайсың?"
        val msg4 = "Hello, how are you?"
        val msg5 = "Абай (Ибраһим) Құнанбаев (1845-1904) — ақын, ағартушы, жазба қазақ әдебиетінің," +
                " қазақ әдеби тілінің негізін қалаушы, философ, композитор"
        val msg6 = "Abai (Ibrahim) Kunanbayev (1845-1904) - poet, educator, founder of written Kazakh" +
                " literature, Kazakh literary language, philosopher, composer"

        message = mutableListOf(
            MessageData(msg1, msg2),
            MessageData(msg3, msg4),
            MessageData(msg5, msg6)
        )

        favoriteMessageAdapter?.setItems(message)
    }
}
