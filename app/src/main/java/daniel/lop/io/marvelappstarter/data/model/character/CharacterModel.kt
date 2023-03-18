package daniel.lop.io.marvelappstarter.data.model.character

import daniel.lop.io.marvelappstarter.data.model.ThumbmailModel

data class CharacterModel(
    val id:Int,
    val name:String,
    val description:String,
    val thumbmail: ThumbmailModel
):java.io.Serializable
