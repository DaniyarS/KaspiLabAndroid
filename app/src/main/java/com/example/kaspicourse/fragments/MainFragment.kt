package com.example.kaspicourse.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.example.kaspicourse.adapters.MessageAdapter
import com.example.kaspicourse.MessageData
import com.example.kaspicourse.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.StringBuilder

private const val MESSAGE_LIST = "message_list"

class MainFragment : Fragment() {

    private lateinit var btSend: ImageView
    private lateinit var tvEdit: EditText
    private var prefs: SharedPreferences? = null
    private var messages = mutableListOf<MessageData>()

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
        loadJSON()
        generate()
    }

    private fun generate() {
        val messageAdapter = MessageAdapter()
        val messageLayoutManager = LinearLayoutManager(activity?.baseContext, LinearLayoutManager.VERTICAL, false)
        messageLayoutManager.stackFromEnd = true
        messageRecycler.adapter = messageAdapter
        messageRecycler.layoutManager = messageLayoutManager
        messageAdapter.setItems(messages)

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
                messageAdapter.setItems(messages)
            }
            smoothScroller.targetPosition = messages.size - 1
            messageLayoutManager.startSmoothScroll(smoothScroller)
        }
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

    override fun onDestroy() {
        super.onDestroy()
        saveJSON()
    }
}
