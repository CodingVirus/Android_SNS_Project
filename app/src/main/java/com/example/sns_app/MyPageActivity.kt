package com.example.sns_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import com.google.android.material.tabs.TabLayout

class MyPageActivity : AppCompatActivity() {

    private val tabLayout: TabLayout by lazy { findViewById(R.id.tab_layout) }
    private val postGridLayout: GridLayout by lazy { findViewById(R.id.grid_my_post) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        postGridLayout.visibility = View.VISIBLE
                    }

                    1 -> {
                        postGridLayout.visibility = View.INVISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

    }
}