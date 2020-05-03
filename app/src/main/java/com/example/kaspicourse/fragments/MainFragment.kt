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
import android.widget.TextView
import com.example.kaspicourse.R

private const val MESSAGE_INSERT = "i_message_key"
private const val MESSAGE_EDITOR = "e_message_key"

class MainFragment : Fragment() {

    private lateinit var btSend: ImageView
    private lateinit var tvInsert: TextView
    private lateinit var tvResult: TextView
    private lateinit var tvEdit: EditText
    private var prefs: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        btSend = view.findViewById(R.id.btSend)!!
        tvInsert = view.findViewById(R.id.tvInsert)!!
        tvResult = view.findViewById(R.id.tvResult)!!
        tvEdit = view.findViewById(R.id.editText)!!

        tvInsert.visibility = View.GONE
        tvResult.visibility = View.GONE

        btSend.setOnClickListener {
            if (tvEdit.text != null) {
                tvInsert.visibility = View.VISIBLE
                tvInsert.text = tvEdit.text
                tvResult.text = ""
                changeToLatin(tvInsert.text.toString())
                tvEdit.text = null
            }
        }

        if (prefs?.getString(MESSAGE_INSERT, "")!=null) {
            tvInsert.visibility = View.VISIBLE
            tvInsert.text = prefs?.getString(MESSAGE_INSERT, "")
            tvResult.text = ""
            changeToLatin(tvInsert.text.toString())
            tvEdit.text = null
        }

        tvEdit.append(prefs?.getString(MESSAGE_EDITOR, ""))

        return view
    }

    private fun changeToLatin(text: String) {

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
                        tvResult.append(latin[j])
                    }
                }
            }

        tvResult.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        val editor: SharedPreferences.Editor? = prefs?.edit()
        editor?.putString(MESSAGE_INSERT, tvInsert.text.toString())
        editor?.putString(MESSAGE_EDITOR, tvEdit.text.toString())
        editor?.apply()
    }
}
