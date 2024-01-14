package com.example.scribblenotes.fragments

import android.app.AlertDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.scribblenotes.MainActivity
import com.example.scribblenotes.R
import com.example.scribblenotes.databinding.FragmentAddNoteBinding
import com.example.scribblenotes.databinding.FragmentEditNoteBinding
import com.example.scribblenotes.model.Note
import com.example.scribblenotes.viewmodel.NoteViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Date


class EditNoteFragment : Fragment(R.layout.fragment_edit_note),MenuProvider {
    private var editNoteBinding: FragmentEditNoteBinding? =null
    private val binding get() = editNoteBinding!!
    private  val currentDate= SimpleDateFormat.getDateInstance().format(Date())

    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note
    private var db=Firebase.firestore

    private val args:EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        editNoteBinding= FragmentEditNoteBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.renderMD(currentNote.noteDesc)
        binding.editNoteDesc.setStylesBar(binding.styleBar)

        binding.editNoteFab.setOnClickListener {
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteContent = binding.editNoteDesc.getMD()
            val currentDate=currentDate.toString()
            if (noteTitle !=currentNote.noteTitle || noteContent!=currentNote.noteDesc){
                if (noteTitle.isNotEmpty() && noteContent.isNotEmpty()) {
                    val note = Note(currentNote.id, noteTitle, noteContent,currentDate)
                    notesViewModel.updateNote(note)
                    db.collection("Notes").document().set(note).addOnSuccessListener{
                        Toast.makeText(context,"Note Updated",Toast.LENGTH_SHORT).show()
                }
                    view.findNavController().popBackStack(R.id.homeFragment,false)
                }
                else{
                    Toast.makeText(context, "Please Enter data in both the fields", Toast.LENGTH_SHORT).show()

                }
            }
            else{
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }
        }
    }
    private fun deleteNote(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete"){ _,_ ->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)


            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.editnote_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.deleteMenu -> {
                deleteNote()
                true
            }
            else -> false
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding=null
    }
}

