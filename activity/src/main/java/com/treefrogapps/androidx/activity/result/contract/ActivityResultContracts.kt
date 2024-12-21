package com.treefrogapps.androidx.activity.result.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

object ActivityResultContracts {

    abstract class UnitInputResultContract<O> internal constructor() : ActivityResultContract<Unit, O>() {

        final override fun createIntent(context: Context, input: Unit): Intent = createIntent(context = context)

        final override fun getSynchronousResult(context: Context, input: Unit): SynchronousResult<O>? = getSynchronousResult(context = context)

        abstract fun createIntent(context: Context): Intent

        protected open fun getSynchronousResult(context: Context): SynchronousResult<O>? = null
    }

    open class GetTypeContent(private val mimeType: String) : UnitInputResultContract<Uri?>() {

        override fun createIntent(context: Context): Intent =
            Intent(Intent.ACTION_GET_CONTENT)
                .addCategory(Intent.CATEGORY_OPENABLE)
                .setType(mimeType)

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? =
            intent.takeIf { resultCode == Activity.RESULT_OK }?.data
    }

    class GetImageContent : GetTypeContent(mimeType = "image/*")

    class GetVideoContent : GetTypeContent(mimeType = "video/*")
}



