package com.example.thecocktailapp.writers

import com.example.thecocktailapp.misc.TCAXCocktailData
import com.example.thecocktailapp.misc.TCAXUtilitiesInstance
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.lang.Exception
import java.net.URL

class TCAXCocktailWriter {
    companion object{
        var sInstance = TCAXCocktailWriter()
    }

    public fun writeIntoRandom(){
        var loApiResponse = URL(TCAXUtilitiesInstance.apiLink+"random.php").readText()

        var loMainRawData = JSONTokener(loApiResponse).nextValue() as JSONObject

        try{
            var loRawDrinksArray = JSONTokener(loMainRawData.getString("drinks")).nextValue() as JSONArray

            TCAXCocktailData.sRandomInstance.parseJSON(loRawDrinksArray)
            EventBus.getDefault().post("random")
        }catch (e:Exception){
            EventBus.getDefault().post("no_results")
        }
    }

    public fun writeIntoCustom(arCustomQuery : String){
        var loApiResponse = URL(TCAXUtilitiesInstance.apiLink+"search.php?s="+arCustomQuery).readText()

        var loMainRawData = JSONTokener(loApiResponse).nextValue() as JSONObject

        try{
            var loRawDrinksArray = JSONTokener(loMainRawData.getString("drinks")).nextValue() as JSONArray

            TCAXCocktailData.sQueryInstance.parseJSON(loRawDrinksArray)
            EventBus.getDefault().post("search")
        }catch (e:Exception){
            EventBus.getDefault().post("no_results")
        }
    }

    public fun writeIntoDaily(){
        var loApiResponse = URL(TCAXUtilitiesInstance.apiLink+"randomselection.php").readText()

        var loMainRawData = JSONTokener(loApiResponse).nextValue() as JSONObject

        try{
            var loRawDrinksArray = JSONTokener(loMainRawData.getString("drinks")).nextValue() as JSONArray

            TCAXCocktailData.sDailyInstance.parseJSON(loRawDrinksArray)
            EventBus.getDefault().post("daily")
        }catch (e:Exception){
            EventBus.getDefault().post("no_results")
        }
    }
}