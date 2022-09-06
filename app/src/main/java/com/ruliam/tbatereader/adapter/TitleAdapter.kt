package com.ruliam.tbatereader.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ruliam.tbatereader.R
import com.ruliam.tbatereader.models.Capitulos

class TitleAdapter(private val capitulosTitles : ArrayList<String>) : RecyclerView.Adapter<TitleAdapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position : Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_capitulos, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = capitulosTitles[position]
        holder.itemTitle.text = currentItem
    }

    override fun getItemCount(): Int {
        return capitulosTitles.size
    }


    inner class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val itemTitle: TextView = itemView.findViewById(R.id.capitulo_title)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}