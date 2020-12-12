package ru.skillbranch.skillarticles.extensions

fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
    if(this.isNullOrEmpty() || substr.isEmpty()) return listOf()
    val res = mutableListOf<Int>()
    val result = Regex(if(ignoreCase) substr.toLowerCase() else substr).findAll(if(ignoreCase) this.toLowerCase() else this, 0)
    for (i in result) {
        res.add(i.range.first)
    }
    return res
}

fun String.isValidEmail(): Boolean {
    val regex = "^\\S+@\\S+\\.\\S+$".toRegex()
    return regex.matches(this)
}

fun String.isValidPassword(): Boolean {
    val regex = "^[a-zA-Z0-9]{8,}\$".toRegex()
    return regex.matches(this)
}

fun String.isValidName(): Boolean {
    val regex = "^[a-zA-Z0-9_-]{3,}\$".toRegex()
    return regex.matches(this)
}