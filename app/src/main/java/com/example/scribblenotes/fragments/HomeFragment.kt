package com.example.scribblenotes.fragments

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.scribblenotes.MainActivity
import com.example.scribblenotes.R
import com.example.scribblenotes.adapter.NoteAdapter
import com.example.scribblenotes.databinding.FragmentHomeBinding
import com.example.scribblenotes.model.Note
import com.example.scribblenotes.viewmodel.NoteViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.Date


class HomeFragment : Fragment(R.layout.fragment_home), android.widget.SearchView.OnQueryTextListener,MenuProvider {
    private var homeBinding:FragmentHomeBinding?=null
    private lateinit var firebaseAuth:FirebaseAuth
    private val binding get()= homeBinding!!
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner,Lifecycle.State.RESUMED)
        notesViewModel = (activity as MainActivity).noteViewModel
        setupHomeRecyclerView()


        binding.addNoteFab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)

        }
    }
    private fun updateUI(note:List<Note>?){
        if (note != null){
            if(note.isNotEmpty()){
                binding.emptyNotesImage.visibility= View.GONE
                binding.homeRecyclerView.visibility= View.VISIBLE
            }
            else{
                binding.emptyNotesImage.visibility= View.VISIBLE
                binding.homeRecyclerView.visibility= View.GONE

            }
        }
    }
    private fun setupHomeRecyclerView(){
        noteAdapter = NoteAdapter()
        binding.homeRecyclerView.apply {
            layoutManager= StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteAdapter
        }
        activity?.let {
            notesViewModel.getAllNotes().observe(viewLifecycleOwner){note->
                noteAdapter.differ.submitList(note)
                updateUI(note)
            }
        }
    }
    private fun searchNote(query: String?){
        val searchQuery = "%$query"
        notesViewModel.searchNote(searchQuery).observe(this){
            noteAdapter.differ.submitList(it)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!=null){
            searchNote(newText)
        }
        return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.options, menu)
        val menuSearch = menu.findItem(R.id.search).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)

    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.logout -> {
                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.signOut()
                view?.findNavController()?.navigate(R.id.action_homeFragment_to_login2)
                true
            }else -> false
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        homeBinding=null
    }

}

