package com.example.scribblenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.scribblenotes.database.NoteDatabase
import com.example.scribblenotes.databinding.ActivityMainBinding
import com.example.scribblenotes.repository.NoteRepository
import com.example.scribblenotes.viewmodel.NoteViewModel
import com.example.scribblenotes.viewmodel.NoteViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
    }
    private fun setupViewModel(){
        val noteRepository= NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application,noteRepository)
        noteViewModel= ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]



    }
}