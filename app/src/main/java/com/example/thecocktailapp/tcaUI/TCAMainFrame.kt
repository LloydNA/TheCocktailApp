package com.example.thecocktailapp.tcaUI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.thecocktailapp.R
import com.example.thecocktailapp.databinding.TcaMainFrameBinding
import com.example.thecocktailapp.tcaUI.fragments.TCAAboutUsFragment
import com.example.thecocktailapp.tcaUI.fragments.TCASearchFragment

class TCAMainFrame : AppCompatActivity() {
    lateinit var obBinding : TcaMainFrameBinding
    lateinit var obSearchFragment : Fragment
    lateinit var obSavedFragment : Fragment
    lateinit var obAboutUsFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        obBinding = TcaMainFrameBinding.inflate(layoutInflater)

        obSearchFragment = TCASearchFragment()
        obAboutUsFragment = TCAAboutUsFragment()

        setCurrentFragment(obSearchFragment)

        obBinding.BottomNavigationBar.setOnNavigationItemSelectedListener{ loSelectedFragment ->
            when(loSelectedFragment.itemId){
                R.id.SearchMenuIcon ->setCurrentFragment(obSearchFragment)
                //R.id.SavedSearchesIcon->setCurrentFragment(obSavedFragment)
                R.id.AboutUsIcon ->setCurrentFragment(obAboutUsFragment)
            }

            true
        }

        setContentView(obBinding.root)
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(obBinding.MainFragmentHolder.id,fragment)
            commit()
        }
}