package pt.rfsfernandes.pocketdex.custom.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.drawable.ProgressBarDrawable
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import pt.rfsfernandes.pocketdex.R
import pt.rfsfernandes.pocketdex.custom.callbacks.ItemListClicked
import pt.rfsfernandes.pocketdex.custom.utils.UtilsClass
import pt.rfsfernandes.pocketdex.databinding.PokemonResultRowBinding
import pt.rfsfernandes.pocketdex.model.service_responses.PokemonResult

class PokemonResultAdapter(
    private val mContext: Context,
    private val callback: ItemListClicked<PokemonResult?>
) : ListAdapter<PokemonResult, RecyclerView.ViewHolder>(DiffCallback) {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PokemonResultViewHolder(PokemonResultRowBinding.inflate(LayoutInflater.from(parent.context), parent, false), mContext)
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_ITEM
    }

    /**
     * Populates each row with the content of the current item
     *
     * @param holder   Holder used to populate
     * @param position Position of the item
     */
    private fun populateItemRows(holder: PokemonResultViewHolder, position: Int) {
        val item = getItem(position)
        holder.mItem = item
        holder.mBinding.textViewPokemonName.text = UtilsClass.toCamelCase(item!!.name)
        holder.mBinding.imageViewPokemonList.setImageURI(item.pokemonImage)
        holder.mBinding.textViewPokemonNumber.text = item.listPosition.toString()
        holder.mBinding.root.setOnClickListener { e: View? -> callback.onClick(item, position) }
        holder.mBinding.viewSelect.visibility = if (item.isSelected) View.VISIBLE else View.GONE
        holder.mBinding.imageViewIconPokeball.setImageDrawable(
            if (item.isSelected) ResourcesCompat.getDrawable(
                mContext.resources,
                R.drawable.open,
                null
            ) else ResourcesCompat.getDrawable(mContext.resources, R.drawable.close, null)
        )
    }

    class PokemonResultViewHolder(val mBinding: PokemonResultRowBinding, val mContext: Context) : RecyclerView.ViewHolder(mBinding.root) {

        var mItem: PokemonResult? = null

        init {
            mBinding.imageViewPokemonList.hierarchy = GenericDraweeHierarchyBuilder(mContext.resources)
                .setProgressBarImage(ProgressBarDrawable())
                .build()
        }
    }

    private inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }


    object DiffCallback : DiffUtil.ItemCallback<PokemonResult>() {
        override fun areItemsTheSame(
            oldItem: PokemonResult,
            newItem: PokemonResult
        ): Boolean = oldItem.listPosition == newItem.listPosition

        override fun areContentsTheSame(
            oldItem: PokemonResult,
            newItem: PokemonResult
        ): Boolean = oldItem.listPosition == newItem.listPosition
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PokemonResultViewHolder) {
            populateItemRows(holder, position)
        }
    }
}