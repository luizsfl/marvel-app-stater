package daniel.lop.io.marvelappstarter.data.model.comic

import daniel.lop.io.marvelappstarter.data.model.ThumbmailModel

data class ComicModel(
    val id:Int,
    val title:String,
    val description:String,
    val thumbmail: ThumbmailModel
):java.io.Serializable