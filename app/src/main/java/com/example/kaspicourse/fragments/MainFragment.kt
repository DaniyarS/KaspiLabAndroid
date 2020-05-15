package com.example.kaspicourse.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.kaspicourse.*
import com.example.kaspicourse.adapters.MessageAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.StringBuilder
import kotlin.system.exitProcess

private const val MESSAGE_LIST = "message_list"

class MainFragment : Fragment() {

    private lateinit var btSend: ImageView
    private lateinit var tvEdit: EditText
    private var prefs: SharedPreferences? = null
    private var messages = mutableListOf<MessageData>()
    private var messageAdapter: MessageAdapter? = null
    private var messageLayoutManager: LinearLayoutManager? = null
    private var click = 0
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        var y = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            y = dy
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when (newState) {
                RecyclerView.SCROLL_STATE_DRAGGING -> {
                    if (y<=0) {
                        scrollButton.visibility = View.VISIBLE
                    } else {
                        scrollButton.visibility = View.GONE
                        y = 0
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        btSend = view.findViewById(R.id.btSend)!!
        tvEdit = view.findViewById(R.id.editText)!!

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        themeButton.setOnClickListener {
            val themePreferences = context?.let { it1 -> ThemePreferences(it1) }
            if ( AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO ) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themePreferences?.setThemeState("dark")
                restartApp()
                Log.d("click", "worksss")
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                restartApp()
                themePreferences?.setThemeState("white")
                Log.d("click", "works")
            }
        }
        loadJSON()
        setupItems()
    }

    override fun onResume() {
        super.onResume()
        messageRecycler.addOnScrollListener(scrollListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        saveJSON()
    }

    private fun setupItems() {
        messageAdapter = MessageAdapter()
        messageLayoutManager = LinearLayoutManager(activity?.baseContext, LinearLayoutManager.VERTICAL, false)
        messageLayoutManager!!.stackFromEnd = true
        messageRecycler.adapter = messageAdapter
        messageRecycler.layoutManager = messageLayoutManager
        messageAdapter!!.setItems(messages)

        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int =
                SNAP_TO_START
        }

        btSend.setOnClickListener {
            if (tvEdit.text.toString() != "") {
                val message = MessageData()
                message.sendedMessage = tvEdit.text.toString()
                message.receivedMessage = changeToLatin(tvEdit.text.toString())
                messages.add(message)
                messageAdapter!!.setItems(messages)
                tvEdit.text = null
            }
            smoothScroller.targetPosition = messages.size - 1
            messageLayoutManager!!.startSmoothScroll(smoothScroller)
        }

        scrollButton.setOnClickListener {
            if (messages.size > 50) {
            messageLayoutManager!!.scrollToPosition(messages.size - 5)
            smoothScroller.targetPosition = messages.size - 1
            messageLayoutManager!!.startSmoothScroll(smoothScroller)
            } else {
                smoothScroller.targetPosition = messages.size - 1
                messageLayoutManager!!.startSmoothScroll(smoothScroller)
            }
            scrollButton.visibility = View.GONE
        }

        val itemDecoration = MessageItemDecoration(10, 20)
        messageRecycler.addItemDecoration(itemDecoration)
    }

    private fun saveJSON() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.clear()
        val gson = Gson()
        val json: String = gson.toJson(messages)
        editor?.putString(MESSAGE_LIST, json)
        editor?.apply()
    }

    private fun loadJSON() {
        val json: String? = prefs?.getString(MESSAGE_LIST, null)
        val turnsType = object : TypeToken<List<MessageData>>() {}.type
        val turns = Gson().fromJson<List<MessageData>>(json, turnsType)
        try {
            messages.clear()
            messages.addAll(turns)
        } catch (e: Exception) {

        }
    }

    private fun changeToLatin(text: String): String {
        val message = StringBuilder()

        val cyrillic: Array<Char> = arrayOf(' ','а','б','в','г','д','е','ё', 'ж','з','и','й','к',
            'л','м','н','о', 'п','р','с','т','у','ф','х', 'ц','ч', 'ш','щ','ъ','ы','ь','э', 'ю',
            'я','А','Б','В','Г','Д','Е','Ё','Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У',
            'Ф','Х', 'Ц', 'Ч','Ш', 'Щ','Ъ','Ы','Ь','Э','Ю','Я','a','b','c','d','e','f','g','h','i',
            'j','k','l','m','n','o','p','q','r','s','t','u','v','w', 'x','y','z','A','B','C','D',
            'E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V', 'W','X','Y','Z')

        val latin: Array<String> = arrayOf(" ","a","b","v","g","d","e","e","zh","z","i","y","k","l",
            "m","n","o","p","r","s","t","u","f","h","ts","ch","sh","sch", "","i", "","e","ju","ja",
            "A","B","V","G","D","E","E","Zh","Z","I","Y","K","L","M","N","O","P","R","S","T","U","F",
            "H","Ts","Ch","Sh","Sch", "","I", "","E","Ju","Ja","a","b","c","d","e","f","g","h","i",
            "j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E",
            "F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")

        for (i in text.indices) {
            for (j in cyrillic.indices) {
                Log.d("size", cyrillic.size.toString())
                if (text[i] == (cyrillic[j])) {
                    message.append(latin[j])
                }
            }
        }
        return message.toString()
    }

    private fun restartApp() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }

}
