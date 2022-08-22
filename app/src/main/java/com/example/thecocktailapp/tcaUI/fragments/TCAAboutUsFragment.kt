package com.example.thecocktailapp.tcaUI.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thecocktailapp.databinding.AboutUsFragmentBinding

class TCAAboutUsFragment : Fragment() {
    lateinit var obBinding: AboutUsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        obBinding = AboutUsFragmentBinding.inflate(inflater, container, false)

        return obBinding.root
    }

}