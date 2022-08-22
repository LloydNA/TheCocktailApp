package com.example.thecocktailapp.tcaUI.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thecocktailapp.misc.TCAXCocktailData
import com.example.thecocktailapp.databinding.SearchMenuResultViewBinding
import com.squareup.picasso.Picasso

class TCASearchResultsAdapter(var arClickListener : (arPosition : Int) -> (Unit), var arIsDailySearchMode : ArrayList<Boolean>) : RecyclerView.Adapter<TCASearchResultsAdapter.ViewHolder>() {

    class ViewHolder(arBinding : SearchMenuResultViewBinding) : RecyclerView.ViewHolder(arBinding.root) {
        val obBinding : SearchMenuResultViewBinding = arBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var loInflater = LayoutInflater.from(parent.context)

        var loBinding = SearchMenuResultViewBinding.inflate(loInflater)

        var loParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        loParams.setMargins(0,0,0,15)

        loBinding.root.layoutParams = loParams

        return ViewHolder(loBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val loCurrentDrink = if(arIsDailySearchMode[0]) TCAXCocktailData.sDailyInstance.obCocktailData.getJSONObject(position) else TCAXCocktailData.sQueryInstance.obCocktailData.getJSONObject(position)

        holder.obBinding.root.setOnClickListener{
            arClickListener(position)
        }

        holder.obBinding.searchResultDrinkName.text = loCurrentDrink.getString("strDrink")
        holder.obBinding.searchResultDrinkCategory.text = loCurrentDrink.getString("strCategory")
        holder.obBinding.searchResultDrinkAlcoholic.text = loCurrentDrink.getString("strAlcoholic")

        Picasso.get().load(loCurrentDrink.getString("strDrinkThumb")).fit().centerInside().into(holder.obBinding.searchResultImage)
    }

    override fun getItemCount(): Int {
        return if(arIsDailySearchMode[0]) TCAXCocktailData.sDailyInstance.obCocktailData.length() else TCAXCocktailData.sQueryInstance.obCocktailData.length()
    }
}