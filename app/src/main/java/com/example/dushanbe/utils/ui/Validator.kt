package com.example.dushanbe.utils.ui

import androidx.core.text.isDigitsOnly
import com.example.dushanbe.utils.Constants.dateRegex
import com.example.dushanbe.utils.enums.InputErrorType

object Validator {
    fun validateTextFields(fieldName: String, txt: String?): Pair<String, InputErrorType> {
        return when {
            txt.isNullOrEmpty() -> Pair(fieldName, InputErrorType.EMPTY)
            fieldName.contains("date") -> if (txt.matches(dateRegex.toRegex())) {
                Pair(fieldName, InputErrorType.VALID)
            } else {
                Pair(fieldName, InputErrorType.MISMATCH)
            }
            txt.length > 3 -> Pair(fieldName, InputErrorType.VALID)
            else -> Pair(fieldName, InputErrorType.INVALID)
        }
    }

    fun validateDigitField(fieldName: String, txt: String?): Pair<String, InputErrorType> {
        return when {
            txt.isNullOrEmpty() -> Pair(fieldName, InputErrorType.EMPTY)
            txt.isDigitsOnly() -> {
                Pair(fieldName, InputErrorType.VALID)
            }
            else -> Pair(fieldName, InputErrorType.INVALID)
        }
    }
}