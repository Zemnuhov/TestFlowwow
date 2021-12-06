package com.example.testflowwow

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var numberEntryView: NumberEntryView
    lateinit var inputTextView: TextView
    lateinit var hintTextView: TextView
    lateinit var waitTextView: LinearLayout
    lateinit var waitImageView: ImageView
    lateinit var animation: AnimationView


    fun initViews(){
        numberEntryView = NumberEntryView(this)
        inputTextView = findViewById(R.id.textView)
        hintTextView = findViewById(R.id.textView2)
        waitTextView = findViewById(R.id.waitLayout)
        waitImageView = findViewById(R.id.imageWait)


    }
    fun init(){
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        animation = AnimationView(metrics.heightPixels.toFloat(),metrics.widthPixels.toFloat())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initViews()
    }


    fun toComeIn(){
        animation.inputTextViewAnimation(inputTextView,waitTextView,waitImageView)
        animation.hintTextViewAnimation(hintTextView)
        animation.numberAnimation(numberEntryView)
        val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0);


    }







}