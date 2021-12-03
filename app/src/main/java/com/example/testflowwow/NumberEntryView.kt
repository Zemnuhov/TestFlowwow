package com.example.testflowwow

import android.text.InputFilter
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.jakewharton.rxbinding4.widget.textChangeEvents

class NumberEntryView(private val mainView: MainActivity) {
    private val countryCodeEditText: EditText = mainView.findViewById(R.id.editCountryCode)

    private val numberEditText: EditText = mainView.findViewById(R.id.editTextNumber)
    private val numberHintTextView: TextView = mainView.findViewById(R.id.hintNumberView)

    private var mask = "000 000 00 00"
    private val codeArray: ArrayList<String>
    private val arrayNumbersData: ArrayList<NumberPOJOItem>


    init {
        numberEditText.filters = arrayOf(InputFilter.LengthFilter(13))
        countryCodeEditText.filters = arrayOf(InputFilter.LengthFilter(4))

        val str = mainView.assets.open("phone-codes.json").bufferedReader().use { it.readText() }
        arrayNumbersData = Gson().fromJson(str,NumberPOJO::class.java)
        codeArray = arrayNumbersData.mapTo(ArrayList()){it.getCode()}
        setListeners()
    }

    private fun setHint(text: String){
        numberHintTextView.text = text
    }

    private fun changeRegion(){
        mask = arrayNumbersData[codeArray.indexOf(countryCodeEditText.text.toString())].getNumberMask()
        numberEditText.filters = arrayOf(InputFilter.LengthFilter(mask.length))
        numberEditText.text.clear()
        numberHintTextView.text = mask
    }

    private fun getSpacesIndexes(): ArrayList<Int>{
        val spaces: ArrayList<Int> = arrayListOf()
        mask.forEachIndexed{index, c ->
            if (c == ' '){
                spaces+=index
            }
        }
        return spaces
    }

    private fun setListeners(){
        numberEditText.textChangeEvents().subscribe{
            setHint(it.text.toString() + mask.subSequence(it.text.length,mask.length))
            when(it.text.length){
                in getSpacesIndexes() -> if (it.count!=0) numberEditText.text.append(" ")
                mask.length -> mainView.toComeIn()
            }
        }

        countryCodeEditText.textChangeEvents().subscribe{
            if(it.text.toString() in codeArray){
                changeRegion()
            }
            if(it.text.isEmpty()){
                countryCodeEditText.text.append("+")
            }
            if(it.text.length>2 && it.count!=0){
                countryCodeEditText.width = countryCodeEditText.width+30
            }
            if(it.text.length>=2 && it.before==1){
                countryCodeEditText.width = countryCodeEditText.width-30
            }
        }
    }


}