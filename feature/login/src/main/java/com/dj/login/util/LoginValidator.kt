package com.dj.login.util

import java.util.regex.Pattern

class LoginValidator {
    /*
        Contains:
            username
            domain
            2 character domain
            country domain
    */
    fun isEmailValid(email: String) =
        Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
                    ")+"
        ).matcher(email).matches()

    /*
        At least 1 alphabet
        At least 1 digit
        No space
        At least 1 special characters e.g. @$!%*#?&^_-
        Minimum 8 characters long
    */
    fun isPasswordValid(password: String) = Pattern.compile(
        "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])([a-zA-Z0-9@$!%*?&]{8,})"
    ).matcher(password).matches()
}