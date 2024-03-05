package com.calpito.beneficiaries.utils

import java.text.SimpleDateFormat
import java.util.Locale

object Utilities {
    fun convertBirthdayFormat(birthday: String): String {
        var result = ""
        // Define the original format and the desired format
        val originalFormat = SimpleDateFormat("MMddyyyy", Locale.US)
        val desiredFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)

        try {
            // Parse the original birthday string
            val date = originalFormat.parse(birthday)
            result = desiredFormat.format(date)
        } catch (e:Exception){
            result = ""
        }


        // Return the formatted birthday string in the desired format
        return result
    }
}