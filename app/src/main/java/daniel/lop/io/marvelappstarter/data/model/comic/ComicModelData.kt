package daniel.lop.io.marvelappstarter.data.model.comic

import com.google.gson.annotations.SerializedName

data class ComicModelData(
        @SerializedName("results")
        val results:List<ComicModel>
):java.io.Serializable