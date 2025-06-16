package nft.nftcreator.pixel.pixelart.creator.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import nft.nftcreator.pixel.pixelart.creator.models.PixelArt

@Dao
interface PixelArtCreationsDao {
    @Insert
    suspend fun insertPixelArt(pixelArt: PixelArt)

    @Update
    suspend fun updatePixelArt(pixelArt: PixelArt)

    @Query("SELECT * FROM PixelArt WHERE item_title LIKE '%' || :q || '%' ORDER BY objId DESC")
    fun getAllPixelArtCreations(q: String): LiveData<List<PixelArt>>

    @Query("SELECT * FROM PixelArt WHERE item_starred = 1")
    fun getAllPixelArtCreationsStarred(): LiveData<List<PixelArt>>

    @Query("DELETE FROM PixelArt WHERE objId=:pixelArtId")
    fun deletePixelArtCreation(pixelArtId: Int)

    @Query("UPDATE PixelArt SET item_cover_bitmap=:coverBitmap WHERE objId=:id_t")
    fun updatePixelArtCreationCoverBitmap(coverBitmap: String, id_t: Int): Int

    @Query("UPDATE PixelArt SET item_title=:title WHERE objId=:id_t")
    fun updatePixelArtCreationTitle(title: String, id_t: Int): Int

    @Query("UPDATE PixelArt SET item_bitmap=:bitmap WHERE objId=:id_t")
    fun updatePixelArtCreationBitmap(bitmap: String, id_t: Int): Int

    @Query("UPDATE PixelArt SET item_starred=:starred WHERE objId=:id_t")
    fun updatePixelArtCreationStarred(starred: Boolean, id_t: Int): Int

    @Query("UPDATE PixelArt SET item_rotation=:rotation WHERE objId=:id_t")
    fun updatePixelArtCreationRotation(rotation: Float, id_t: Int): Int

    @Query("SELECT COUNT(objId) FROM PixelArt")
    fun countPixelArt(): Int
}