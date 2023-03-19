package daniel.lop.io.marvelappstarter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import daniel.lop.io.marvelappstarter.R
import daniel.lop.io.marvelappstarter.data.model.character.ComicModel
import daniel.lop.io.marvelappstarter.databinding.ItemCharacterBinding
import daniel.lop.io.marvelappstarter.util.limitDescription

class CharacterAdapter:RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding: ItemCharacterBinding):
            RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object :DiffUtil.ItemCallback<ComicModel>(){
        override fun areItemsTheSame(oldItem: ComicModel, newItem: ComicModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: ComicModel, newItem: ComicModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private val differ = AsyncListDiffer(this,differCallBack)

    var characters : List<ComicModel>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.binding.apply {
            tvNameCharacter.text = character.name
            if(character.description.isEmpty()){
                tvDescriptionCharacter.text = holder.itemView.context.getString(R.string.text_description_empty)
            }else{
                tvDescriptionCharacter.text  = character.description.limitDescription(100)
            }
            Glide.with(holder.itemView.context)
                .load(character.thumbmail.path +"."+character.thumbmail.extension)
                .into(imgCharacter)
        }

        holder.itemView.setOnClickListener{
            onItemClickListener?.let {
                it(character)
            }
        }
    }

    override fun getItemCount():Int =  characters.size

    private var onItemClickListener:((ComicModel)->Unit) ? = null

    fun setOnclickListener(listener:(ComicModel)->Unit){
        onItemClickListener = listener
    }
}