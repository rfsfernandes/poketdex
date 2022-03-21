package pt.rfsfernandes.pocketdex.custom.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.drawable.ProgressBarDrawable
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.custom.callbacks.ItemListClicked
import pt.rfsfernandes.pocketdex.custom.utils.UtilsClass
import pt.rfsfernandes.pocketdex.model.service_responses.PokemonResult

class PokemonResultAdapter(private val mContext: Context,
                           private val callback: ItemListClicked<PokemonResult?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var mPokemonResultList: List<PokemonResult?> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.pokemon_result_row, parent, false)
            PokemonResultViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.loading_row, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mPokemonResultList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PokemonResultViewHolder) {
            populateItemRows(holder, position)
        }
    }

    /**
     * Populates each row with the content of the current item
     *
     * @param holder   Holder used to populate
     * @param position Position of the item
     */
    private fun populateItemRows(holder: PokemonResultViewHolder, position: Int) {
        val item = mPokemonResultList[position]
        holder.mItem = item
        holder.textViewPokemonName.text = UtilsClass.toCamelCase(item!!.name)
        holder.mSimpleDraweeView.setImageURI(item.pokemonImage)
        holder.textViewPokemonNumber.text = item.listPosition.toString()
        holder.mView.setOnClickListener { e: View? -> callback.onClick(item) }
        holder.selectedView.visibility = if (item.isSelected) View.VISIBLE else View.GONE
        holder.imageViewIconPokeball.setImageDrawable(if (item.isSelected) ResourcesCompat.getDrawable(mContext.resources, R.drawable.open, null) else ResourcesCompat.getDrawable(mContext.resources, R.drawable.close, null))
    }

    override fun getItemCount(): Int {
        return mPokemonResultList.size
    }

    /**
     * Assigns a value to the global List of PokemonResult and notifies the adapter of that change
     *
     * @param pokemonResults New list
     */
    fun refreshList(pokemonResults: List<PokemonResult?>) {
        mPokemonResultList = pokemonResults
        notifyDataSetChanged()
    }

    inner class PokemonResultViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val textViewPokemonName: TextView
        val mSimpleDraweeView: SimpleDraweeView
        val textViewPokemonNumber: TextView
        val selectedView: View
        val imageViewIconPokeball: ImageView
        var mItem: PokemonResult? = null

        init {
            textViewPokemonName = mView.findViewById(R.id.textViewPokemonName)
            textViewPokemonNumber = mView.findViewById(R.id.textViewPokemonNumber)
            selectedView = mView.findViewById(R.id.viewSelect)
            mSimpleDraweeView = mView.findViewById(R.id.imageViewPokemonList)
            mSimpleDraweeView.hierarchy = GenericDraweeHierarchyBuilder(mContext.resources)
                    .setProgressBarImage(ProgressBarDrawable())
                    .build()
            imageViewIconPokeball = mView.findViewById(R.id.imageViewIconPokeball)
        }
    }

    private inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }
}