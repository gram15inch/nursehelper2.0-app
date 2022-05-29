package com.nuhlp.nursehelper.ui.login

import android.app.Application
import androidx.lifecycle.*
import com.nuhlp.nursehelper.data.LoginDataStoreImpl
import com.nuhlp.nursehelper.data.room.getUserDatabase
import com.nuhlp.nursehelper.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application){
    private val loginRepository = LoginRepository(LoginDataStoreImpl(application),getUserDatabase(application))
    val isLogin : LiveData<Boolean> = loginRepository.isLogin.asLiveData()
    val isAgreeTerm : LiveData<Boolean> = loginRepository.isAgreeTerms.asLiveData()

    fun loginSuccess(){
        viewModelScope.launch{
            loginRepository.setIsLoginToDataStore(true)
        }
    }

    fun agreeTerms(){
        viewModelScope.launch {
            loginRepository.setTermsToDataStore(true)
        }
    }
    fun checkId(userId :String){
        viewModelScope.launch{
             loginRepository.getAvailableId(userId)
        } // id 가져오는거부터 다시 
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}