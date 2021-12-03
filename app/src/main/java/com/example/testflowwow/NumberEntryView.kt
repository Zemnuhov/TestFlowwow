package com.example.testflowwow

import android.content.Context
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.InputFilter
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding4.widget.textChangeEvents
import kotlinx.serialization.json.Json
import java.security.AccessControlContext
import kotlin.text.StringBuilder

class NumberEntryView(private val mainView: MainActivity) {
    private val countryCodeEditText: EditText = mainView.findViewById(R.id.editCountryCode)
    private val numberEditText: EditText = mainView.findViewById(R.id.editTextNumber)
    private val numberHintTextView: TextView = mainView.findViewById(R.id.hintView)
    private val rusNumber = "999 999 99 99"


    init {
        numberEditText.filters = arrayOf(InputFilter.LengthFilter(13))
        numberEditText.textChangeEvents().subscribe{
            countryCodeEditText.selectAll()
            val hintText = it.text.toString() + rusNumber.subSequence(it.text.length,rusNumber.length)
            numberHintTextView.text = hintText
            when(it.text.length){
                3,7,10 -> if (it.count!=0) numberEditText.text.append(" ")
            }

        }
    }


}