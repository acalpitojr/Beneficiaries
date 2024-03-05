package ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.calpito.beneficiaries.R
import com.calpito.beneficiaries.model.Beneficiary
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BeneficiaryDetailBottomSheet : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(beneficiary: Beneficiary): BeneficiaryDetailBottomSheet {
            val args = Bundle().apply {
                putString("SSN", beneficiary.socialSecurityNumber)
                putString("DateOfBirth", beneficiary.dateOfBirth)
                putString("PhoneNumber", beneficiary.phoneNumber)
                putString("Address", beneficiary.beneficiaryAddress.toString()) // Implement toString() in BeneficiaryAddress class appropriately
            }
            val fragment = BeneficiaryDetailBottomSheet()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.beneficiary_detail_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tvSSN).text = arguments?.getString("SSN")
        view.findViewById<TextView>(R.id.tvDateOfBirth).text = arguments?.getString("DateOfBirth")
        view.findViewById<TextView>(R.id.tvPhoneNumber).text = arguments?.getString("PhoneNumber")
        view.findViewById<TextView>(R.id.tvAddress).text = arguments?.getString("Address")
    }
}
