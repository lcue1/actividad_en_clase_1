package com.example.gestionalumnos

import android.content.Context
import android.content.Intent
import android.os.Bundle

fun goAnotherActivity(context:Context, targetActivity: Class<*>, extras: Bundle? = null) {
    val intent = Intent(context, targetActivity)
    extras?.let { intent.putExtras(it) }
    context.startActivity((intent))
}