package com.franvalle.myimc_v4.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.franvalle.myimc_v4.adapters.ViewPageAdapter
import com.franvalle.myimc_v4.databinding.ActivityMainBinding

class

MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Se carga el toolBar
        setSupportActionBar(binding.toolbar)
        //Se carga el adapter
        val adapter = ViewPageAdapter(supportFragmentManager)
        // Se añaden los fragments y los títulos de pestañas.
        adapter.addFragment(FragmentImc(), "IMC")
        adapter.addFragment(FragmentHistorico(), "HISTÓRICO")

        // Se asocia el adapter.
        binding.viewPager.adapter = adapter
        //Se cargan las tabs
        binding.tabs.setupWithViewPager(binding.viewPager)
    }
}