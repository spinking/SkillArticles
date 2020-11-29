package ru.skillbranch.skillarticles.viewmodels.auth

import androidx.lifecycle.SavedStateHandle
import ru.skillbranch.skillarticles.data.repositories.RootRepository
import ru.skillbranch.skillarticles.extensions.isValidEmail
import ru.skillbranch.skillarticles.extensions.isValidName
import ru.skillbranch.skillarticles.extensions.isValidPassword
import ru.skillbranch.skillarticles.viewmodels.base.BaseViewModel
import ru.skillbranch.skillarticles.viewmodels.base.IViewModelState
import ru.skillbranch.skillarticles.viewmodels.base.NavigationCommand
import ru.skillbranch.skillarticles.viewmodels.base.Notify

class AuthViewModel(handle: SavedStateHandle) : BaseViewModel<AuthState>(handle, AuthState()), IAuthViewModel {
    private val repository = RootRepository

    init {
        subscribeOnDataSource(repository.isAuth()) { isAuth, currentState ->
            currentState.copy(isAuth = isAuth)
        }
    }

    override fun handleLogin(login: String, password: String, destination: Int?) {
        launchSafety {
            repository.login(login, password)
            navigate(NavigationCommand.FinishLogin(destination))
        }
    }

    fun handleRegister(name: String, login: String, password: String, dest: Int?) {
        if (name.isEmpty() || login.isEmpty() || password.isEmpty()) {
            notify(Notify.ErrorMessage("Name, login, password it is required fields and not must be empty"))
            return
        }

        if (name.isValidName().not()) {
            notify(Notify.ErrorMessage("The name must be at least 3 characters long and contain only letters and numbers and can also contain the characters \"-\" and \"_\""))
            return
        }

        if (login.isValidEmail().not()) {
            notify(Notify.ErrorMessage("Incorrect Email entered"))
            return
        }

        if (password.isValidPassword().not()) {
            notify(Notify.ErrorMessage("Password must be at least 8 characters long and contain only letters and numbers"))
            return
        }

        launchSafety {
            repository.register(name, login, password)
            navigate(NavigationCommand.FinishRegistration(dest))
        }
    }
}

data class AuthState(val isAuth: Boolean = false): IViewModelState