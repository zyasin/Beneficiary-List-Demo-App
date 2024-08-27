package com.zyasin.beneficiarylist.view

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zyasin.beneficiarylist.R
import com.zyasin.beneficiarylist.viewmodel.BeneficiaryViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var beneficiaryViewModel: BeneficiaryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create and configure the Toolbar.
        val toolbar = Toolbar(this).apply {
            // Set a light blue background color.
            setBackgroundColor(resources.getColor(R.color.bright_blue, null))

            // Create a custom layout for the Toolbar title.
            val titleTextView = TextView(this@MainActivity).apply {
                text = getString(R.string.beneficiary_list) // Using string resource.
                textSize = 20f
                setTextColor(Color.WHITE)
                setTypeface(null, Typeface.BOLD)
                gravity = Gravity.CENTER
                layoutParams = Toolbar.LayoutParams(
                    Toolbar.LayoutParams.WRAP_CONTENT,
                    Toolbar.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER
                }
            }

            // Add the custom title to the Toolbar
            addView(titleTextView)
        }

        // Create RecyclerView
        val recyclerView = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // Create layout and add Toolbar and RecyclerView
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL

            // Add the Toolbar
            addView(
                toolbar, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )

            // Add the RecyclerView with a margin to move it slightly below the Toolbar.
            addView(recyclerView, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            ).apply {
                setMargins(0, 50, 0, 0) // Add top margin to move RecyclerView below Toolbar.
            })
        }

        setContentView(layout)

        // Set up ViewModel and RecyclerView adapter.
        beneficiaryViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
                .get(BeneficiaryViewModel::class.java)

        beneficiaryViewModel.beneficiaries.observe(this, { beneficiaries ->
            val adapter = BeneficiaryAdapter(beneficiaries) { beneficiary ->
                val intent = Intent(this, BeneficiaryDetailActivity::class.java)
                intent.putExtra("beneficiary", beneficiary)
                startActivity(intent)
            }
            recyclerView.adapter = adapter
        })
    }
}
