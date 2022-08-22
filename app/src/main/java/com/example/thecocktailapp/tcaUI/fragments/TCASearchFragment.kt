package com.example.thecocktailapp.tcaUI.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecocktailapp.R
import com.example.thecocktailapp.writers.TCAXCocktailWriter
import com.example.thecocktailapp.databinding.SearchMenuFragmentBinding
import com.example.thecocktailapp.tcaUI.adapters.TCASearchResultsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TCASearchFragment : Fragment() {
    lateinit var obBinding : SearchMenuFragmentBinding
    var obIsAutoSearch : ArrayList<Boolean> = arrayListOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        obBinding = SearchMenuFragmentBinding.inflate(inflater,container,false)

        obBinding.SearchResultsRecyclerView.layoutManager = LinearLayoutManager(this.context,
            LinearLayoutManager.VERTICAL,false)

        obBinding.SearchResultsRecyclerView.adapter = TCASearchResultsAdapter(::displaySelectedcocktail, obIsAutoSearch)

        GlobalScope.launch(Dispatchers.IO){
            TCAXCocktailWriter.sInstance.writeIntoDaily()
        }

        obBinding.SearchTextField.addTextChangedListener {
            if(obBinding.SearchTextField.text.isNotBlank()){
                GlobalScope.launch(Dispatchers.IO){
                    TCAXCocktailWriter.sInstance.writeIntoCustom(obBinding.SearchTextField.text.toString())
                }
            }
            else{
                EventBus.getDefault().post("daily")
            }
        }

        obBinding.SearchRandomButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){
                TCAXCocktailWriter.sInstance.writeIntoRandom()
            }
        }

        return obBinding.root
    }

    override fun onStart(){
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop(){
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onPause() {
        super.onPause()
        obBinding.SearchTextField.text.clear()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEvent(arSearchMode : String){
        when(arSearchMode){
            "search" -> {
                obBinding.SearchNoResultsText.visibility = View.GONE
                obBinding.SearchResultsScrollView.visibility = View.VISIBLE
                obIsAutoSearch.clear()
                obIsAutoSearch.add(false)
                obBinding.SearchResultsRecyclerView.adapter?.notifyDataSetChanged()
            }
            "daily" -> {
                obBinding.SearchNoResultsText.visibility = View.GONE
                obBinding.SearchResultsScrollView.visibility = View.VISIBLE
                obIsAutoSearch.clear()
                obIsAutoSearch.add(true)
                obBinding.SearchResultsRecyclerView.adapter?.notifyDataSetChanged()
            }
            "random" -> {
                obBinding.SearchNoResultsText.visibility = View.GONE
                obBinding.SearchResultsScrollView.visibility = View.VISIBLE
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.MainFragmentHolder, TCACurrentSelectedCocktailFragment(0,'r'))
                    ?.addToBackStack(null)
                    ?.commit()
            }
            "no_results" -> {
                obBinding.SearchNoResultsText.visibility = View.VISIBLE
                obBinding.SearchResultsScrollView.visibility = View.GONE
            }
        }
    }

    fun displaySelectedcocktail(arPosition : Int){
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(
                R.id.MainFragmentHolder, TCACurrentSelectedCocktailFragment(arPosition, when(obIsAutoSearch[0]){
                true -> 'd'
                else -> 's'
            })
            )
            ?.addToBackStack(null)
            ?.commit()
    }

}