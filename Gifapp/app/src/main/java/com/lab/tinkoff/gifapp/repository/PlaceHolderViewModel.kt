package com.lab.tinkoff.gifapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab.tinkoff.gifapp.api.ApiInstance
import com.lab.tinkoff.gifapp.entity.api.Gif
import com.lab.tinkoff.gifapp.entity.api.ResponseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class PlaceHolderViewModel() : ViewModel()  {

    val responseApi : MutableLiveData<Response<ResponseApi>> = MutableLiveData()
    val list_gif : MutableLiveData<List<Gif>> = MutableLiveData()
    val current_gif : MutableLiveData<Gif> = MutableLiveData()
    var page = MutableLiveData(0)
    var current_gif_index = MutableLiveData(0)
    private lateinit var category: String

    fun getGifs(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = ApiInstance.api.get_gifs(category, page.value!!)
            responseApi.postValue(response)

            if (response.isSuccessful){
                val responseList = response.body()?.result

                list_gif.postValue(responseList)

                if (responseList != null && responseList.isNotEmpty()) {
                    current_gif.postValue(responseList[current_gif_index.value!!])
                }
            }
        }
    }


    fun getGifsNextPage(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = ApiInstance.api.get_gifs(category, page.value!!)
            responseApi.postValue(response)

            if (response.isSuccessful){
                val responseList = response.body()?.result

                if (responseList != null && responseList.isNotEmpty()) {

                    val temp = list_gif.value?.plus(responseList) as List<Gif>
                    list_gif.postValue(temp)
                    current_gif.postValue(temp[current_gif_index.value!!])
                }
            }
        }
    }


    fun nextGif(){
        val temp = current_gif_index.value?.plus(1)
        current_gif_index.postValue(temp)
        if (temp != null && temp >= list_gif.value?.size!!  ) {
            val new_page = page.value?.plus(1)
            page.value = new_page
            getGifsNextPage()
        }
        else{
            current_gif.postValue(temp?.let { list_gif.value?.get(it) })
        }

    }


    fun prevGif(){
        var temp = current_gif_index.value
        if (temp != null) {
            if (temp >= 1){
                temp-=1
                current_gif_index.value = temp
                current_gif.value = list_gif.value?.get(temp)
            }
        }
    }

    fun setCategory(category: String){
        this.category = category
    }

}