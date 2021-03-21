package com.mobappdev.codeish.mainView.data

import android.content.Intent
import android.widget.ImageView

data class Topic(var name: String = "",
                 var title: String = "",
                 var description: String ="",
                 var imagePath: Int = 0,
                 var activityName: Intent = Intent())