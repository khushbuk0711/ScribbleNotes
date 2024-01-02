package com.example.scribblenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.room.RoomDatabase
import com.example.scribblenotes.databinding.ActivityCreatenotesBinding
import com.google.firebase.auth.FirebaseAuth

class createnotes : AppCompatActivity() {
    private lateinit var binding: ActivityCreatenotesBinding
    private lateinit var roomDatabase: RoomDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreatenotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarcreatenote)
        val title=binding.notetitle.text.toString()
        val content=binding.notecontent.text.toString()
        binding.savenotes.setOnClickListener{
            if (title.isEmpty()||content.isEmpty()){
                Toast.makeText(this, "Both Fields are required", Toast.LENGTH_SHORT).show()
            }
            else{

            }


        }




    }

}