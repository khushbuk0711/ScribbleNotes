package com.example.scribblenotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.scribblenotes.databinding.NotesLayoutBinding
import com.example.scribblenotes.fragments.HomeFragment
import com.example.scribblenotes.fragments.HomeFragmentDirections
import com.example.scribblenotes.model.Note
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import org.commonmark.node.SoftLineBreak
import kotlin.coroutines.coroutineContext

class NoteAdapter:RecyclerView.Adapter<NoteAdapter.NoteViewHolder>( ){

    inner class NoteViewHolder(val itemBinding: NotesLayoutBinding):RecyclerView.ViewHolder(itemBinding.root){
        val markWon= Markwon.builder(itemView.context).usePlugin(StrikethroughPlugin.create())
        .usePlugin(TaskListPlugin.create(itemView.context))
        .usePlugin(object : AbstractMarkwonPlugin(){
            override fun configureVisitor(builder: MarkwonVisitor.Builder) {
                super.configureVisitor(builder)
                builder.on(
                    SoftLineBreak::class.java
                ){visitor,_,->visitor.forceNewLine()}
            }
        }).build()
    }

    private val differCallback= object :DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id==newItem.id &&
                    oldItem.noteDesc==newItem.noteDesc&&
                    oldItem.noteTitle==newItem.noteTitle
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
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote=differ.currentList[position]
        holder.itemBinding.noteTitle.text=currentNote.noteTitle
        holder.markWon.setMarkdown(holder.itemBinding.noteDesc,currentNote.noteDesc)
        holder.itemBinding.noteDate.text = currentNote.noteDate
        holder.itemView.setOnClickListener{
            val direction= HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote)
            it.findNavController().navigate(direction)

        }


    }

}