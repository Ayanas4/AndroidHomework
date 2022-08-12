package com.example.androidhomework.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.androidhomework.R
import com.example.androidhomework.fragment.ChatFragment
import com.example.androidhomework.fragment.ExploreFragment
import com.example.androidhomework.fragment.PhoneFragment
import com.example.androidhomework.fragment.WalletFragment
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_main.*
import org.chromium.base.Log


class MainActivity : AppCompatActivity() {
    var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        token = intent.getStringExtra("token").toString()
        init()
    }

    fun init() {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.frameLayout, PhoneFragment()).commit()
        navigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.phone -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, PhoneFragment()).commit()
                }

                R.id.chat -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ChatFragment()).commit()
                }

                R.id.explore -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ExploreFragment()).commit()
                }

                R.id.wallet -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, WalletFragment(token)).commit()
                }
            }
            true
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        //指定R.menu.名稱
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}