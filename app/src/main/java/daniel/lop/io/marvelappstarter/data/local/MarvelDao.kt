package daniel.lop.io.marvelappstarter.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import daniel.lop.io.marvelappstarter.data.model.character.CharacterModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterModel): Long

    @Query("select * from CARACTERMODEL order by id")
    fun getAll(): Flow<List<CharacterModel>>

    @Delete
    suspend fun delet(character: CharacterModel)
}