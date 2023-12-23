package com.example.myapplication

import android.app.Activity
import android.content.*
import android.content.ContextWrapper
import android.os.IBinder
import java.util.*

class MusicPlayer:Activity(){

    companion object {
        var mConnectionMap: WeakHashMap<Context, ServiceBinder>? = null
        var mService: ITimberService? = null
    }
    fun bindToService(context:Context,callback: ServiceConnection): ServiceToken? {//2
        var realActivity:Context= context
        if (realActivity == null) realActivity = context
        var contextWrapper = ContextWrapper(realActivity)
        contextWrapper.startService(Intent(contextWrapper, MusicService::class.java))
        var binder = ServiceBinder(callback,contextWrapper.applicationContext)
        var fred:Boolean=contextWrapper.bindService(Intent(contextWrapper, MusicService::class.java),binder,0)
        if(fred){
            mConnectionMap?.put(contextWrapper, binder)
            return ServiceToken(contextWrapper)
        }


      return null
    }
    class ServiceBinder(Callback: ServiceConnection?, Context: Context) : ServiceConnection{
         var mCallback: ServiceConnection? = Callback
         var mContext: Context? = Context



        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            mService = ITimberService.asInterface(service)
            mCallback?.onServiceConnected(className, service)

        }

        override fun onServiceDisconnected(className: ComponentName) {
            mCallback?.onServiceDisconnected(className)
            mService = null
        }
    }
    fun playall(context:Context,fwson:Song){
        var id:Long = fwson.id
 mService!!.open(id)
mService!!.play()


    }
    fun play(){
        mService!!.play()
    }
    fun pause(){
        mService!!.pause()
    }

    class ServiceToken{
        var mwrappedcontext: ContextWrapper? = null
        constructor(context:ContextWrapper){
            mwrappedcontext = context
        }

    }
}