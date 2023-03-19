package daniel.lop.io.marvelappstarter.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import daniel.lop.io.marvelappstarter.databinding.FragmentFavoriteCharacterBinding
import daniel.lop.io.marvelappstarter.ui.base.BaseFragment
@AndroidEntryPoint
class FavoriteCharacterFragment :BaseFragment<FragmentFavoriteCharacterBinding,FavoriteCharacterModel>(){
    override val viewModel: FavoriteCharacterModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteCharacterBinding {

        return FragmentFavoriteCharacterBinding.inflate(inflater,container,false)

    }
}