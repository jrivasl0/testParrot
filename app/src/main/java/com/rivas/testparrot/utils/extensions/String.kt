package com.rivas.testparrot.utils.extensions

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.util.regex.Pattern

internal fun String.toJson(): JsonObject = JsonParser()
    .parse(this)
    .asJsonObject

internal fun String.isJson() = try {
    val gson = Gson()
    gson.fromJson(this, Any::class.java)
    true
} catch (ex: com.google.gson.JsonSyntaxException) {
    false
}

internal fun String.toApiException() =
    Exception(this)

val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)
internal fun String.isValidEmail() : Boolean = EMAIL_ADDRESS_PATTERN.matcher(this).matches();

