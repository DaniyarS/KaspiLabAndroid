package com.example.kaspicourse.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kaspicourse.FavoriteMessageAdapter
import com.example.kaspicourse.MessageData
import com.example.kaspicourse.R

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var favoriteMessageAdapter: FavoriteMessageAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = view.findViewById(R.id.favoriteRecycler)
        recyclerView.layoutManager = linearLayoutManager
        favoriteMessageAdapter = FavoriteMessageAdapter()
        recyclerView.adapter = favoriteMessageAdapter
        return view
    }


    private fun generate(): List<MessageData> {
        val msg1 = "Абай (Ибраһим) Құнанбаев (1845-1904) — ақын, ағартушы, жазба қазақ әдебиетінің, қазақ әдеби тілінің негізін қалаушы, философ, композитор"
        val msg2 = "Abai (Ibrahim) Kunanbayev (1845-1904) - poet, educator, founder of written Kazakh literature, Kazakh literary language, philosopher, composer"
        val msg3 = "Сәлем, қалайсың?"
        val msg4 = "Hello, how are you?"

        return listOf(MessageData(msg1, msg2), MessageData(msg3, msg4))
    }
}
