package com.ihsanarslan.swiperefreshlayout.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ihsanarslan.swiperefreshlayout.R
import com.ihsanarslan.swiperefreshlayout.database.NoteDao
import kotlin.collections.ArrayList

data class Note(
    var id: Int,
    var title: String,
    var content: String,
    var color:Int,
    var liked:Boolean
)
class NoteAdapter(private val notes: ArrayList<Note>, val noteDao: NoteDao) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //itemviewin içeriğinde ki bilgileri buluyoruz
        private val titleTextView: TextView = itemView.findViewById(R.id.itemtitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.itemcontent)
        private val noteCardd:CardView=itemView.findViewById(R.id.cardView)

        fun bind(note: Note,context: Context) {
            if(note.title.length>30){
                titleTextView.text = note.title.substring(0,30)+"..."
            }
            else{
                titleTextView.text = note.title
            }
            if(note.content.length>50){
                descriptionTextView.text = note.content.substring(0,50)+"..."
            }
            else{
                descriptionTextView.text = note.content
            }
            noteCardd.setCardBackgroundColor(note.color)
            if (note.liked){
                noteCardd.findViewById<ImageView>(R.id.favButoon).setImageResource(R.drawable.baseline_favorite_24)
            }
            else{
                noteCardd.findViewById<ImageView>(R.id.favButoon).setImageResource(R.drawable.baseline_favorite_border_24)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem=notes[position]
        holder.bind(currentItem,holder.itemView.context)
    }


    override fun getItemCount(): Int {
        return notes.size
    }
}