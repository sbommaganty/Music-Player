package com.example.myapplication

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.card_item.view.*

class BlankFragment : Fragment() {
    val SONG_SORT_ORDER: String = "song_sort_order"
    val SONG_A_Z: String = MediaStore.Audio.Media.DEFAULT_SORT_ORDER
    lateinit var cycler: RecyclerView

    lateinit var allsongs: ArrayList<Song>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_blank, container, false)
        istrue = true
        isnotrue = true
        g = false
        allsongs = loaddata()
        cycler = rootView.findViewById(R.id.cycler) as RecyclerView
        cycler.layoutManager = LinearLayoutManager(activity)
        cycler.adapter = CountryAdapter(allsongs, activity)

        return rootView
    }

    fun loaddata(): ArrayList<Song> {

        var newcur: Cursor? = makesongCursor()
        var rrayList: ArrayList<Song> = ArrayList()
        if (newcur != null && newcur.moveToFirst())
            do {
                val id = newcur.getLong(0)
                val title = newcur.getString(1)
                val artist = newcur.getString(2)
                val album = newcur.getString(3)
                val duration = newcur.getInt(4)
                val trackNumber = newcur.getInt(5)
                val artistId = newcur.getInt(6).toLong()
                val albumId = newcur.getLong(7)

                rrayList.add(Song(id, albumId, artistId, title, artist, album, duration, trackNumber))
            } while (newcur.moveToNext())
        if (newcur != null)
            newcur.close()
        return rrayList

    }

    fun makesongCursor(): Cursor? {
        var mPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        var songsortorder: String? = mPreferences.getString(SONG_SORT_ORDER, SONG_A_Z)
        var selectionStatement = "is_music=1 AND title != ''"

        return context!!.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf("_id", "title", "artist", "album", "duration", "track", "artist_id", "album_id"),
            selectionStatement,
            null,
            songsortorder
        )
    }


    class CountryAdapter(items: List<Song>, ctx: FragmentActivity?) :
        RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

        var list = items

        var cts: FragmentActivity? = ctx
        lateinit var ju: ViewHolder
          lateinit var xu: ViewHolder
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {


            return ViewHolder(LayoutInflater.from(cts).inflate(R.layout.card_item, p0, false))

        }

        override fun getItemCount(): Int {

            return list.size

        }

        override fun onBindViewHolder(p0: ViewHolder, position: Int) {
            var uri: Uri = ContentUris.withAppendedId(sArtworkUri, list.get(position).albumId)
            Glide.with(cts).asBitmap().load(Uri.parse(uri.toString())).into(object : BitmapImageViewTarget(p0.img1) {
                override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                    super.onResourceReady(resource, transition)

                }
            })

var handone = Handler()

            handone.post({ p0.bu.setOnClickListener(object:View.OnClickListener{
                override fun onClick(v: View?) {

                    if(isnotrue){
                        isnotrue = false
                        MusicPlayer().pause()
                    }
                    else{
                        isnotrue = true
                        MusicPlayer().play()
                    }
                }

            })
            })


            var totalsize: Int = list.size

var handtwo = Handler()
            handtwo.post({p0.img1.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View?) {

                    if(istrue) {
                        if(g){
                           xu.sek.visibility = View.GONE
                            xu.bu.visibility = View.GONE
                            xu.img2.visibility = View.GONE
                        }
                        ju = take(p0)


                        g= true

                        istrue = false
                    }
                    else{
                        if(g){
                            ju.sek.visibility = View.GONE
                            ju.bu.visibility = View.GONE
                            ju.img2.visibility = View.GONE
                        }
                        xu =  take(p0)




                        istrue = true

                    }
                    var fer: Song = list.get(position)
                    var handler = Handler()
                    handler.postDelayed({

                   FlankFragment().nrsonga(fer)
                        MusicPlayer().playall(cts as AppCompatActivity, fer)


                    }, 100)


                }

            })  })



        }


         class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

            var img1 = itemView.findViewById<ImageView>(R.id.card_background)
            var img2 = itemView.findViewById<ImageView>(R.id.imageView)
            var bu = itemView.findViewById<Button>(R.id.button1)
            var text = itemView.findViewById<TextView>(R.id.button1)
            var sek:SeekBar = itemView.findViewById<SeekBar>(R.id.seekBar2)

        }





    }
    companion object {
        var sArtworkUri: Uri = Uri.parse("content://media/external/audio/albumart")


        var istrue: Boolean = true
        var g:Boolean = false
        var isnotrue = true



        fun take(iu: CountryAdapter.ViewHolder): CountryAdapter.ViewHolder {
            iu.sek.visibility = View.VISIBLE
            iu.img2.visibility = View.VISIBLE
            iu.bu.visibility = View.VISIBLE
            return iu
        }
    }
}
