package ru.skillbranch.skillarticles.ui.auth

import android.text.Spannable
import androidx.annotation.VisibleForTesting
import androidx.core.text.set
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.et_login
import kotlinx.android.synthetic.main.fragment_registration.et_password
import kotlinx.android.synthetic.main.fragment_registration.tv_privacy
import ru.skillbranch.skillarticles.R
import ru.skillbranch.skillarticles.extensions.attrValue
import ru.skillbranch.skillarticles.ui.RootActivity
import ru.skillbranch.skillarticles.ui.base.BaseFragment
import ru.skillbranch.skillarticles.ui.custom.spans.UnderlineSpan
import ru.skillbranch.skillarticles.viewmodels.auth.AuthViewModel
import ru.skillbranch.skillarticles.viewmodels.base.NavigationCommand

class RegistrationFragment() : BaseFragment<AuthViewModel>() {
    var _mockFactory: ((SavedStateRegistryOwner)->ViewModelProvider.Factory)? = null

    override val layout: Int = R.layout.fragment_registration
    override val viewModel: AuthViewModel by viewModels {
        _mockFactory?.invoke(this) ?: defaultViewModelProviderFactory
    }
    private val args: RegistrationFragmentArgs by navArgs()

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    constructor(
        mockRoot: RootActivity,
        mockFactory: ((SavedStateRegistryOwner)-> ViewModelProvider.Factory)? = null
    ) : this() {
        _mockRoot = mockRoot
        _mockFactory = mockFactory
    }

    override fun setupViews() {
        tv_privacy.setOnClickListener {
            viewModel.navigate(NavigationCommand.To(R.id.page_privacy_policy))
        }

        btn_registration.setOnClickListener {
            viewModel.handleRegister(
                et_name.text.toString(),
                et_login.text.toString(),
                et_password.text.toString(),
                if (args.privateDestination == -1) null else args.privateDestination
            )
        }
        val color = root.attrValue(R.attr.colorPrimary)
        (tv_privacy.text as Spannable).let { it[0..it.length] = UnderlineSpan(color) }
    }
}