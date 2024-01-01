package com.example.scribblenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toolbar
import androidx.room.RoomDatabase
import com.example.scribblenotes.databinding.ActivityCreatenotesBinding
import com.example.scribblenotes.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class createnotes : AppCompatActivity() {
    private lateinit var savenote:FloatingActionButton
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var roomDatabase: RoomDatabase
    private lateinit var binding: ActivityCreatenotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding= ActivityCreatenotesBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbarcreatenote)




    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }
}