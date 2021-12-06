package com.example.testflowwow

import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.marginStart
import androidx.core.view.marginTop

class AnimationView(val height:Float,var width:Float) {


//    fun numberAnimation(view: NumberEntryView){
//        view.numberHintTextView.visibility = View.GONE
//        ViewCompat.animate(view.layout)
//            .y(height/2-view.layout.marginTop)
//            .x(width/2-view.layout.width/2+view.layout.marginStart)
//            .setDuration(2500)
//
//            .withEndAction{
//                ViewCompat.animate(view.layout)
//                    .scaleX(view.countryCodeEditText.scaleX*1.2F)
//                    .scaleY(view.countryCodeEditText.scaleY*1.2F)
//                    .setDuration(1000)
//
//            }
//        view.countryCodeEditText.isCursorVisible = false
//        view.countryCodeEditText.keyListener = null
//
//        view.numberEditText.isCursorVisible = false
//        view.numberEditText.keyListener = null
//
//    }

    fun hintTextViewAnimation(view: View){

        ViewCompat.animate(view)
            .alpha(0F)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(1000)
            .withEndAction {
                view.visibility = View.GONE
            }
    }

    fun inputTextViewAnimation(inputView: View, waitView:View, waitImageView: View){
        ViewCompat.animate(inputView).alpha(0F)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(500)
            .withEndAction{
                waitView.visibility = View.VISIBLE
                ViewCompat.animate(waitView)
                    .alpha(1F)
                    .withEndAction {
                        ViewCompat.animate(waitView)
                            .x(width / 2 - waitView.width/2)
                            .y(height / 3)
                            .setInterpolator(AccelerateDecelerateInterpolator())
                            .setDuration(2000)
                    }
            }
        waitImageView.setBackgroundResource(R.drawable.wait_animation)
        val a: AnimationDrawable = waitImageView.background as AnimationDrawable
        a.start()
    }
}