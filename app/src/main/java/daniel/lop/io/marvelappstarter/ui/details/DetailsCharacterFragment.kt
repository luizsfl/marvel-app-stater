package daniel.lop.io.marvelappstarter.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import daniel.lop.io.marvelappstarter.databinding.FragmentDetailsCharacterBinding
import daniel.lop.io.marvelappstarter.ui.base.BaseFragment
@AndroidEntryPoint
class DetailsCharacterFragment:BaseFragment<FragmentDetailsCharacterBinding,DetailsCharacterModel>() {
    override val viewModel: DetailsCharacterModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsCharacterBinding {
        return FragmentDetailsCharacterBinding.inflate(inflater,container,false)
    }
}