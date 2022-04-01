package pt.rfsfernandes.pocketdex.custom.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.custom.adapters.TypesAdapter
import pt.rfsfernandes.pocketdex.custom.utils.UtilsClass
import pt.rfsfernandes.pocketdex.databinding.TypeDialogLayoutBinding

/**
 * Class CustomDialogClass created at 1/20/21 00:22 for the project PoketDex
 * By: rodrigofernandes
 */
class TypeCustomDialog(val c: Activity) : Dialog(c), View.OnClickListener {
    private var binding: TypeDialogLayoutBinding? = null
    private var gridViewDoubleDamage: GridView? = null
    private var gridViewHalfDamage: GridView? = null
    private var gridViewNoDamage: GridView? = null
    private var includeTypeRow: View? = null
    var textViewComparisonType: TextView? = null
        private set
    var pokemonTypesAdapterDoubleDamage: TypesAdapter? = null
        private set
    var pokemonTypesAdapterHalfDamage: TypesAdapter? = null
        private set
    var pokemonTypesAdapterNoDamage: TypesAdapter? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TypeDialogLayoutBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view)
        val close = binding!!.buttonClose
        this.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        gridViewDoubleDamage = binding!!.gridViewDoubleDamage
        gridViewHalfDamage = binding!!.gridViewHalfDamage
        gridViewNoDamage = binding!!.gridViewNoDamage
        includeTypeRow = view.findViewById(R.id.includeTypeRow)
        textViewComparisonType = binding!!.textViewComparisonType
        pokemonTypesAdapterDoubleDamage = TypesAdapter(context)
        pokemonTypesAdapterHalfDamage = TypesAdapter(context)
        pokemonTypesAdapterNoDamage = TypesAdapter(context)
        gridViewDoubleDamage!!.adapter = pokemonTypesAdapterDoubleDamage
        gridViewHalfDamage!!.adapter = pokemonTypesAdapterHalfDamage
        gridViewNoDamage!!.adapter = pokemonTypesAdapterNoDamage
        close.setOnClickListener(this)
    }

    fun setType(type: String) {
        val textViewType = includeTypeRow!!.findViewById<TextView>(R.id.textViewType)
        textViewType.text = type
        val linearLayoutType = includeTypeRow!!.findViewById<LinearLayout>(R.id.linearLayoutType)
        val color = UtilsClass.returnColorId(c, type)
        val drawable = ResourcesCompat.getDrawable(
            c.resources,
            R.drawable.type_background,
            null
        )
        drawable?.setTint(color)
        linearLayoutType.background = drawable
    }

    override fun onClick(v: View) {
        dismiss()
    }
}