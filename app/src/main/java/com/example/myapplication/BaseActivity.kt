package com.example.myapplication

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder

open class BaseActivity:ATe(), ServiceConnection {
    var mService: ITimberService? = null
    private var mToken: MusicPlayer.ServiceToken? = null
    override fun onServiceDisconnected(name: ComponentName?) {
        mService = null
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder) {

        mService =  ITimberService.asInterface(service)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mToken = MusicPlayer().bindToService(this, this)
    }
    override fun onResume() {


        //For Android 8.0+: service may get destroyed if in background too long
        if (mService == null) {
            mToken = MusicPlayer().bindToService(this, this)
        }

        super.onResume()
    }

}