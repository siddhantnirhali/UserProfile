package com.example.userprofile.userprofile.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userprofile.userprofile.domain.UserProfileRepository
import com.example.userprofile.userprofile.model.MenuItem
import com.example.userprofile.userprofile.model.UserProfileResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileScreenViewModel(private val userProfileRepositoryImpl: UserProfileRepository) : ViewModel(){

    private val _userProfileState = MutableStateFlow<UserProfileUiState>(UserProfileUiState.Loading)
    val userProfileState: StateFlow<UserProfileUiState> = _userProfileState

    private val _menuGroups = MutableStateFlow<Map<String, List<MenuItem>>>(emptyMap())
    val menuGroups: StateFlow<Map<String, List<MenuItem>>> = _menuGroups


    init {
        fetchProfile()
    }

    fun fetchProfile() {
        Log.d("Response", "fetchProfile Pressed")

        viewModelScope.launch {
            _userProfileState.value = UserProfileUiState.Loading
            val response = userProfileRepositoryImpl.getUserProfileData()

            _userProfileState.value = if (response.isSuccess) {
                Log.d("Response", response.getOrThrow().result.title)
                _menuGroups.value = groupMenusBySection(response.getOrThrow().result.menus)
                UserProfileUiState.Success(response.getOrThrow())


            } else {

                if (response.isFailure) {
                    Log.d("Response", response.exceptionOrNull()?.message ?: "Unknown error")
                }
                UserProfileUiState.Error(response.exceptionOrNull()?.message ?: "Unknown error")

            }
        }
    }
    fun groupMenusBySection(menus: List<MenuItem>): Map<String, List<MenuItem>> {
        val result = LinkedHashMap<String, MutableList<MenuItem>>()  // maintain order
        var currentSection: String? = null

        val filteredMenus = menus.drop(4).dropLast(2)
        for (item in filteredMenus) {

            if (item.type == 0) {
                // New section
                currentSection = item.label
                result[currentSection] = mutableListOf()
            } else if (item.type == 1 && currentSection != null) {
                // Add to current section
                result[currentSection]?.add(item)
            }
        }

        return result
    }

}

sealed class UserProfileUiState {
    object Loading : UserProfileUiState()
    data class Success(val userProfile: UserProfileResponse) : UserProfileUiState()
    data class Error(val message: String) : UserProfileUiState()
}