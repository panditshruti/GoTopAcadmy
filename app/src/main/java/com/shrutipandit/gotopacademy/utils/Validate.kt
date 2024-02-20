package com.shrutipandit.gotopacademy.utils

import java.util.regex.Pattern

object Validate {

    fun isEmailValid(email:String):Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        val patter = Pattern.compile(emailRegex)
        val matcher =  patter.matcher(email)
        return matcher.matches()
    }

    fun isMobileNumberValid(mobileNo:String) :Boolean{
        val mobileRegex = "^[6-9][0-9]{9}$"
        val pattern = Pattern.compile(mobileRegex)
        val matcher = pattern.matcher(mobileNo)
        return matcher.matches()
    }
}