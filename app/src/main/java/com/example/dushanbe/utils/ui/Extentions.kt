package com.example.dushanbe.utils.ui

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.annotation.CheckResult
import androidx.core.view.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.dushanbe.R
import com.example.dushanbe.utils.enums.InputErrorType
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun View.applyKeyboardInset(isSubtract: Boolean = false) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val posBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
        val pos = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
        val status = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top

        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
//            updateMargins(bottom=posBottom)
            if (isSubtract) updateMargins(bottom = -(posBottom), top = 0)
            else updatePadding(bottom = (posBottom - pos - 40), top = status)

        }

        if (view is ViewGroup) {
            for (i in 0 until view.size) {
                ViewCompat.dispatchApplyWindowInsets(view[i], insets)
            }
        }
        insets
    }
}


fun <T> LiveData<T>.debounce(duration: Long = 1000L, coroutineScope: CoroutineScope) =
    MediatorLiveData<T>().also { mld ->

        val source = this
        var job: Job? = null

        mld.addSource(source) {
            job?.cancel()
            job = coroutineScope.launch {
                delay(duration)
                mld.value = source.value
            }
        }
    }

fun AutoCompleteTextView.validate(context: Context, type: InputErrorType, view: TextView? = null) {
    when (type) {
        InputErrorType.EMPTY -> {
//            this.setBackgroundResource(R.drawable.input_disabled)
//            view?.text = context.getText(R.string.error_empty)
        }
        InputErrorType.VALID -> {
            this.setBackgroundResource(R.drawable.input)
            view?.text = ""
        }
        InputErrorType.INVALID -> {
            this.setBackgroundResource(R.drawable.input_disabled)
            view?.text = context.getText(R.string.error_fill_correctly)
        }
        else -> {}
    }
}

fun AutoCompleteTextView.reset() {
    this.setBackgroundResource(R.drawable.input)
}

fun TextInputLayout.reset() {
    this.error = ""
    this.isErrorEnabled = false
}

fun TextInputLayout.validate(context: Context, type: InputErrorType) {
    when (type) {
        InputErrorType.EMPTY -> {
            this.isErrorEnabled = true
            this.error = context.getText(R.string.error_empty)
        }
        InputErrorType.VALID -> {
            this.isErrorEnabled = false
            this.error = ""
        }
        InputErrorType.REPEATED -> {
            this.isErrorEnabled = true
            this.error = context.getText(R.string.mismatch)
        }
        InputErrorType.MISMATCH -> {
            this.isErrorEnabled = true
            this.error = context.getText(R.string.mismatch)
        }
        InputErrorType.INVALID -> {
            this.isErrorEnabled = true
            this.error = context.getText(R.string.error_fill_correctly)
        }
        else -> {}
    }
}

fun TextInputEditText.reset(){
    this.setBackgroundResource(R.drawable.input)
}

fun TextInputEditText.validate(context: Context, type: InputErrorType, view: TextView? = null) {
    when (type) {
        InputErrorType.EMPTY -> {
            this.setBackgroundResource(R.drawable.input_disabled)
            view?.text = context.getText(R.string.error_empty)
        }
        InputErrorType.VALID -> {
            this.setBackgroundResource(R.drawable.input)
            view?.text = ""
        }
        InputErrorType.REPEATED -> {
            this.setBackgroundResource(R.drawable.input_disabled)
            view?.text = context.getText(R.string.error_fill_correctly)
        }
        InputErrorType.INVALID -> {
            this.setBackgroundResource(R.drawable.input_disabled)
            view?.text = context.getText(R.string.error_fill_correctly)
        }
        InputErrorType.MISMATCH -> {
            this.setBackgroundResource(R.drawable.input_disabled)
            view?.text = context.getText(R.string.error_fill_correctly)
        }
        else -> {}
    }
}

@ExperimentalCoroutinesApi
@CheckResult
fun TextInputEditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}

@SuppressLint("SimpleDateFormat")
fun Long.toDate(): String {
    val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    utc.timeInMillis = this
    val format = SimpleDateFormat("dd.MM.yyyy")
    return format.format(utc.time)
}

fun String.normalize(): String {
    val date = this.split(".")
    if (date.size == 3) {
        return date[2] + "-" + date[1] + "-" + date[0]
    }
    return this
}
fun String.deNormalize(): String {
    val date = this.split("-")
    if (date.size == 3) {
        return date[2] + "." + date[1] + "." + date[0]
    }
    return this
}