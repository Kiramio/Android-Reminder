package com.example.myapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class rememoRecyclerAdapter(context : Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<rememoRecyclerAdapter.ReminderViewHolder>() {
    val items = ArrayList<ReminderData>()
    var myListener: MyItemClickListener? = null
    var lastPosition = -1 // for animation
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var ref = database.child("reminders")
    val TAG = "FB Adapter"
    val token = FirebaseAuth.getInstance().uid

    var childEventListener = object: ChildEventListener{
        override fun onCancelled(p0: DatabaseError) {
            Log.d(TAG, "child event listener - onCancelled" + p0.toException())
            Toast.makeText(context, "Fail to load data", Toast.LENGTH_SHORT).show()
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            Log.d(TAG, "child event listener - onChildMoved" +p0.toString())
            val data = p0.getValue<ReminderData>(ReminderData::class.java)
            val key = p0.key
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            Log.d(TAG, "child event listener - onChildChanged" +p0.toString())
            val data = p0.getValue<ReminderData>(ReminderData::class.java)
            val key = p0.key
            if(data != null && key != null) {
                for((index, reminder) in items.withIndex()){
                    items.removeAt(index)
                    items.add(index, reminder)
                    notifyItemChanged(index)
                }
            }
        }

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            Log.d(TAG, "child event listener - onChildAdded" +p0.toString())
            val data = p0.getValue<ReminderData>(ReminderData::class.java)
            //Log.d("11111111", "msg" + p0.child("uid").getValue<String>(String::class.java))
            val key = p0.key
            if(data != null && key != null) {
                var insertPos = items.size
                /*for( reminder in items ){
                    if(reminder..equals(key))
                        return
                }*/
                items.add(insertPos, data)
                notifyItemInserted(insertPos)
            }
            /*for(ds in p0.getChildren()){
                if(!token!!.equals(ds.child("uid").getValue(String::class.java))) continue
                var title = ds.child("header").getValue(String::class.java)
                var content = ds.child("content").getValue(String::class.java)
                var status = ds.child("status").getValue(Int::class.java)
                var reminderData = ReminderData(header = title, content = content, status = status)

            }*/
        }

        override fun onChildRemoved(p0: DataSnapshot) {
            Log.d(TAG, "child event listener - onChildRemoved" +p0.toString())
            val data = p0.getValue<ReminderData>(ReminderData::class.java)
            val key = p0.key
            if(data != null && key != null) {
                var delPos = -1
                /*for( (index, movie) in items.withIndex()){
                    /f(reminder.title.equals(key)){
                        delPos = index
                        break
                    }
                }*/
                if( delPos != -1 ){
                    items.removeAt(delPos)
                    notifyItemRemoved(delPos)
                }
            }
        }

    }

    init {
        ref.addChildEventListener(childEventListener)
// initializeFB()
    }


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
        Log.d("INFLATE11111111", "id" + p1  )
        view = when(p1){
            0 -> {
                layoutInflater.inflate(R.layout.item_ongoing, p0, false)
            }
            1 -> {
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
            //memoDelete.setOnCheckedChangeListener { buttonView, isDeleted ->
            //    items[adapterPosition].deleted = isDeleted
            //}
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