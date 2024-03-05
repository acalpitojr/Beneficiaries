package com.calpito.beneficiaries.utils

import java.text.SimpleDateFormat
import java.util.Locale

object Utilities {

    /**
     * Converts a birthday string from "MMddyyyy" format to "MM/dd/yyyy" format.
     *
     * This function takes a birthday string in the format of "MMddyyyy" and attempts to convert it
     * into a more user-friendly format of "MM/dd/yyyy". If the parsing or formatting process fails
     * due to an incorrect input format or any other reason, an empty string is returned.
     *
     * Example:
     * - Input: "04201979"
     * - Output: "04/20/1979"
     *
     * @param birthday The birthday string in "MMddyyyy" format to be converted.
     * @return The birthday string in "MM/dd/yyyy" format if successful; otherwise, an empty string.
     */
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

    /**
     * Interprets a designation code and returns its descriptive meaning.
     *
     * This function translates specific designation codes into their full textual descriptions
     * for better readability. It supports a predefined set of codes, returning an empty string
     * for any codes that are not explicitly handled.
     *
     * Supported codes and their interpretations:
     * - "P": Primary
     * - "C": Contingent
     *
     * @param code The designation code to be interpreted.
     * @return The descriptive meaning of the code if recognized; otherwise, an empty string.
     */
    fun interpretDesignationCode(code: String): String {
        var result = ""
        when (code) {
            "P" -> {
                result = "Primary"
            }

            "C" -> {
                result = "Contingent"
            }

            else -> {
                result = ""
            }
        }
        return result
    }
}