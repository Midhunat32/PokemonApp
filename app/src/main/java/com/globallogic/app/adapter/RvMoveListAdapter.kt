package com.globallogic.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.globallogic.app.R
import com.globallogic.app.data.Moves
import com.globallogic.app.databinding.RvMoveItemBinding

class RvMoveListAdapter : RecyclerView.Adapter<RvMoveListAdapter.MovesItemViewHolder>() {

    private var moves :List<Moves> = mutableListOf()

    fun setMoves(moves :List<Moves>){
        this.moves = moves
        notifyDataSetChanged()
    }


    inner class MovesItemViewHolder(private val binding : RvMoveItemBinding)
        : RecyclerView.ViewHolder(binding.root){

            fun bind(moves : Moves){
                binding.setVariable(BR.moves, moves.move)
                binding.executePendingBindings()
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovesItemViewHolder {
        val binding : RvMoveItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context)
            , R.layout.rv_move_item, parent, false)
        return MovesItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovesItemViewHolder, position: Int) {
       val move = moves[position]
        holder.bind(move)

    }

    override fun getItemCount(): Int {
        return moves.size
    }
}