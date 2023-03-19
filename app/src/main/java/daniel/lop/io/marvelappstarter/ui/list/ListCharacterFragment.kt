package daniel.lop.io.marvelappstarter.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import daniel.lop.io.marvelappstarter.R
import daniel.lop.io.marvelappstarter.databinding.FragmentListCharacterBinding
import daniel.lop.io.marvelappstarter.ui.adapters.CharacterAdapter
import daniel.lop.io.marvelappstarter.ui.base.BaseFragment
import daniel.lop.io.marvelappstarter.ui.state.ResourceState
import daniel.lop.io.marvelappstarter.util.hide
import daniel.lop.io.marvelappstarter.util.show
import daniel.lop.io.marvelappstarter.util.toast
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ListCharacterFragment :BaseFragment<FragmentListCharacterBinding,ListCharacterViewModel>(){

    override val viewModel: ListCharacterViewModel by viewModels()
    private val characterAdapter by lazy { CharacterAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        clickAdapter()
        collectObserve()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListCharacterBinding {
        return FragmentListCharacterBinding.inflate(inflater,container,false)

    }

    private fun collectObserve() = lifecycleScope.launch {
        viewModel.list.collect { resources ->
            when(resources){
                is ResourceState.Success -> {
                    resources.data?.let { values->
                        binding.progressCircular.hide()
                        characterAdapter.characters = values.data.results.toList()
                    }
                }
                is ResourceState.Error ->{
                    binding.progressCircular.hide()
                    resources.message?.let{ message->
                        toast(getString(R.string.an_error_occurred)+message)
                        Timber.tag("ListCharacterFragment").e("Erro -> $message")
                    }
                }

                is ResourceState.Loading ->{
                    binding.progressCircular.show()
                }
            }

        }
    }

    private fun clickAdapter() {
        characterAdapter.setOnclickListener { characterModel->
            val action = ListCharacterFragmentDirections
                .actionListCharacterFragmentToDetailsCharacterFragment(characterModel)
            findNavController().navigate(action)
        }
    }

    private fun setupRecycleView() = with(binding) {
        rvCharacters.apply {
            adapter = characterAdapter
            layoutManager= LinearLayoutManager(requireContext())
        }
    }
}