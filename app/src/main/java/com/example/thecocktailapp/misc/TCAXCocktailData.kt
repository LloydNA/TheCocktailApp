package com.example.thecocktailapp.misc

import org.json.JSONArray

class TCAXCocktailData {
    companion object{
        var sRandomInstance = TCAXCocktailData()
        var sQueryInstance = TCAXCocktailData()
        var sDailyInstance = TCAXCocktailData()
    }

    var obCocktailData : JSONArray = JSONArray()

    public fun parseJSON(arParsedJSON : JSONArray){
        obCocktailData = arParsedJSON
    }
}