package nft.nftcreator.pixel.pixelart.creator.database

import android.content.Context
import androidx.room.*
import androidx.room.Database
import nft.nftcreator.pixel.pixelart.creator.dao.PixelArtCreationsDao
import nft.nftcreator.pixel.pixelart.creator.models.PixelArt

@Database(entities = [PixelArt::class], version = 3)
abstract class PixelArtDatabase: RoomDatabase() {

    abstract fun pixelArtCreationsDao(): PixelArtCreationsDao

    companion object {
        private var instance: PixelArtDatabase? = null
        fun getDatabase(context: Context): PixelArtDatabase {
            if (instance == null) {
                synchronized(PixelArtDatabase::class) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(context.applicationContext, PixelArtDatabase::class.java, AppData.pixelArtDBFileName)
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return instance!!
        }
    }
}