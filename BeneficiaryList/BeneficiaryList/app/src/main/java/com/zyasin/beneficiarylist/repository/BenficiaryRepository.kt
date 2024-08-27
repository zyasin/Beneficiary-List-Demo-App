package com.zyasin.beneficiarylist.repository

import android.content.Context
import com.zyasin.beneficiarylist.model.Address
import com.zyasin.beneficiarylist.model.Beneficiary
import org.json.JSONArray

// Repository class for loading and parsing beneficiary data from a JSON file in the assets folder.
class BeneficiaryRepository(private val context: Context) {

    fun getBeneficiaries(): List<Beneficiary> {
        val jsonString = loadJsonFromAssets("beneficiaries.json")
        return parseJson(jsonString)
    }

    private fun loadJsonFromAssets(fileName: String): String? {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    private fun parseJson(jsonString: String?): List<Beneficiary> {
        val beneficiaries = mutableListOf<Beneficiary>()
        if (jsonString != null) {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val addressObject = jsonObject.getJSONObject("beneficiaryAddress")
                val address = Address(
                    addressObject.getString("firstLineMailing"),
                    addressObject.optString("scndLineMailing", null),
                    addressObject.getString("city"),
                    addressObject.getString("zipCode"),
                    addressObject.getString("stateCode"),
                    addressObject.getString("country")
                )
                val beneficiary = Beneficiary(
                    jsonObject.getString("firstName"),
                    jsonObject.getString("middleName"),
                    jsonObject.getString("lastName"),
                    jsonObject.getString("designationCode"),
                    jsonObject.getString("beneType"),
                    jsonObject.getString("socialSecurityNumber"),
                    jsonObject.getString("dateOfBirth"),
                    jsonObject.getString("phoneNumber"),
                    address
                )
                beneficiaries.add(beneficiary)
            }
        }
        return beneficiaries
    }
}