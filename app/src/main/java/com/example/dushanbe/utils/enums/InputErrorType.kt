package com.example.dushanbe.utils.enums

enum class InputErrorType(val errorType:String="") {
    EMPTY(""),
    MISMATCH("mismatch"),
    INVALID("invalid"),
    VALID("valid"),
    REPEATED("repeated"),
    MIN_AMOUNT("min_amount")
}