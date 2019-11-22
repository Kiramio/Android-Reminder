package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.short_test.view.*

class MyAnimalAdapter(val items : ArrayList<String>, val context: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<MyAnimalAdapter.ViewHolder>() {
    var myListener: MyItemClickListener? = null
    interface MyItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
    fun setMyItemClickListener ( listener: MyItemClickListener){
        this.myListener = listener
    }
    // MUST
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.short_test, parent, false)
// inflate item view
        return ViewHolder(view)
    }
    // MUST
    override fun getItemCount(): Int {
// number of items in the list
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
// bind each item in the list to a view
        holder?.animalKind?.text = items.get(index = position)
    }
    // static inner ViewHolder class
    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        // hold textview
        val animalKind = itemView.tv_animal_type
        init {
            animalKind.setOnClickListener {
                if(myListener != null){
                    if(adapterPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION){
                        myListener!!.onItemClick(it, adapterPosition)
                    }
                }
            }
        }
    }
}