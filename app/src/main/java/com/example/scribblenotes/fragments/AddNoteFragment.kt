package com.example.scribblenotes.fragments

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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.scribblenotes.MainActivity
import com.example.scribblenotes.R
import com.example.scribblenotes.databinding.FragmentAddNoteBinding
import com.example.scribblenotes.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Date


class AddNoteFragment : Fragment(R.layout.fragment_add_note), MenuProvider {

    private var addNoteBinding: FragmentAddNoteBinding?=null
    private val binding get()=addNoteBinding!!
    private  val currentDate= SimpleDateFormat.getDateInstance().format(Date())
    private lateinit var navController: NavController


    private lateinit var notesViewModel: NoteViewModel
    private lateinit var addNoteView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addNoteBinding= FragmentAddNoteBinding.inflate(inflater,container,false)

        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        notesViewModel = (activity as MainActivity).noteViewModel
        addNoteView=view
        binding.addNoteDesc.setStylesBar(binding.styleBar)
        binding.savenotes.setOnClickListener{
            saveNote(addNoteView)
        }
    }
    private fun saveNote(view: View){
        val noteTitle=binding.addNoteTitle.text.toString().trim()
        val noteContent=binding.addNoteDesc.getMD()
        val currentDate=currentDate.toString()
        if (noteTitle.isNotEmpty() &&noteContent.isNotEmpty()){
            val note =com.example.scribblenotes.model.Note(0,noteTitle,noteContent,currentDate)
            notesViewModel.addNote(note)
            Toast.makeText(addNoteView.context,"Note Saved",Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)
        }
        else{
            Toast.makeText(addNoteView.context,"Both Fields Required",Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.addnote_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.back->{
                view?.findNavController()?.navigate(R.id.action_addNoteFragment_to_homeFragment)
                true

            }
            else-> false
        }



    }
    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding=null
    }
}

