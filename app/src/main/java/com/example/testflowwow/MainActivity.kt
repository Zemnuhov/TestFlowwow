package com.example.testflowwow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.widget.EditText
import com.jakewharton.rxbinding4.widget.textChangeEvents

class MainActivity : AppCompatActivity() {
    lateinit var countryCodeEditText: EditText
    lateinit var number: EditText

    fun initViews(){
        countryCodeEditText = findViewById(R.id.editCountryCode)
        number = findViewById(R.id.editTextNumber)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        number.textChangeEvents().subscribe{
            PhoneNumberFormattingTextWatcher(number.text.toString())
        }
    }
}