package com.globallogic.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.globallogic.app.BR
import com.globallogic.app.R
import com.globallogic.app.databinding.ItemPokemonBinding

class PokemonViewpagerAdapter : RecyclerView.Adapter<PokemonViewpagerAdapter.PokemonViewHolder>() {

    private var mImageList:List<String> = ArrayList()

    fun setImageData(list: List<String>) {
        mImageList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_pokemon,parent, false))

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(mImageList[position])
    }

    override fun getItemCount(): Int  = mImageList.size

    inner class PokemonViewHolder(private val itemPokemonBinding: ItemPokemonBinding):
        RecyclerView.ViewHolder(itemPokemonBinding.root){

        fun bind(image:String) {
            itemPokemonBinding.setVariable(BR.url, image)
            itemPokemonBinding.executePendingBindings()
        }
    }

}