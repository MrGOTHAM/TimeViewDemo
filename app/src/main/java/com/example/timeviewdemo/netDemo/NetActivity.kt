package com.example.timeviewdemo.netDemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.timeviewdemo.R
import kotlinx.android.synthetic.main.activity_net.*

/**
 * Created by anchaoguang on 2019-11-22.
 *
 */
class NetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net)
        var list = ArrayList<Bean>()
        list.add(Bean("投篮",0.6f))
        list.add(Bean("突破",0.9f))
        list.add(Bean("篮板",0.8f))
        list.add(Bean("助攻",0.9f))
        list.add(Bean("抢断",0.8f))
        list.add(Bean("盖帽",0.6f))
        netView.start(list)
    }
}