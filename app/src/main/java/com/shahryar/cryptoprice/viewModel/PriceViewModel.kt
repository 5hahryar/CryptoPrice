package com.shahryar.cryptoprice.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahryar.cryptoprice.application.KEY_PREFS_API_KEY
import com.shahryar.cryptoprice.application.Utils
import com.shahryar.cryptoprice.model.Data
import com.shahryar.cryptoprice.repository.PriceApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class PriceViewModel(context: Context): ViewModel() {

    private val apiKey: String? = Utils().readStringPreference(context, KEY_PREFS_API_KEY)
    private val _response: MutableLiveData<Data> = MutableLiveData()
    val response: MutableLiveData<Data>
        get() = _response

    init {
        if (apiKey != null) getData(null)
        else Log.d("TAG", "Api key not found")
    }

    fun getData(sortKey: String?) {
        viewModelScope.launch {
            PriceApi.apiService.getProperties(apiKey!!, sortKey).enqueue(
                    object : retrofit2.Callback<Data> {
                        override fun onResponse(call: Call<Data>, response: Response<Data>) {
                            if (response.isSuccessful) _response.value = response.body()
                            Log.d("TAG", response.isSuccessful.toString())
                        }

                        override fun onFailure(call: Call<Data>, t: Throwable) {
                            Log.d("TAG", t.toString())
                        }
                    }
            )
        }
    }

    fun sort(key: String) {
        getData(key)
    }
}