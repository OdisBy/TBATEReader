package com.ruliam.tbatereader.network

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class CapitulosNetwork : Application()
{
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}