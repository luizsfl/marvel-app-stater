package daniel.lop.io.marvelappstarter.ui.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import daniel.lop.io.marvelappstarter.R
import daniel.lop.io.marvelappstarter.data.model.character.CharacterModel
import daniel.lop.io.marvelappstarter.databinding.FragmentDetailsCharacterBinding
import daniel.lop.io.marvelappstarter.ui.adapters.ComicAdapter
import daniel.lop.io.marvelappstarter.ui.base.BaseFragment
import daniel.lop.io.marvelappstarter.ui.state.ResourceState
import daniel.lop.io.marvelappstarter.util.hide
import daniel.lop.io.marvelappstarter.util.limitDescription
import daniel.lop.io.marvelappstarter.util.show
import daniel.lop.io.marvelappstarter.util.toast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailsCharacterFragment:
    BaseFragment<FragmentDetailsCharacterBinding,DetailsCharacterViewModel>() {

    private val args :DetailsCharacterFragmentArgs by navArgs()

    private val comicAdapter by lazy { ComicAdapter() }
    private lateinit var characterModel : CharacterModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        characterModel = args.character
        viewModel.fetch(characterModel.id)
        setupRecycleView()
        onLoadedCharacter(characterModel)
        collectObserver()
    }

    private fun collectObserver()  = lifecycleScope.launch{
        viewModel.detail.collect{ result ->
            when(result){
                is ResourceState.Success -> {
                    binding.progressBarDetail.hide()
                    result.data?.let { values ->
                        if(values.data.results.count() > 0){
                            comicAdapter.comics = values.data.results.toList()
                        }else{
                            toast(getString(R.string.empty_list_comics))
                        }
                    }
                }
                is ResourceState.Error -> {
                    binding.progressBarDetail.hide()
                    result.message?.let { message ->
                        Timber.tag("DetailsCharacterFragment").e(message)
                        toast(getString(R.string.an_error_occurred))
                    }
                }
                is ResourceState.Loading -> {
                    binding.progressBarDetail.show()

                }
               else -> {}
            }
        }
    }

    private fun onLoadedCharacter(characterModel: CharacterModel) = with(binding) {
        tvNameCharacterDetails.text = characterModel.name
        if (characterModel.description.isEmpty()){
            tvDescriptionCharacterDetails.text = requireContext().getString(R.string.text_description_empty).limitDescription(100)
        }else{
            tvDescriptionCharacterDetails.text = characterModel.description
        }

        Glide.with(requireContext())
            .load(characterModel.thumbnail.path + "." + characterModel.thumbnail.extension)
            .into(imgCharacterDetails)
    }

    private fun setupRecycleView() = with(binding){
        rvComics.apply {
            adapter = comicAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.favorite -> {
               // viewModel.inset(characterModel)
                toast(getString(R.string.saved_successfully))
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override val viewModel: DetailsCharacterViewModel by viewModels()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsCharacterBinding =
        FragmentDetailsCharacterBinding.inflate(inflater,container,false)





}