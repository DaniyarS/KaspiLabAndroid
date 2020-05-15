package com.example.kaspicourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.kaspicourse.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.navTranslate -> {
                setFragment(TranslateFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navFavorite -> {
                setFragment(FavoriteFragment())
                return@OnNavigationItemSelectedListener  true
            }
            R.id.navMain ->{
                setFragment(MainFragment())
                return@OnNavigationItemSelectedListener  true
            }
            R.id.navSearch ->{
                setFragment(SearchFragment())
                return@OnNavigationItemSelectedListener  true
            }
            R.id.navAccount ->{
                setFragment(AccountFragment())
                return@OnNavigationItemSelectedListener  true
            }
        }
        false
    }

    private fun setFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFrame, fragment)
        fragmentTransaction.commit()
    }
}
