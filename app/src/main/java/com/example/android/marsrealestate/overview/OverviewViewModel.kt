/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    enum class MarsApiStatus { LOADING, ERROR, DONE }

    // The internal MutableLiveData String that stores the most recent response
    private val _status = MutableLiveData<MarsApiStatus>()   // _underscore indicates an instance variable
     // The external immutable LiveData for the response String
    val status: LiveData<MarsApiStatus>  // public response field
    get() = _status           // accessible by calling get() - a oneliner function


    // Property details of one property
    // DL Note: is there an error in the tutorial - _properties ?????

//    private val _property = MutableLiveData<MarsProperty>()

//    val property: LiveData<MarsProperty>
//        get() = _property



    // The internal MutableLiveData that stores the status of the most recent request
    //private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the request status
    //val status: LiveData<MarsApiStatus>
     //   get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _properties = MutableLiveData<List<MarsProperty>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<MarsProperty>>
        get() = _properties





    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     * COROUTINE Version
     */
    private fun getMarsRealEstateProperties() {
        //  _response.value = "Set the Mars API Response here!"
        // set LiveData value; data will be dispatched to any observers

        // any changes to the _response  (i.e. the LiveData ) will be dispatched
        // to any observers of this data. Inthis case the binding object in the
        // OverviewFragmnet is an observer, so it will refresh its content and
        // display new content in the overview fragmnat layout.

        viewModelScope.launch { // launch coroutine within this coroutine scope
        //    try {
                // invokes API request and returns result as list of MarsProperty objects
                // get the size of the list and embed it in a String
//                val listResult = MarsApi.retrofitService.getProperties()
//                _response.value =
//                        "Success: ${listResult.size} Mars properties retrieved"


       //         val listResult = MarsApi.retrofitService.getProperties()
//                _response.value = "Success: ${listResult.size} Mars properties retrieved"
//            } catch (e: Exception) {
//                _response.value = "Failure: ${e.message}"
//        }

            _status.value = MarsApiStatus.LOADING
            try {
                _properties.value = MarsApi.retrofitService.getProperties()
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList() // empty list
            }
    }
    }
}


//        MarsApi.retrofitService.getProperties().enqueue(  // background thread
//                object: Callback<List<MarsProperty>> {
//
//                    override fun onResponse(call: Call<List<MarsProperty>>,
//                                            response: Response<List<MarsProperty>>) {
//                        _response.value =
//                                "Success: ${response.body()?.size} Mars properties retrieved"
//                    }
//
//                    override fun onFailure(call:  Call<List<MarsProperty>>,
//                                           t: Throwable) {
//                        _response.value = "Failure: " + t.message
//                    }
//                })
//    }
//}
