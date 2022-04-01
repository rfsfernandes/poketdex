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
import pt.rfsfernandes.pocketdex.model.pokemon.types.PokemonType

/**
 * Class TypesAdapter created at 1/19/21 00:42 for the project PoketDex
 * By: rodrigofernandes
 */
class PokemonTypesAdapter(private val context: Context) : BaseAdapter() {
    private var mPokemonTypes: List<PokemonType>
    override fun getCount(): Int {
        return mPokemonTypes.size
    }

    override fun getItem(position: Int): PokemonType {
        return mPokemonTypes[position]
    }

    override fun getItemId(position: Int): Long {
        return mPokemonTypes[position].type.urlId.toInt().toLong()
    }

    override fun getView(position: Int, cv: View?, parent: ViewGroup): View {
        var convertView = cv
        if (cv == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pokemon_type_row, parent, false)
        }
        val pokemonType = getItem(position)
        val textViewType = convertView?.findViewById<TextView>(R.id.textViewType)
        textViewType?.text = pokemonType.type.name
        val linearLayoutType = convertView?.findViewById<LinearLayout>(R.id.linearLayoutType)
        val color = UtilsClass.returnColorId(context, pokemonType.type.name)
        val drawable = ResourcesCompat.getDrawable(context.resources,
                R.drawable.type_background,
                null)
        drawable?.setTint(color)
        linearLayoutType?.background = drawable
        return convertView!!
    }

    /**
     * Assigns a value to the global List of PokemonType and notifies the adapter of that change
     *
     * @param pokemonTypes New list
     */
    fun refreshList(pokemonTypes: List<PokemonType>) {
        mPokemonTypes = pokemonTypes
        notifyDataSetChanged()
    }

    init {
        mPokemonTypes = ArrayList()
    }
}