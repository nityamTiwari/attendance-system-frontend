package com.example.attendancesystem.presentation.splash

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancesystem.data.datastore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.compose
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class  SplashViewModel @Inject constructor(

    private val tokenManager: TokenManager

) : ViewModel(){


    private val _navigationState = MutableStateFlow<SplashNavigationState>(
        SplashNavigationState.Loading
    )

    val navigationState = _navigationState.asStateFlow()


    init{
        checkLogin()
    }

    private fun checkLogin(){

        viewModelScope.launch {

            tokenManager.token.collect{ token ->

                if(token.isNullOrBlank()){

                     _navigationState.value = SplashNavigationState.Login
                }else{

                    _navigationState.value = SplashNavigationState.Home
                }
            }


            }

    }









}