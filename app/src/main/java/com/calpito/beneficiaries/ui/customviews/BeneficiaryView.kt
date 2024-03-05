package com.calpito.beneficiaries.ui.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.ImageView
import com.calpito.beneficiaries.R

/*Custom View Class for showing beneficiary data*/
class BeneficiaryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var nameTextView: TextView
    private var benefitTypeTextView: TextView
    private var designationTextView: TextView
    private var arrowImageView: ImageView

    init {
        // Inflate the layout
        val view = LayoutInflater.from(context).inflate(R.layout.beneficiary_item, this, true)

        // Initialize views
        nameTextView = view.findViewById(R.id.tv_name)
        benefitTypeTextView = view.findViewById(R.id.tv_benefit_type)
        designationTextView = view.findViewById(R.id.tv_designation)
        arrowImageView = view.findViewById(R.id.iv_arrow) // Make sure to have an ID for your ImageView in XML

    }

    fun setBeneficiaryName(name: String) {
        nameTextView.text = name
    }

    fun setBenefitType(benefitType: String) {
        benefitTypeTextView.text = benefitType
    }

    fun setDesignation(designation: String) {
        designationTextView.text = designation
    }

}
