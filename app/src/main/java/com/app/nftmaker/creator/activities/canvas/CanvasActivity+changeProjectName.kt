package nft.nftcreator.pixel.pixelart.creator.activities.canvas

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import nft.nftcreator.pixel.pixelart.databinding.DialogChangeProjectNameBinding

fun CanvasActivity.changeProjectName(context: Context) {
    var bindingView = DialogChangeProjectNameBinding.inflate(LayoutInflater.from(context))
    val dialog = Dialog(context)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    bindingView.name.setText(projectTitle)
    dialog.setCancelable(true)
    dialog.setContentView(bindingView.root)

    bindingView.btnUpdate.setOnClickListener {
        title = bindingView.name.text.toString()
        projectTitle = bindingView.name.text.toString()
        dialog.dismiss()
    }
    dialog.show()
}