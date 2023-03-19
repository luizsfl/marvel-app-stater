package daniel.lop.io.marvelappstarter.data.model.comic

import daniel.lop.io.marvelappstarter.data.model.ThumbnailModel

data class ComicModel(
    val id:Int,
    val title:String,
    val description:String,
    val thumbmail: ThumbnailModel
):java.io.Serializable
