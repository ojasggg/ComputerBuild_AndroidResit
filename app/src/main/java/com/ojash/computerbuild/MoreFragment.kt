package com.ojash.computerbuild

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button

class MoreFragment: Fragment(){

    private lateinit var webview: WebView
    private lateinit var btnMap: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val moreFragmentView= layoutInflater.inflate(R.layout.fragment_more,container,false)

        webview=moreFragmentView.findViewById(R.id.webview)
        btnMap=moreFragmentView.findViewById(R.id.btnMap)


        btnMap.setOnClickListener {
            val intent= Intent(context, MapActivity::class.java)
            context?.startActivity(intent)
        }

        webview.loadUrl("https://softwarica.edu.np/about-softwarica/")

        return moreFragmentView
    }
}