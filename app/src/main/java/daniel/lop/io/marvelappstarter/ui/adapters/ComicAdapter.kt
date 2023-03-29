package daniel.lop.io.marvelappstarter.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import daniel.lop.io.marvelappstarter.R
import daniel.lop.io.marvelappstarter.data.model.comic.ComicModel
import daniel.lop.io.marvelappstarter.databinding.ItemComicBinding
import daniel.lop.io.marvelappstarter.util.limitDescription
import daniel.lop.io.marvelappstarter.util.loadImage

class ComicAdapter:RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    inner class ComicViewHolder(val binding: ItemComicBinding):
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

    var comics : List<ComicModel>
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
            if(comic.title.isNullOrEmpty()){
                tvNameComic.text  = holder.itemView.context.getString(R.string.tx_item_name)
            }else{
                tvNameComic.text = comic.title
            }
            if(comic.description.isNullOrEmpty()){
                tvDescriptionComic.text  = holder.itemView.context.getString(R.string.empty_list_comics)
            }else{
                tvDescriptionComic.text  = comic.description.limitDescription(100)
            }

            loadImage(imgComic,comic.thumbmail.path,comic.thumbmail.extension)

        }
    }

    override fun getItemCount():Int =  comics.size

}