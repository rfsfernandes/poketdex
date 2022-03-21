package pt.rfsfernandes.pocketdex.custom.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import pt.rfsfernandes.pocketdex.databinding.SimpleDialogLayoutBinding
/**
 * Class CustomDialogClass created at 1/20/21 00:22 for the project PoketDex
 * By: rodrigofernandes
 */
class SimpleCustomDialog(private val c: Activity) : Dialog(c), View.OnClickListener {
    private var binding: SimpleDialogLayoutBinding? = null
    var textViewSimpleTitle: TextView? = null
        private set
    var textViewSimpleText: TextView? = null
        private set

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        binding = SimpleDialogLayoutBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        this.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view)
        val close = binding!!.buttonClose
        textViewSimpleTitle = binding!!.textViewSimpleTitle
        textViewSimpleText = binding!!.textViewSimpleText
        close.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        dismiss()
    }
}