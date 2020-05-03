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

    private val translateFragment = TranslateFragment()
    private val favoriteFragment = FavoriteFragment()
    private val mainFragment = MainFragment()
    private val searchFragment = SearchFragment()
    private val accountFragment = AccountFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId){
            R.id.navTranslate -> {
                setFragment(translateFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navFavorite -> {
                setFragment(favoriteFragment)
                return@OnNavigationItemSelectedListener  true
            }
            R.id.navMain ->{
                setFragment(mainFragment)
                return@OnNavigationItemSelectedListener  true
            }
            R.id.navSearch ->{
                setFragment(searchFragment)
                return@OnNavigationItemSelectedListener  true
            }
            R.id.navAccount ->{
                setFragment(accountFragment)
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
