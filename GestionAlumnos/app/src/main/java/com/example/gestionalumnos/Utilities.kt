package com.example.gestionalumnos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat

fun goAnotherActivity(context:Context, targetActivity: Class<*>, extras: Bundle? = null) {
    val intent = Intent(context, targetActivity)
    extras?.let { intent.putExtras(it) }
    context.startActivity((intent))
}

fun createButton(context:Context,textBtn:String, bgButton:Int): Button {
    return Button(context).apply {
        text = textBtn
        textSize = 18f
        setPadding(10, 16, 10, 16)
        setBackgroundColor(bgButton)
        setTextColor(ContextCompat.getColor(context, R.color.text_color_1))
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(100, 16, 100, 16) // Optional: Add margins for spacing
        }
    }
}