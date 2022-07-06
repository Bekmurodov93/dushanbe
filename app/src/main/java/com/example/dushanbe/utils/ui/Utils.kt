package com.example.dushanbe.utils.ui



object Utils {
    fun getTypes(): List<Pair<Int, String>> =
        listOf(
            Pair(1, "Дом здоровья"), Pair(1, "Центр здоровья"), Pair(1, "Сельскый центр здоровья"),
            Pair(1, "Ройонный центр здоровья"), Pair(1, "Сельская участковая больница"),
            Pair(1, "Сельская номерная больница"), Pair(1, "Центральная районная больница"),
            Pair(1, "Роддом третьего уровня/перинатальный центр")
        )
    fun countryCodeToFlagEmoji(countryCode: String): String {
        val firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6
        val secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6
        return String(Character.toChars(firstLetter)).plus(String(Character.toChars(secondLetter))).plus(" ")
    }
}