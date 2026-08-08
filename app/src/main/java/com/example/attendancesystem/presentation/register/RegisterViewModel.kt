package com.example.attendancesystem.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancesystem.common.Gender
import com.example.attendancesystem.data.datastore.TokenManager
import com.example.attendancesystem.data.model.LocalProfile
import com.example.attendancesystem.data.model.request.RegisterRequest
import com.example.attendancesystem.data.repository.AuthRepository
import com.example.attendancesystem.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uistate = MutableStateFlow(RegisterUiState())
    val uiState = _uistate.asStateFlow()

    fun onFirstNameChange(name: String) {
        _uistate.update {
            it.copy(
                firstName = name,
                error = null
            )
        }

    }

    fun onLastNameChange(name: String) {
        _uistate.update {
            it.copy(
                lastName = name,
                error = null
            )
        }

    }

    fun onEmailChange(email: String) {
        _uistate.update {
            it.copy(
                email = email,
                error = null
            )
        }

    }

    fun onPhoneChange(phone: String) {
        _uistate.update {
            it.copy(
                phone = phone,
                error = null
            )
        }
    }

    fun onDobChange(dob: String) {

        _uistate.update {
            it.copy(
                dob = dob,
                error = null
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uistate.update {
            it.copy(
                password = password,
                error = null
            )
        }

    }

    fun onConfirmPasswordChange(password: String) {

        _uistate.update {
            it.copy(
                confirmPassword = password,
                error = null
            )
        }
    }

    fun onGenderChange(gender: Gender) {
        _uistate.update {
            it.copy(
                gender = gender,
                error =null
            )
        }
    }

    fun register() {

        if (_uistate.value.firstName.isBlank()) {
            _uistate.update {
                it.copy(
                    error = " First Name is required"
                )
            }
            return
        }

        if (_uistate.value.lastName.isBlank()) {
            _uistate.update {
                it.copy(
                    error = " Last Name is Required "
                )
            }
            return
        }

        if (_uistate.value.email.isBlank()) {
            _uistate.update {
                it.copy(
                    error = " Email is Required "
                )
            }
            return
        }

        if (_uistate.value.dob.isBlank()) {
            _uistate.update {
                it.copy(
                    error = "Date of Birth is required"
                )
            }
            return
        }
        if (_uistate.value.phone.isBlank()) {
            _uistate.update {
                it.copy(error = "Phone Number is required")
            }
            return
        }

        if (_uistate.value.password.isBlank()) {
            _uistate.update {
                it.copy(
                    error = " Password is Required "
                )
            }
            return
        }

        if (_uistate.value.confirmPassword.isBlank()) {
            _uistate.update {
                it.copy(
                    error = " Confirm Password is required "
                )
            }
            return
        }

        if (_uistate.value.confirmPassword != _uistate.value.password) {
            _uistate.update {
                it.copy(
                    error = " Password do not match "
                )
            }
            return
        }

        viewModelScope.launch {

            _uistate.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            val result = repository.register(

                RegisterRequest(
                    firstName = _uistate.value.firstName,
                    lastName = _uistate.value.lastName,
                    email = _uistate.value.email,
                    password = _uistate.value.password,
                    phone =  _uistate.value.phone,
                    gender = _uistate.value.gender,
                    dob = _uistate.value.dob
                )
            )


            when(result){

                is NetworkResult.Success ->{

                    tokenManager.saveProfile(
                        LocalProfile(
                            firstName = _uistate.value.firstName,
                            lastName = _uistate.value.lastName,
                            email = _uistate.value.email,
                            phone = _uistate.value.phone,
                            dob = _uistate.value.dob,
                            gender = _uistate.value.gender
                        )
                    )

                    _uistate.update {
                        it.copy(
                            isLoading = false,
                            isRegisterSuccess = true,
                            error = null
                        )
                    }
                    Log.d("Register", "Success:")
                }

                is NetworkResult.Error -> {

                    _uistate.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }


            }


        }


    }

}