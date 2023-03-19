package daniel.lop.io.marvelappstarter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import daniel.lop.io.marvelappstarter.data.model.character.CharacterModel
import daniel.lop.io.marvelappstarter.databinding.ItemComicBinding
import daniel.lop.io.marvelappstarter.util.limitDescription

class ComicAdapter:RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    inner class ComicViewHolder(val binding: ItemComicBinding):
            RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object :DiffUtil.ItemCallback<CharacterModel>(){
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

    private val differ = AsyncListDiffer(this,differCallBack)

    var comics : List<CharacterModel>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        return ComicViewHolder(
            ItemComicBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val comic = comics[position]
        holder.binding.apply {
            tvNameComic.text = comic.name
            tvDescriptionComic.text  = comic.description.limitDescription(100)

            Glide.with(holder.itemView.context)
                .load(comic.thumbnail.path +"."+comic.thumbnail.extension)
                .into(imgComic)
        }
    }

    override fun getItemCount():Int =  comics.size

}