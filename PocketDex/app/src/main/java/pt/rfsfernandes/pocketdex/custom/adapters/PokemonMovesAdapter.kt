package pt.rfsfernandes.pocketdex.custom.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.custom.Constants
import pt.rfsfernandes.pocketdex.custom.adapters.PokemonMovesAdapter.MovesViewHolder
import pt.rfsfernandes.pocketdex.custom.callbacks.MovesItemClick
import pt.rfsfernandes.pocketdex.custom.utils.UtilsClass
import pt.rfsfernandes.pocketdex.model.moves.Moves
import java.util.*

class PokemonMovesAdapter(
    private val context: Context,
    private val mMovesItemClick: MovesItemClick
) : RecyclerView.Adapter<MovesViewHolder>() {
    private var mPokemonMoves: List<Moves?> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.moves_row, parent, false)
        return MovesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovesViewHolder, position: Int) {
        populateItemRows(holder, position)
    }

    /**
     * Populates each row with the content of the current item
     *
     * @param holder   Holder used to populate
     * @param position Position of the item
     */
    private fun populateItemRows(holder: MovesViewHolder, position: Int) {
        val item = mPokemonMoves[position]
        holder.mItem = item
        holder.textViewMoveTitle.text = item?.name?.replace("-", " ")
        val color = item?.type?.name?.let { UtilsClass.returnColorId(context, it) }
        val drawable = ResourcesCompat.getDrawable(
            context.resources,
            R.drawable.type_background_round,
            null
        )
        color?.let { drawable?.setTint(it) }
        holder.textViewMovePower.background = drawable
        var chosenFlavour = ""
        val country =
            Locale.getDefault().language.split("-").toTypedArray()[0].lowercase(Locale.getDefault())
        item?.flavourEntriesList?.let {
            for (flavour in it) {
                if (flavour.language.name.contains(country)) {
                    chosenFlavour = flavour.getFlavourText()
                    break
                }
            }
        }

        if (chosenFlavour.isEmpty()) {
            item?.flavourEntriesList?.let {
                for (flavour in it) {
                    if (flavour.language.name.contains("en")) {
                        chosenFlavour = flavour.getFlavourText()
                        break
                    }
                }
            }

        }
        holder.textViewMoveDescription.text = chosenFlavour.replace("\n", " ")
        holder.textViewMoveEffect.text = item?.effectEntry?.effect?.replace("  ", " ")
            ?.replace("\$effect_chance%", item.effectChance.toString())
        holder.textViewMoveShortEffect.text = item?.effectEntry?.shortEffect?.replace(
            "  ",
            " "
        )?.replace("\$effect_chance%", item.effectChance.toString())
        holder.textViewMovePower.text = item?.power.toString()
        holder.textViewMovePower.setOnClickListener { e: View? ->
            mMovesItemClick.clickedMovesItem(
                "",
                item?.type?.urlId,
                Constants.MOVES_ITEM.TYPE
            )
        }
        holder.textViewMoveDescription.setOnClickListener { e: View? ->
            mMovesItemClick.clickedMovesItem(
                context.resources.getString(R.string.description),
                holder.textViewMoveDescription.text.toString(),
                Constants.MOVES_ITEM.SIMPLE
            )
        }
        holder.textViewMoveEffect.setOnClickListener { e: View? ->
            mMovesItemClick.clickedMovesItem(
                context.resources.getString(R.string.effect),
                holder.textViewMoveEffect.text.toString(),
                Constants.MOVES_ITEM.SIMPLE
            )
        }
        holder.textViewMoveShortEffect.setOnClickListener { e: View? ->
            mMovesItemClick.clickedMovesItem(
                context.resources.getString(R.string.short_effect),
                holder.textViewMoveShortEffect.text.toString(),
                Constants.MOVES_ITEM.SIMPLE
            )
        }
    }

    override fun getItemCount(): Int {
        return mPokemonMoves.size
    }

    /**
     * Assigns a value to the global List of Moves and notifies the adapter of that change
     *
     * @param moves New list
     */
    fun refreshList(moves: List<Moves?>) {
        mPokemonMoves = moves
        notifyDataSetChanged()
    }

    inner class MovesViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val textViewMoveTitle: TextView = mView.findViewById(R.id.textViewMoveTitle)
        val textViewMoveEffect: TextView = mView.findViewById(R.id.textViewMoveEffect)
        val textViewMoveShortEffect: TextView = mView.findViewById(R.id.textViewMoveShortEffect)
        val textViewMovePower: TextView = mView.findViewById(R.id.textViewMovePower)
        val textViewMoveDescription: TextView = mView.findViewById(R.id.textViewMoveDescription)
        var mItem: Moves? = null
    }
}