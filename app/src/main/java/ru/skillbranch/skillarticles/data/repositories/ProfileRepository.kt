package ru.skillbranch.skillarticles.data.repositories

import androidx.lifecycle.LiveData
import okhttp3.MultipartBody
import ru.skillbranch.skillarticles.data.local.PrefManager
import ru.skillbranch.skillarticles.data.models.User
import ru.skillbranch.skillarticles.data.remote.NetworkManager
import ru.skillbranch.skillarticles.data.remote.req.EditProfileReq

object ProfileRepository : IProfileRepository {
    private val prefs = PrefManager
    private val network = NetworkManager.api

    override suspend fun removeAvatar() {
        network.remove(prefs.accessToken)
        prefs.replaceAvatarUrl("")
    }

    override fun getProfile(): LiveData<User?> = prefs.profileLive

    override suspend fun uploadAvatar(body: MultipartBody.Part) {
        val (url) = network.upload(body, prefs.accessToken)
        prefs.replaceAvatarUrl(url)
    }

    override suspend fun editProfile(name: String, about: String) {
        val  user = network.editProfile(EditProfileReq(name, about), prefs.accessToken)
        prefs.profile = user
    }
}

interface IProfileRepository {
    fun getProfile(): LiveData<User?>
    suspend fun uploadAvatar(body: MultipartBody.Part)
    suspend fun removeAvatar()
    suspend fun editProfile(name: String, about: String)
}