package com.calpito.beneficiaries.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.calpito.beneficiaries.R


class BeneficiaryDetailView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var nameTextView: TextView
    private var ssnTextView: TextView
    private var dobTextView: TextView
    private var phoneTextView: TextView
    private var addressTextView: TextView

    init {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.beneficiary_detail_dialog, this, true)

        nameTextView = findViewById(R.id.dialog_name)
        ssnTextView = findViewById(R.id.dialog_ssn)
        dobTextView = findViewById(R.id.dialog_dob)
        phoneTextView = findViewById(R.id.dialog_phone)
        addressTextView = findViewById(R.id.dialog_address)
    }

    fun setDetails(name: String, ssn: String, dob: String, phone: String, address: String) {
        nameTextView.text = name
        ssnTextView.text = ssn
        dobTextView.text = dob
        phoneTextView.text = phone
        addressTextView.text = address
    }

    fun setName(name: String) {
        nameTextView.text = name
    }

    fun setSsn(ssn: String) {
        ssnTextView.text = ssn
    }

    fun setDob(dob: String) {
        dobTextView.text = dob
    }

    fun setPhone(phone: String) {
        phoneTextView.text = phone
    }

    fun setAddress(address: String) {
        addressTextView.text = address
    }
}