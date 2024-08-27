package com.zyasin.beneficiarylist.view

import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zyasin.beneficiarylist.model.Beneficiary

// Adapter class for displaying a list of beneficiaries in a RecyclerView.
class BeneficiaryAdapter(
    private val beneficiaries: List<Beneficiary>,
    private val onClick: (Beneficiary) -> Unit
) : RecyclerView.Adapter<BeneficiaryAdapter.BeneficiaryViewHolder>() {

    inner class BeneficiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return BeneficiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeneficiaryViewHolder, position: Int) {
        val beneficiary = beneficiaries[position]
        // Use the getDesignation() method to get the human-readable designation.
        holder.textView.text =
            "${beneficiary.firstName} ${beneficiary.middleName} ${beneficiary.lastName} - ${beneficiary.beneType} (${beneficiary.getDesignation()})"
        holder.textView.setOnClickListener {
            onClick(beneficiary)
        }

        // Set text size to 18sp and make the text bold
        holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        holder.textView.setTypeface(null, Typeface.BOLD)
    }

    override fun getItemCount(): Int = beneficiaries.size
}