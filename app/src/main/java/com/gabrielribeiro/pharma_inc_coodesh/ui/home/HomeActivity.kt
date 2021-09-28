package com.gabrielribeiro.pharma_inc_coodesh.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.gabrielribeiro.pharma_inc_coodesh.BaseViewModel
import com.gabrielribeiro.pharma_inc_coodesh.R
import com.gabrielribeiro.pharma_inc_coodesh.data.api.RetrofitInstance
import com.gabrielribeiro.pharma_inc_coodesh.data.repositores.UserRepositoryImplemented
import com.gabrielribeiro.pharma_inc_coodesh.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityHomeBinding
    lateinit var viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.PharmaInc_NoActionBar)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            BaseViewModel.BaseViewModelFactory(UserRepositoryImplemented(RetrofitInstance().getApi()))
        ).get(BaseViewModel::class.java)
        viewModel.getUser(50)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigationView()

    }

    private fun setupNavigationView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
    }
    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}