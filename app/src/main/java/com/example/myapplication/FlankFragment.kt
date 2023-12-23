package com.example.myapplication

import android.content.ContentUris
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import java.util.*;
import java.util.LinkedList
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import org.w3c.dom.Node
import java.util.*
import kotlin.collections.ArrayList

class FlankFragment: Fragment() {
    lateinit var cycle: RecyclerView
    companion object {
        var head:Node? = null
        var sArtworkUri: Uri = Uri.parse("content://media/external/audio/albumart")

        var istru: Boolean = true
        var gr:Boolean = false
        var isnotru = true

        fun take(iu: CountryAdapter.ViewHolder): CountryAdapter.ViewHolder {
            iu.sek.visibility = View.VISIBLE
            iu.img2.visibility = View.VISIBLE
            iu.bu.visibility = View.VISIBLE
            return iu
        }

    }
    var newNode:Node? = null

lateinit var llsongs:ArrayList<Song>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    class Node(data:Song){
        var dat = data
        var next:Node? = null


    }

fun insertAfter(data:Song) {
    if (head == null) {
        newNode = Node(data)
         head = newNode
    } else {
        newNode = Node(data)
        newNode!!.next = head
        head = newNode
    }
}
fun nrsonga(cad:Song){
    insertAfter(cad)

}
fun load():ArrayList<Song>{
    var temp = head
    var rayList: ArrayList<Song> = ArrayList()
 if(temp?.next == null){
     rayList.add(temp!!.dat)
 }
    while(temp?.next!=null){
       rayList.add(temp.dat)
        temp = temp.next
    }
  return rayList
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.flank_fragment, container, false)

        cycle = rootView.findViewById(R.id.cycler1) as RecyclerView
        llsongs =  load()
        cycle.layoutManager = LinearLayoutManager(activity)
        cycle.adapter = CountryAdapter(llsongs, activity)

        return rootView
    }
    class CountryAdapter(items: ArrayList<Song>, ctx: FragmentActivity?) :
        RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

        var list = items

        var cts: FragmentActivity? = ctx
        lateinit var ju: ViewHolder
        lateinit var xu: ViewHolder
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {


            return ViewHolder(LayoutInflater.from(cts).inflate(R.layout.card_item2, p0, false))

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

                    if(isnotru){
                        isnotru = false
                        MusicPlayer().pause()
                    }
                    else{
                        isnotru = true
                        MusicPlayer().play()
                    }
                }

            })
            })


            var totalsize: Int = list.size

            var handtwo = Handler()
            handtwo.post({p0.img1.setOnClickListener(object : View.OnClickListener {

                override fun onClick(view: View?) {

                    if(istru) {
                        if(gr){
                            xu.sek.visibility = View.GONE
                            xu.bu.visibility = View.GONE
                            xu.img2.visibility = View.GONE
                        }
                        ju = take(p0)


                        gr= true

                        istru = false
                    }
                    else{
                        if(gr){
                            ju.sek.visibility = View.GONE
                            ju.bu.visibility = View.GONE
                            ju.img2.visibility = View.GONE
                        }
                        xu =  take(p0)




                        istru = true

                    }
                    var fer: Song = list.get(position)
                    var handler = Handler()
                    handler.postDelayed({


                        MusicPlayer().playall(cts as AppCompatActivity, fer)


                    }, 100)


                }

            })  })



        }


        class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

            var img1 = itemView.findViewById<ImageView>(R.id.card_background1)
            var img2 = itemView.findViewById<ImageView>(R.id.imageView1)
            var bu = itemView.findViewById<Button>(R.id.button2)

            var sek: SeekBar = itemView.findViewById(R.id.seekBar3)

        }





    }



}