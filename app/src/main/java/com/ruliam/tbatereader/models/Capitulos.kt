package com.ruliam.tbatereader.models

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

data class Capitulos(
    var title : String = "",
    var text : String = "",
    var url : String = ""
)
