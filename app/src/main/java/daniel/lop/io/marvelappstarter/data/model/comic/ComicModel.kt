package daniel.lop.io.marvelappstarter.data.model.comic

import com.google.gson.annotations.SerializedName
import daniel.lop.io.marvelappstarter.data.model.ThumbnailModel

data class ComicModel(
    @SerializedName("id")
    val id:Int,
    @SerializedName("title")
    val title:String,
    @SerializedName("description")
    val description:String,
    @SerializedName("thumbnail")
    val thumbmail: ThumbnailModel
):java.io.Serializable
