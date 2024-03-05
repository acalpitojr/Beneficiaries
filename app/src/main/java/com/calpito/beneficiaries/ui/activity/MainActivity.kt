package com.calpito.beneficiaries.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calpito.beneficiaries.databinding.ActivityMainBinding
import com.calpito.beneficiaries.model.Beneficiary
import com.calpito.beneficiaries.ui.customviews.BeneficiaryDetailView
import com.calpito.beneficiaries.ui.customviews.BeneficiaryView
import com.calpito.beneficiaries.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: recyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }


    /**
     * Initializes the view components, sets up listeners, and starts observers for UI updates.
     */
    private fun initView() {
        //recycler view init
        adapter = recyclerViewAdapter()
        binding.rvConversions.layoutManager = LinearLayoutManager(this)
        binding.rvConversions.adapter = adapter

        //start observing data
        setupObservers()

        //get initial data
        mainViewModel.getBeneficiariesAndDisplay()

    }

    class recyclerViewAdapter :
        RecyclerView.Adapter<recyclerViewAdapter.BeneficiaryViewHolder>() {

        // ViewHolder class for the adapter.  This will hold our custom view
        class BeneficiaryViewHolder(val beneficiaryView: BeneficiaryView) : RecyclerView.ViewHolder(beneficiaryView)

        override fun getItemViewType(position: Int): Int {
            // depends on your problem
            /*position is the item number.  It will be passed to the oncreateviewlholder in the viewType parameter*/
            return position
        }


        //inflate the layout
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiaryViewHolder {
            // Create an instance of the custom view
            val beneficiaryView = BeneficiaryView(parent.context).apply {
                // Make sure BeneficiaryView has appropriate layout parameters
                layoutParams = RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
            }

            //create the viewHolder with our customView
            return BeneficiaryViewHolder(beneficiaryView)
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        /*set view values*/
        override fun onBindViewHolder(holder: BeneficiaryViewHolder, position: Int) {

            val item = differ.currentList[position]
            holder.beneficiaryView.setBeneficiaryName("${item.firstName} ${item.lastName}")
            holder.beneficiaryView.setBenefitType("Benefit Type: ${item.beneType}")
            holder.beneficiaryView.setDesignation("Designation: ${item.designationCode}")

            //Show beneficiary details when clicked
            holder.itemView.setOnClickListener {
                // Create and setup the dialog
                val context = holder.itemView.context

                //load our custom view
                val beneficiaryDetailView = BeneficiaryDetailView(context)
                //data to display in our custom view
                val name = "${item.firstName} ${item.lastName}"
                val ssn = "${item.socialSecurityNumber}"
                val dob = "${item.dateOfBirth}"
                val phone = "${item.phoneNumber}"

                //address
                val addrLine1 = item.beneficiaryAddress.firstLineMailing
                var addrLine2 = item.beneficiaryAddress.scndLineMailing
                if(addrLine2 == null){
                    addrLine2 = ""
                } else {
                    addrLine2 = "\n$addrLine2"
                }
                val city = "\n${item.beneficiaryAddress.city}"
                val state = "\n${item.beneficiaryAddress.stateCode}"
                val zipcode = "${item.beneficiaryAddress.zipCode}"
                val country = "\n${item.beneficiaryAddress.country}"
                val address = "$addrLine1$addrLine2$city$state, $zipcode$country"

                //set all data into our custom view
                beneficiaryDetailView.setDetails(name, ssn, dob, phone, address)

                //CREATE THE DIALOG TO DISPLAY BENEFICIARY DETAILS
                val dialog = Dialog(context)
                dialog.setCanceledOnTouchOutside(true)
                dialog.setContentView(beneficiaryDetailView) // Ensure you have this layout

                // Adjust the dialog width to 95% of the screen width
                val window = dialog.window
                if (window != null) {
                    val displayMetrics = context.resources.displayMetrics
                    val width = (displayMetrics.widthPixels * 0.95).toInt()
                    window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
                }

                // Show the dialog
                dialog.show()
            }


        }


        /*this code sets up a mechanism for efficiently handling updates to a list of
        objects in a RecyclerView. It uses DiffUtil for calculating differences between lists,
        and AsyncListDiffer for handling these differences asynchronously and efficiently
        updating the UI. The submitList method is a convenient way to submit a
        new list for calculation and update.*/
        private val diffCallback = object : DiffUtil.ItemCallback<Beneficiary>() {
            override fun areItemsTheSame(oldItem: Beneficiary, newItem: Beneficiary): Boolean {
                var result = false
                if (oldItem.socialSecurityNumber == newItem.socialSecurityNumber) {
                    result = true
                }
                return result
            }

            override fun areContentsTheSame(oldItem: Beneficiary, newItem: Beneficiary): Boolean {
                var result = false
                if (oldItem.equals(newItem)) {
                    result = true
                }
                return result
            }
        }

        private val differ = AsyncListDiffer(this, diffCallback)

        fun submitList(list: List<Beneficiary>) = differ.submitList(list)
    }

    /**
     * Sets up observers to handle updates in the UI.
     */
    private fun setupObservers() {
        setupRecyclerViewObserver()
    }

    /**
     * Sets up an observer for the RecyclerView to handle updates in the UI
     */
    private fun setupRecyclerViewObserver() {
        //observe liveData from ViewModel
        mainViewModel.beneficiaryList.observe(this, Observer { beneficiaryList ->
            adapter.submitList(beneficiaryList)
        })
    }


}
