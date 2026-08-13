package com.nityam.attendancesystem.presentation.components

// Nityam Tiwari - NT
fun avatarInitials(firstName: String?, lastName: String?): String {
    val first = firstName?.firstOrNull()?.uppercaseChar()
    val last = lastName?.firstOrNull()?.uppercaseChar()
    val combined = listOfNotNull(first, last).joinToString("")
    return combined.ifBlank { "?" }
}