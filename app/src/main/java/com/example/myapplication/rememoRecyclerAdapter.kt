package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class rememoRecyclerAdapter(val items : ArrayList<ReminderData>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<rememoRecyclerAdapter.ReminderViewHolder>() {
    var myListener: MyItemClickListener? = null
    var lastPosition = -1 // for animation
    // Adapter Listener!!!
    interface MyItemClickListener {
        fun onItemClickedFromAdapter(reminder : ReminderData)
        //fun onItemLongClickedFromAdapter(position : Int)
        //fun onOverflowMenuClickedFromAdapter(view: View, position: Int)
    }
    fun setMyItemClickListener ( listener: MyItemClickListener){
        this.myListener = listener
    }
    // MUST DO!!
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ReminderViewHolder {
        val layoutInflater = LayoutInflater.from(p0.context) // p0 is parent
        val view : View
// p1 -> View Type, check getItemViewType function!!
        view = when(p1){
            1 -> {
                layoutInflater.inflate(R.layout.item_ongoing, p0, false)
            }
            2 -> {
                layoutInflater.inflate(R.layout.item_finished, p0, false)
            }
            else -> {
                layoutInflater.inflate(R.layout.item_trash, p0, false)
            }
        }
        return ReminderViewHolder(view)
    }
    // MUST DO!!
    override fun getItemCount(): Int {
        return items.size
    }
    // MUST DO!!
    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val rememo = items[position]
        holder.memoTitle.text = rememo.header
        var length = rememo.content!!.length
        length = if (length > 150) 150 else length
        holder.memoOverview.text = rememo.content?.substring(0, length - 1) + " ..."
        holder.memoSelect.isChecked = rememo.checked!!
        holder.memoDelete.isChecked = rememo.deleted!!
//setAnimation(holder.itemView, position)
    }
    private fun setAnimation(view: View, position: Int){
        if(position != lastPosition){
            when(getItemViewType(position)){
                1 -> {
                    val animation = AnimationUtils.loadAnimation(view.context, android.R.anim.slide_in_left)
                    animation.duration = 700
                    animation.startOffset = position * 100L
                    view.startAnimation(animation)
                }
                2 -> {
                    val animation = AlphaAnimation(0.0f, 1.0f)
                    animation.duration = 1000
                    animation.startOffset = position * 50L
                    view.startAnimation(animation)
                }
                else -> {
                    val animation = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f)
                    animation.duration = 500
                    animation.startOffset = position * 200L
                    view.startAnimation(animation)
                }
            }
//animation.startOffset = position * 100L
            lastPosition = position
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (items[position].status!! == 3 ) {
            return 3
        } else if (items[position].status!! == 2 ) {
            return 2
        } else {
            return 1
        }
    }
    fun getItem(index: Int) : Any{
        return items[index]
    }
    fun addMemo() {
        //addReminder
        notifyItemInserted(lastPosition+1)
    }

    fun findFirst(query: String): Int {
        for (i in items.indices) {
            if (items[i].header!!.toLowerCase(Locale.getDefault()).contains(query)) {
                return i
            }
        }
        return 0
    }

    fun deleteMemo(position: Int){
        items.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ReminderViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view){
        val memoTitle = view.findViewById<TextView>(R.id.rvHeader)
        val memoOverview = view.findViewById<TextView>(R.id.rvDetail)
        val memoDelete = view.findViewById<RadioButton>(R.id.rvDelete)
        val memoSelect = view.findViewById<CheckBox>(R.id.rvChx)
        init{
            memoSelect.setOnCheckedChangeListener { buttonView, isChecked ->
                items[adapterPosition].checked = isChecked
            }
            memoDelete.setOnCheckedChangeListener { buttonView, isDeleted ->
                items[adapterPosition].deleted = isDeleted
            }
            view.setOnClickListener {
                if(myListener != null){
                    if(adapterPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION){
                        myListener!!.onItemClickedFromAdapter(items[adapterPosition])
                    }
                }
            }
        }
    }
}