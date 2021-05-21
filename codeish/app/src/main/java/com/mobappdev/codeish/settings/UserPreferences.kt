package com.mobappdev.codeish.settings

data class UserPreferences(var coins : Int = 0,
                           var customisations : MutableList<String> = ArrayList(),
                           var userid : String? = "")