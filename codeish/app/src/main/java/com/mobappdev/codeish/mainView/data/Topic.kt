package com.mobappdev.codeish.mainView.data

import android.content.Intent

data class Topic(var name : String = "",
                        var title : String = "",
                        var description : String ="",
                        var imagePath : String ="",
                        var activityName: Intent? = null)