package com.calpito.beneficiaries

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.calpito.beneficiaries.R
import com.calpito.beneficiaries.databinding.ActivityMainBinding
import com.calpito.beneficiaries.model.Beneficiary
import com.calpito.beneficiaries.ui.customviews.BeneficiaryDetailView
import com.calpito.beneficiaries.ui.customviews.BeneficiaryView
import com.calpito.beneficiaries.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import ui.fragments.BeneficiaryDetailBottomSheet

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: recyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    /*Screen visible*/
    override fun onStart() {
        super.onStart()
        //mainViewModel.startGettingRates() //screen visible, lets make sure our rates are as up to date as possible
    }

    /*Screen invisible*/
    override fun onStop() {
        super.onStop()
        //mainViewModel.stopGettingRates() //screen is not visible, lets save resources
    }

    /**
     * Initializes the view components, sets up listeners, and starts observers for UI updates.
     */
    private fun initView() {
        /* //VALUE TO CONVERT EDIT TEXT
         binding.tiEtPrice.addTextChangedListener(object : TextWatcher {
             override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                 // Not used in this example
             }

             override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                 // Not used in this example
             }

             override fun afterTextChanged(s: Editable?) {
                 val enteredText = s.toString()
                 println("User entered: $enteredText")

                 //lets try to perform the conversion
                 var enteredValue = enteredText.toDoubleOrNull()
                 if (enteredValue == null) {
                     enteredValue = INVALID_PRICE
                 }
                 //we need the currency the user selected as a base for the conversions
                 var selectedCurrency = mainViewModel.selectedCurrency.value
                 selectedCurrency?.let {
                     //after we get this list, our observer will automatically populate the data
                     mainViewModel.setPriceToConvert(enteredValue)
                     mainViewModel.getListOfConversions(
                         enteredValue, selectedCurrency
                     )
                 }


             }
         })
 */
        //SELECTED CURRENCY SPINNER
        /*  binding.spinnerCurrency.onItemSelectedListener =
              object : AdapterView.OnItemSelectedListener {
                  override fun onItemSelected(
                      parent: AdapterView<*>?, view: View?, position: Int, id: Long
                  ) {
                      // Get the selected item from the adapter
                      val selectedItem = parent?.getItemAtPosition(position).toString()
                      mainViewModel.setSelectedCurrency(selectedItem)
                      //println("Selected item: $selectedItem")
                  }

                  override fun onNothingSelected(parent: AdapterView<*>?) {
                      // This is called when nothing is selected
                  }
              }*/

        //recycler view init
        adapter = recyclerViewAdapter()
        binding.rvConversions.layoutManager = LinearLayoutManager(this)
        binding.rvConversions.adapter = adapter

        //start observing data
        Log.d(TAG, "starting observers")
        setupObservers()
        Log.d(TAG, "observers active")

        //get initial data
        mainViewModel.getBeneficiariesAndDisplay()

    }

    class recyclerViewAdapter :
        RecyclerView.Adapter<recyclerViewAdapter.BeneficiaryViewHolder>() {

        /*set up views*/
        // ViewHolder class for the adapter.  This will hold our custom view
        class BeneficiaryViewHolder(val beneficiaryView: BeneficiaryView) : RecyclerView.ViewHolder(beneficiaryView)

        /*inner class ConversionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            internal var nameTextView: TextView
            internal var benefitType: TextView
            internal var designation: TextView

            init {
                nameTextView = itemView.findViewById(R.id.tv_name) as TextView
                benefitType = itemView.findViewById(R.id.tv_benefit_type) as TextView
                designation = itemView.findViewById(R.id.tv_designation) as TextView
            }
        }*/

        override fun getItemViewType(position: Int): Int {
            // depends on your problem
            /*position is the item number.  It will be passed to the oncreateviewlholder in the viewType parameter*/
            return position
        }


        //inflate the layout
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiaryViewHolder  {
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
            /*return ConversionsViewHolder(
                LayoutInflater.from(
                    parent.context
                ).inflate(
                    R.layout.beneficiary_item, parent, false
                )
            )*/
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


        /*this code sets up a mechanism for efficiently handling updates to a list of Currency
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
     * Sets up observers for RecyclerView and Spinner to handle updates in the UI.
     */
    private fun setupObservers() {
        setupRecyclerViewObserver()
    }

    /**
     * Sets up an observer for the RecyclerView to handle updates in the UI based on the converted currency list.
     */
    private fun setupRecyclerViewObserver() {
        //observe liveData from ViewModel
        mainViewModel.beneficiaryList.observe(this, Observer { beneficiaryList ->

            adapter.submitList(beneficiaryList)


            /*  when (currencyResponseResource.status) {
                  Status.SUCCESS -> {
                      binding.progress.visibility = View.GONE
                      binding.rvConversions.visibility = View.VISIBLE

                      currencyResponseResource.data.let { res ->
                          if (res != null) {
                              adapter.submitList(res)
                          }
                      }
                  }

                  Status.LOADING -> {
                      binding.progress.visibility = View.VISIBLE
                      //binding.rvConversions.visibility = View.GONE
                  }

                  Status.ERROR -> {
                      binding.progress.visibility = View.GONE
                      binding.rvConversions.visibility = View.VISIBLE
                      Snackbar.make(binding.rootView, "Something went wrong", Snackbar.LENGTH_SHORT)
                          .show()
                  }
              }*/
        })
    }


}
