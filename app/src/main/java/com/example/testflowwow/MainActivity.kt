package com.example.testflowwow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import com.jakewharton.rxbinding4.widget.textChangeEvents
import android.R.anim
import android.animation.ObjectAnimator
import android.graphics.Insets
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.AnimationDrawable
import android.opengl.Visibility
import android.os.Build
import android.util.DisplayMetrics
import android.util.Size
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import android.view.WindowInsets

import android.view.WindowMetrics
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.*
import androidx.core.view.ViewCompat


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

    }







}