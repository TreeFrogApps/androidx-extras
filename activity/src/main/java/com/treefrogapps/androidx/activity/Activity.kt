package com.treefrogapps.androidx.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable


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
        is String     -> putExtra(key, extra)
        is Int        -> putExtra(key, extra)
        is Float      -> putExtra(key, extra)
        is Long       -> putExtra(key, extra)
        is Double     -> putExtra(key, extra)
        is Parcelable -> putExtra(key, extra)
    }
}

@Suppress("DEPRECATION")
inline fun <reified V : Any> Activity.extractExtra(default: V, key: String): V =
    with(intent) {
        when (V::class) {
            String::class     -> getStringExtra(key)
            Int::class        -> getIntExtra(key, Int.MIN_VALUE)
            Float::class      -> getFloatExtra(key, Float.MIN_VALUE)
            Long::class       -> getLongExtra(key, Long.MIN_VALUE)
            Double::class     -> getDoubleExtra(key, Double.MIN_VALUE)
            Parcelable::class -> getParcelableExtra(key)
            else              -> throw IllegalArgumentException("Unknown extra type ${V::class}")
        } as? V ?: default
    }

inline fun <reified V : Any> Activity.extractDefaultExtra(default: V): V =
    extractExtra(default = default, key = this@extractDefaultExtra::class.java.name)