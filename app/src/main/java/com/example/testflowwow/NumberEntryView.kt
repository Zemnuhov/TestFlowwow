package com.example.testflowwow

import android.content.Context
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.InputFilter
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding4.widget.textChangeEvents
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
            var s: String = rusNumber.replace("9","5")

            numberHintTextView.text = s
            when(it.text.length){
                3,7,10 -> if (it.count!=0) numberEditText.text.append(" ")
            }

        }
    }


}