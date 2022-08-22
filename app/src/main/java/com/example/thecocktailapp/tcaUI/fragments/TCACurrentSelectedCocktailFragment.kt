package com.example.thecocktailapp.tcaUI.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thecocktailapp.misc.TCAXCocktailData
import com.example.thecocktailapp.databinding.CurrentSelectedCocktailBinding
import com.example.thecocktailapp.misc.RoundedCornersTransform
import com.squareup.picasso.Picasso

class TCACurrentSelectedCocktailFragment(val arPosition : Int, val arSearchMode : Char) : Fragment() {
    lateinit var obBinding : CurrentSelectedCocktailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        obBinding = CurrentSelectedCocktailBinding.inflate(inflater,container,false)

        getData()

        return obBinding.root
    }

    private fun getData(){
        val loCurrentDrink = when(arSearchMode){
            'd' -> TCAXCocktailData.sDailyInstance.obCocktailData.getJSONObject(arPosition)
            's' -> TCAXCocktailData.sQueryInstance.obCocktailData.getJSONObject(arPosition)
            'r' -> TCAXCocktailData.sRandomInstance.obCocktailData.getJSONObject(arPosition)
            else -> TCAXCocktailData.sDailyInstance.obCocktailData.getJSONObject(arPosition)
        }

        Picasso.get().load(loCurrentDrink.getString("strDrinkThumb")).fit().centerInside().transform(RoundedCornersTransform()).into(obBinding.currentSelectedCocktailImage)

        obBinding.selectedCocktailNameText.text = loCurrentDrink.getString("strDrink")
        obBinding.selectedCocktailCategoryText.text = loCurrentDrink.getString("strCategory")
        obBinding.selectedCocktailAlcoholicText.text = loCurrentDrink.getString("strAlcoholic")

        var loNewIngredients : String = ""
        for(i in 1..15){
            if(loCurrentDrink.getString("strIngredient"+i.toString()) == "null")
                break

            loNewIngredients += loCurrentDrink.getString("strIngredient"+i.toString()) + "\n"
        }

        obBinding.selectedCocktailIngredientsText.text = loNewIngredients

        obBinding.selectedCocktailStepsText.text = loCurrentDrink.getString("strInstructions")
    }

}