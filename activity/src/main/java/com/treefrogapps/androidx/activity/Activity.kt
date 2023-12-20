package com.treefrogapps.androidx.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import java.io.Serializable


inline fun <reified T : Activity, V : Any> Context.launchActivity(extra: V? = null, builder: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply {
        builder(this)
        putDefaultExtra<T, V>(extra)
    })
}

inline fun <reified T : Activity> Context.launchActivity(builder: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(builder))
}

inline fun <reified T : Activity, V : Any> Intent.putDefaultExtra(extra: V?) {
    val key = T::class.java.name
    when (extra) {
        is String -> putExtra(key, extra)
        is Int -> putExtra(key, extra)
        is Float -> putExtra(key, extra)
        is Long -> putExtra(key, extra)
        is Double -> putExtra(key, extra)
        is Parcelable -> putExtra(key, extra)
    }
}

@Suppress("DEPRECATION", "UNCHECKED_CAST")
fun <V : Any> Activity.extractExtra(default: V, key: String): V =
    with(intent) {
        when (default) {
            is String -> getStringExtra(key)
            is Int -> getIntExtra(key, Int.MIN_VALUE)
            is Float -> getFloatExtra(key, Float.MIN_VALUE)
            is Long -> getLongExtra(key, Long.MIN_VALUE)
            is Double -> getDoubleExtra(key, Double.MIN_VALUE)
            is Parcelable -> getParcelableExtra(key)
            is Serializable -> getSerializableExtra(key)
            else -> throw IllegalArgumentException("Unknown extra type ${default::class}")
        } as? V ?: default
    }

fun <V : Any> Activity.extractDefaultExtra(default: V): V =
    extractExtra(default = default, key = this@extractDefaultExtra::class.java.name)