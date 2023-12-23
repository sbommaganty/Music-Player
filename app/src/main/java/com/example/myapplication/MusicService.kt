package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.os.RemoteException
import android.provider.MediaStore
import java.lang.ref.WeakReference

class MusicService:Service() {
    lateinit var path: String
    lateinit var mPlayer: MultiPlayer
    var mcursor: Cursor? = null
    var PROJECTION = arrayOf(
        "audio._id AS _id",
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.DATA,
        MediaStore.Audio.Media.MIME_TYPE,
        MediaStore.Audio.Media.ALBUM_ID,
        MediaStore.Audio.Media.ARTIST_ID
    )
    var mBinder: IBinder = ServiceStub(this)
    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        mPlayer = MultiPlayer(this)
    }

    fun open(id: Long) {
        closecursor()
        if (mPlayer.isInitialized())
            mPlayer.stop()
        var selection: String = "_id=" + id

        var uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        mcursor = contentResolver.query(uri, PROJECTION, selection, null, null)
        if (!mcursor!!.moveToFirst()) {
            mcursor!!.close()
        }

        if (mcursor != null) {
            path = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString() + "/" + mcursor!!.getLong(0)
        }

        var sath: String = path

        mPlayer.setDatasource(path)
    }

    fun closecursor() {
        if (mcursor != null) {
            mcursor!!.close()
            mcursor = null
        }
    }

    fun playthesong() {
        mPlayer.play()
    }
    fun pause(){
        mPlayer.pause()
    }

    class MultiPlayer {
        lateinit var MmediaPlayer: MediaPlayer
        var istrue: Boolean = false
        var mIsInitialized: Boolean = false
        lateinit var mService: WeakReference<MusicService>

        constructor(service: MusicService) {
            mService = WeakReference(service)

        }

        fun isInitialized(): Boolean {
            return mIsInitialized
        }

        fun stop() {
            MmediaPlayer.reset()
            mIsInitialized = false
        }


        fun setDatasource(path: String) {


            MmediaPlayer = MediaPlayer.create(mService.get(), Uri.parse(path))


        }
fun pause(){
    MmediaPlayer.pause()
}

        fun play() {
            MmediaPlayer.start()
            mIsInitialized = true
        }

    }

    class ServiceStub() : ITimberService.stub() {


        var mService: WeakReference<MusicService>? = null

        constructor(service: MusicService) : this() {
            mService = WeakReference<MusicService>(service)
        }

        override fun open(_id: Long) {
            mService!!.get()!!.open(_id)
        }

        override fun play() {
            mService!!.get()!!.playthesong()
        }

        override fun pause() {
            mService!!.get()!!.pause()
        }



    }

}

