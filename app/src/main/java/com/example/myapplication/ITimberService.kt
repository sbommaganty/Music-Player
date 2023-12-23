package com.example.myapplication


import android.os.IBinder
import android.os.IInterface
import android.os.Parcel

interface ITimberService  :IInterface {
    companion object {
         var TRANSACTION_open = android.os.IBinder.FIRST_CALL_TRANSACTION + 0
        var TRANSACTION_play = android.os.IBinder.FIRST_CALL_TRANSACTION + 1
        var DESCRIPTER:String = "com.example.myapplication.ITimberService"
        var TRANSACTION_pause = android.os.IBinder.FIRST_CALL_TRANSACTION + 2
        fun asInterface(obj:android.os.IBinder): ITimberService? {
            if (obj == null) {
                return null
            }
            var iin = obj.queryLocalInterface(ITimberService.DESCRIPTER)
            return stub.Proxy(obj)
        }

    }

  open abstract class stub :ITimberService,android.os.Binder(){



        init {
           this.attachInterface(this, DESCRIPTER)
       }



       override fun asBinder(): IBinder {
           return this
       }



        override fun onTransact(code:Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            var descriptor:String= DESCRIPTER
          when(code){
              TRANSACTION_open->{
                  data.enforceInterface(descriptor)

                  var _arg2: Long
                  _arg2 = data.readLong()

                  this.open( _arg2)
                  reply!!.writeNoException()
                  return true
              }
              TRANSACTION_play->{
                  data.enforceInterface(descriptor)
                  this.play()
                  reply!!.writeNoException()
                  return true
              }
              TRANSACTION_pause->{
                  data.enforceInterface(descriptor)
                  this.pause()
                  reply!!.writeNoException()
                  return true
              }

          }

           return super.onTransact(code, data, reply, flags)
       }
     class Proxy:ITimberService{
           override fun open(_id:Long) {
               val _data = android.os.Parcel.obtain()
               val _reply = android.os.Parcel.obtain()
               try{
                   _data.writeInterfaceToken(ITimberService.DESCRIPTER)
                   _data.writeLong(_id)
                   mRemote.transact(TRANSACTION_open, _data, _reply, 0)
               }
               finally {
                   _reply.recycle();
                   _data.recycle();
               }
           }


           override fun play() {
               val _data = android.os.Parcel.obtain()
               val _reply = android.os.Parcel.obtain()
               try {
                   _data.writeInterfaceToken(ITimberService.DESCRIPTER)
                   mRemote.transact(TRANSACTION_play, _data, _reply, 0)
                   _reply.readException()
               } finally {
                   _reply.recycle()
                   _data.recycle()
               }
           }

         override fun pause() {
             val _data = android.os.Parcel.obtain()
             val _reply = android.os.Parcel.obtain()
             try {
                 _data.writeInterfaceToken(ITimberService.DESCRIPTER)
                 mRemote.transact(TRANSACTION_pause, _data, _reply, 0)
                 _reply.readException()
             } finally {
                 _reply.recycle()
                 _data.recycle()
             }
         }

           var mRemote: android.os.IBinder
           constructor(remote:android.os.IBinder){
               mRemote = remote
           }
           override fun asBinder(): IBinder {
               return mRemote
           }

       }
   }


       fun open(_id:Long)

fun pause()



      fun play()
 }