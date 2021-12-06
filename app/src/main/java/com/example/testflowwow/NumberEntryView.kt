package com.example.testflowwow

import android.text.InputFilter
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.Gson
import com.jakewharton.rxbinding4.widget.textChangeEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers


class NumberEntryView(private val mainView: MainActivity) {

    public val numberEditText: EditText = mainView.findViewById(R.id.editTextNumber)
    public val numberHintTextView: TextView = mainView.findViewById(R.id.hintNumberView)
    public val layout: ConstraintLayout = mainView.findViewById(R.id.numberLayout)

    private var mask = "+7 000 000 00 00"
    private val codeArray: ArrayList<String>
    private val arrayNumbersData: ArrayList<NumberPOJOItem>

    val maskArray = arrayListOf('+','7',' ','#','#','#',' ','#','#','#',' ','#','#',' ','#','#')





    init {
        numberEditText.filters = arrayOf(InputFilter.LengthFilter(13))

        val str = mainView.assets.open("phone-codes.json").bufferedReader().use { it.readText() }
        arrayNumbersData = Gson().fromJson(str,NumberPOJO::class.java)
        codeArray = arrayNumbersData.mapTo(ArrayList()){it.getCode()}
        setListeners()
    }



    private fun setListeners(){
        //Слушаем ввод номера
        numberEditText.textChangeEvents()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ it ->
                var j=0
                for(i in 1..it.text.length){
                    if(maskArray[i]!=' '){
                        maskArray[i] = it.text[j]
                        j++
                    }
                }
                numberEditText.setText(maskArray.filter { it!='#' }.joinToString(""))
                var s = maskArray.map { if (it == '#') '0' else it }.joinToString("")
                numberHintTextView.text = s
                if(it.text.isEmpty()){
                    numberEditText.text.append('+')
                }


        }


    }


}