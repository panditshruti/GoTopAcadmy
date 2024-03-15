package com.shrutipandit.gotopacademy.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.UserRepository
import com.shrutipandit.gotopacademy.databinding.ActivityMainBinding
import com.shrutipandit.gotopacademy.ui.HomeFragmentDirections
import com.shrutipandit.gotopacademy.viewmodel.AuthViewModel
import com.shrutipandit.gotopacademy.viewmodel.AuthViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val userRepository = UserRepository()
        val viewModelFactory = AuthViewModelFactory(userRepository)
        authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        if (authViewModel.isUserAlreadyLogin()){
            startActivity(Intent(this,LogInPage::class.java))
            finish()
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.allFormFragment,
                R.id.noticeFragment,
                R.id.resultFragment,
                R.id.newsFragment
            )
        )
        binding.toolbar.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController)||super.onOptionsItemSelected(item)
    }
    fun logOut(item: MenuItem){
        authViewModel.signOutUser(object :UserRepository.Callback{
            override fun onSuccess() {
                val intent = Intent (this@MainActivity,LogInPage::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(exception: Exception) {
                Toast.makeText(this@MainActivity, exception.message.toString(), Toast.LENGTH_SHORT).show()
            }


        })

    }

    fun shareAppLink(item: MenuItem) {
        val packageName = packageName

        // Create a URI for the app on the Play Store
        val appStoreUri = Uri.Builder()
            .scheme("https")
            .authority("play.google.com")
            .appendPath("store")
            .appendPath("apps")
            .appendPath("details")
            .appendQueryParameter("id", packageName)
            .build()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, appStoreUri.toString())
        }

        startActivity(Intent.createChooser(shareIntent, "Share App Link"))
    }


}
