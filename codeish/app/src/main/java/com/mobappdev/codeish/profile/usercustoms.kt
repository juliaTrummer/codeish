package com.mobappdev.codeish.profile

data class usercustoms(
    var coins: Int = 0,
    var customisations: MutableList<String>? = null,
    var userid: String ="")
