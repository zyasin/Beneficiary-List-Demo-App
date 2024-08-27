package com.zyasin.beneficiarylist.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.zyasin.beneficiarylist.R
import com.zyasin.beneficiarylist.model.Beneficiary
import java.text.SimpleDateFormat
import java.util.Locale

// Activity class for displaying detailed information about a selected beneficiary.
class BeneficiaryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create and configure the Toolbar with a back arrow.
        val toolbar = Toolbar(this).apply {
            setBackgroundColor(resources.getColor(R.color.bright_blue, null))

            // Create a custom layout for the Toolbar title.
            val titleTextView = TextView(this@BeneficiaryDetailActivity).apply {
                text = getString(R.string.beneficiary_detail) // Using string resource.
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

            // Set the Toolbar as the ActionBar
            setSupportActionBar(this)
        }

        // Enable the back button on the Toolbar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false) // Hide the default title to use the custom title
        }

        // Set the back arrow color to white
        toolbar.navigationIcon?.setTint(resources.getColor(R.color.white, null))

        // Handle back button click to go back to the previous activity (MainActivity)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Set up the rest of the layout
        val beneficiary = intent.getParcelableExtra<Beneficiary>("beneficiary")

        // Create the main layout that contains all the text views
        val contentLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 32, 16, 16) // Add top padding to move it below the toolbar
        }

        beneficiary?.let {
            contentLayout.addView(createTextView("Name: ${it.firstName} ${it.middleName} ${it.lastName}"))
            contentLayout.addView(createTextView("SSN: ${it.socialSecurityNumber}"))
            contentLayout.addView(createTextView("Date of Birth: ${formatDate(it.dateOfBirth)}"))
            contentLayout.addView(createTextView("Phone: ${it.phoneNumber}"))
            contentLayout.addView(createTextView("Address: ${it.beneficiaryAddress.firstLineMailing}, ${it.beneficiaryAddress.city}, ${it.beneficiaryAddress.stateCode}, ${it.beneficiaryAddress.zipCode}, ${it.beneficiaryAddress.country}"))
        }

        // Create a parent layout that includes the toolbar and the content layout
        val parentLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(
                toolbar, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )

            addView(
                contentLayout, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }

        setContentView(parentLayout)
    }

    private fun createTextView(text: String): TextView {
        return TextView(this).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            setTextColor(Color.BLACK)
            setText(text)
            setTypeface(null, Typeface.BOLD)
            setPadding(20, 20, 20, 0) // Padding for individual TextView (20dp for top and sides)
        }
    }

    private fun formatDate(date: String): String {
        val originalFormat = SimpleDateFormat("MMddyyyy", Locale.US)
        val targetFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        val parsedDate = originalFormat.parse(date)
        return targetFormat.format(parsedDate)
    }
}