package com.example.testflowwow

import android.text.InputFilter
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.Gson
import com.jakewharton.rxbinding4.widget.textChangeEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers


class NumberEntryView(private val mainView: MainActivity) {
    public val countryCodeEditText: EditText = mainView.findViewById(R.id.editCountryCode)
    public val numberEditText: EditText = mainView.findViewById(R.id.editTextNumber)
    public val numberHintTextView: TextView = mainView.findViewById(R.id.hintNumberView)
    public val layout: ConstraintLayout = mainView.findViewById(R.id.numberLayout)

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

    //Смена маски в зависимости от региона
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

    //Получаем полный номер
    fun getNumber(): String {
        return countryCodeEditText.text.toString() + numberEditText.text.filter { it!= ' ' }.toString()
    }

    //Проверяем корректность ввода
    fun isValidNumber(fullNumber: String): Boolean {
        return fullNumber.matches("[+0-9]+".toRegex())
    }

    private fun setListeners(){
        //Слушаем ввод номера
        numberEditText.textChangeEvents()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
            setHint(it.text.toString() + mask.subSequence(it.text.length,mask.length))
            when(it.text.length){
                in getSpacesIndexes() -> if (it.count!=0) numberEditText.text.append(" ")
                //Тут по добру возвращаем номер
                mask.length -> {
                    if(countryCodeEditText.text.length>=2 && isValidNumber(getNumber())) mainView.toComeIn()
                }
            }
        }

        //Слушаем поле ввода кода страны
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