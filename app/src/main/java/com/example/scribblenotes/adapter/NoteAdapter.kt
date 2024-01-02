package com.example.scribblenotes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.scribblenotes.databinding.ActivityCreatenotesBinding
import com.example.scribblenotes.databinding.NotesLayoutBinding
import com.example.scribblenotes.model.Note

class NoteAdapter:RecyclerView.Adapter<NoteAdapter.NoteViewHolder>( ){

    class NoteViewHolder(val itemBinding: NotesLayoutBinding):RecyclerView.ViewHolder(itemBinding.root)
    private val differCallback= object :DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id==newItem.id &&
                    oldItem.notecontent==newItem.notecontent&&
                    oldItem.notetitle==newItem.notetitle
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NotesLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote=differ.currentList[position]
        holder.itemBinding.notestitle.text=currentNote.notetitle
        holder.itemBinding.notescontent.text=currentNote.notecontent
        holder.itemView.setOnClickListener{
            val direction=
        }


    }

}