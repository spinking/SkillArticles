package ru.skillbranch.skillarticles.ui.profile

import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.skillbranch.skillarticles.ui.base.BaseFragment
import ru.skillbranch.skillarticles.ui.base.Binding
import ru.skillbranch.skillarticles.ui.delegates.RenderProp
import ru.skillbranch.skillarticles.viewmodels.base.IViewModelState
import ru.skillbranch.skillarticles.viewmodels.profile.PendingAction
import ru.skillbranch.skillarticles.viewmodels.profile.ProfileState
import ru.skillbranch.skillarticles.viewmodels.profile.ProfileViewModel

class ProfileFragment : BaseFragment<ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()
    override val layout: Int = ru.skillbranch.skillarticles.R.layout.fragment_profile
    override val binding: ProfileBinding by lazy { ProfileBinding() }

    private val permissionsResultCallback = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {result ->
        val permissionsResult = result.mapValues { (permission, isGranted ) ->
            if (isGranted) true to true
            else false to ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                permission
            )
        }
        viewModel.handlePermission(permissionsResult)
    }

    private val settingsResultCallback = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        //show dialog
    }

    private val galleryResultCallback = registerForActivityResult(ActivityResultContracts.GetContent()) {result ->
        if (result != null) {
            val inputStream = requireContext().contentResolver.openInputStream(result)
            viewModel.handleUploadPhoto(inputStream)
        }

    }

    override fun setupViews() {
        iv_avatar.setOnClickListener {
            viewModel.handleTestAction()
        }

        viewModel.observerPermissions(viewLifecycleOwner) {
            permissionsResultCallback.launch(it.toTypedArray())
        }

        viewModel.observeActivityResults(viewLifecycleOwner) {
            when(it) {
                is PendingAction.GalleryAction -> galleryResultCallback.launch(it.payload)
                is PendingAction.SettingsAction -> settingsResultCallback.launch(it.payload)
            }
        }
    }

    private fun updateAvatar(avatarUrl:String){
        //TODO update avatar with Glide this
    }

    inner class ProfileBinding: Binding() {
        var avatar by RenderProp(""){
            updateAvatar(it)
        }

        var name by RenderProp(""){
            tv_name.text = it
        }

        var about by RenderProp(""){
            tv_about.text = it
        }

        var rating by RenderProp(0){
            tv_rating.text = "Rating: $it"
        }

        var respect by RenderProp(0){
            tv_respect.text = "Respect: $it"
        }

        override fun bind(data: IViewModelState) {
            data as ProfileState
            if(data.avatar!=null) avatar = data.avatar
            if(data.name!=null) name = data.name
            if(data.about!=null) about = data.about
            rating = data.rating
            respect = data.respect
        }
    }
}
