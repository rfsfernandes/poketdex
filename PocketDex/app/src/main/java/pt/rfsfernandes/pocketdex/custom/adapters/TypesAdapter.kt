package pt.rfsfernandes.pocketdex.custom.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.custom.utils.UtilsClass
import pt.rfsfernandes.pocketdex.model.SimpleModelData

/**
 * Class TypesAdapter created at 1/19/21 00:42 for the project PoketDex
 * By: rodrigofernandes
 */
class TypesAdapter(private val context: Context) : BaseAdapter() {
    private var mModelData: List<SimpleModelData>
    override fun getCount(): Int {
        return mModelData.size
    }

    override fun getItem(position: Int): SimpleModelData {
        return mModelData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, cv: View?, parent: ViewGroup): View {
        var convertView = cv
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pokemon_type_row, parent, false)
        }
        val typeData = getItem(position)
        val textViewType = convertView?.findViewById<TextView>(R.id.textViewType)
        textViewType?.text = typeData.name
        val linearLayoutType = convertView?.findViewById<LinearLayout>(R.id.linearLayoutType)
        val color = UtilsClass.returnColorId(context, typeData.name)
        val drawable = ResourcesCompat.getDrawable(context.resources,
                R.drawable.type_background,
                null)
        drawable?.setTint(color)
        linearLayoutType?.background = drawable
        return convertView!!
    }

    /**
     * Assigns a value to the global List of ModelData and notifies the adapter of that change
     *
     * @param pokemonTypes New list
     */
    fun refreshList(pokemonTypes: List<SimpleModelData>) {
        mModelData = pokemonTypes
        notifyDataSetChanged()
    }

    init {
        mModelData = ArrayList()
    }
}