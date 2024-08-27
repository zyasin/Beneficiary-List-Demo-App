package com.zyasin.beneficiarylist.model

import android.os.Parcel
import android.os.Parcelable

// Data class representing a beneficiary with Parcelable implementation for passing between components.
data class Beneficiary(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val designationCode: String,
    val beneType: String,
    val socialSecurityNumber: String,
    val dateOfBirth: String,
    val phoneNumber: String,
    val beneficiaryAddress: Address
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(Address::class.java.classLoader) ?: Address("", "", "", "", "", "")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(middleName)
        parcel.writeString(lastName)
        parcel.writeString(designationCode)
        parcel.writeString(beneType)
        parcel.writeString(socialSecurityNumber)
        parcel.writeString(dateOfBirth)
        parcel.writeString(phoneNumber)
        parcel.writeParcelable(beneficiaryAddress, flags)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Beneficiary> {
        override fun createFromParcel(parcel: Parcel): Beneficiary = Beneficiary(parcel)
        override fun newArray(size: Int): Array<Beneficiary?> = arrayOfNulls(size)
    }

    fun getDesignation(): String {
        return when (designationCode) {
            "P" -> "Primary"
            "C" -> "Contingent"
            else -> "Unknown"
        }
    }
}
