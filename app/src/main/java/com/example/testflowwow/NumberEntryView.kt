package com.example.testflowwow

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.Gson
import com.jakewharton.rxbinding4.widget.TextViewTextChangeEvent
import com.jakewharton.rxbinding4.widget.textChangeEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class NumberEntryView(private val mainView: MainActivity) {

    public val numberEditText: EditText = mainView.findViewById(R.id.editTextNumber)
    public val numberHintTextView: TextView = mainView.findViewById(R.id.hintNumberView)
    public val layout: ConstraintLayout = mainView.findViewById(R.id.numberLayout)

    private val codeArray: ArrayList<String>
    private val arrayNumbersData: ArrayList<NumberPOJOItem>

    lateinit var formatWatcher: FormatWatcher
    var maskString = "+_ ___ ___ __ __"
    var code = "7"
    var maskEnable = true





    init {
        val str = mainView.assets.open("phone-codes.json").bufferedReader().use { it.readText() }
        arrayNumbersData = Gson().fromJson(str,NumberPOJO::class.java)
        codeArray = arrayNumbersData.mapTo(ArrayList()){it.getCode()}
        setMask(maskString,code)
        setListeners()
        numberEditText.requestFocus()

        val imm = mainView.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(numberHintTextView, InputMethodManager.SHOW_IMPLICIT)
    }

    fun setMask(mask: String, toAppend: String){
        val slots = UnderscoreDigitSlotsParser().parseSlots(mask)
        formatWatcher = MaskFormatWatcher(MaskImpl.createTerminated(slots))
        formatWatcher.installOn(numberEditText) // install on any TextView
        numberEditText.text.clear()
        numberEditText.append(toAppend)

    }

    fun setHint() {
        if (maskEnable){
            numberHintTextView.text = numberEditText.text.toString() +
                    maskString.map { if(it=='_') '0' else it }
                        .joinToString("")
                        .subSequence(numberEditText.text.length,maskString.length)
        }
    }


    fun setMaskIsEntryInput(textChangeEvent: TextViewTextChangeEvent){
        if (textChangeEvent.text.toString().split(" ")[0] in codeArray && !maskEnable && textChangeEvent.count==1){
            code = textChangeEvent.text.toString().split(" ")[0]
            val indexInArray = codeArray.indexOf(textChangeEvent.text.toString())
            maskString = arrayNumbersData[indexInArray].getNumberMask()
            setMask(maskString,code)
            maskEnable = true
        }
    }

    fun setMaskIsPastInput(textChangeEvent: TextViewTextChangeEvent){
        Log.e("Count",textChangeEvent.count.toString())
        if (textChangeEvent.count>1 && !maskEnable){
            var temp = textChangeEvent.text.toString().replace("+","")
            numberEditText.text.clear()
            numberEditText.append("+")
            for (i in 0..temp.length){
                var a: String ="+"+temp.subSequence(0,i) as String
                if(a in codeArray){
                    code = a
                    val indexInArray = codeArray.indexOf(code)
                    maskString = arrayNumbersData[indexInArray].getNumberMask()
                    setMask(maskString,temp)
                    maskEnable = true
                }
            }



        }
    }



    @SuppressLint("SetTextI18n")
    private fun setListeners(){
        //Слушаем ввод номера
        numberEditText.textChangeEvents()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ it ->
                setMaskIsEntryInput(it)
                setMaskIsPastInput(it)
                setHint()


                if(it.text.isEmpty()){
                    numberEditText.text.append('+')
                    formatWatcher.removeFromTextView()
                    maskEnable = false
                }
                if (it.text.length == 1){
                    formatWatcher.removeFromTextView()
                    maskEnable = false
                    numberHintTextView.text = "+"
                }

                if(it.text.length == maskString.length){
                    mainView.toComeIn()
                }
                Log.e("Text",it.text.toString())
        }
    }
}


