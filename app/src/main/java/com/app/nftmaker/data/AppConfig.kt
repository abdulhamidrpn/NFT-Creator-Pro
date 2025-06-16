package nft.nftcreator.pixel.pixelart.data

object AppConfig {

    // flag for enable/disable all ads
    private const val ADS_ENABLE : Boolean= true

    // flag for display ads (change true & false ant the end only )
    const val ADS_MAIN_BANNER : Boolean = ADS_ENABLE && true
    const val ADS_MAIN_INTERS : Boolean = ADS_ENABLE && true
    const val ADS_INTERS_INTERVAL : Int = 2
    const val ADS_CREATOR_BANNER : Boolean = ADS_ENABLE && true
}