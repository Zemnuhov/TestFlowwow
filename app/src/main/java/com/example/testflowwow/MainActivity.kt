package com.example.testflowwow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.widget.EditText
import com.jakewharton.rxbinding4.widget.textChangeEvents

class MainActivity : AppCompatActivity() {

    lateinit var numberEntryView: NumberEntryView

    fun initViews(){
        numberEntryView = NumberEntryView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    fun toComeIn(){

    }
}