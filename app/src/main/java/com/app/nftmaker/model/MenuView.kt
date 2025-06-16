package nft.nftcreator.pixel.pixelart.model

import androidx.fragment.app.Fragment

class MenuView {

    var title = 0
    var parent = 0
    var image:Int = 0
    var frame:Int = 0
    var fragment: Fragment? = null

    constructor(title: Int, parent: Int, image: Int, frame: Int, fragment: Fragment?) {
        this.title = title
        this.parent = parent
        this.image = image
        this.frame = frame
        this.fragment = fragment
    }
}