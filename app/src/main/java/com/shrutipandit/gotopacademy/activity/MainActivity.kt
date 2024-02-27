package com.shrutipandit.gotopacademy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.form.setOnClickListener {

            val intent = Intent(this, Form::class.java)
            startActivity(intent)
        }

        binding.result.setOnClickListener {

            val intent = Intent(this, Result::class.java)
            startActivity(intent)
        }

    binding.notice.setOnClickListener {
        val intent = Intent(this, Notice::class.java)
        startActivity(intent)
    }

        binding.about.setOnClickListener {

            val intent = Intent(this, About::class.java)
            startActivity(intent)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
              val intent = Intent(this,Profile::class.java)
                startActivity(intent)
                true
            }

            R.id.aboutus -> {
                Toast.makeText(this, "Clicked on abouus page", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.helpus -> {
                Toast.makeText(this, "Clicked on help page", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.faq -> {
                Toast.makeText(this, "Clicked on fas page", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.logout -> {
                Toast.makeText(this, "Clicked on Setting page", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}
