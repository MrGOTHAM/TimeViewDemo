package com.example.timeviewdemo.clockView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.timeviewdemo.R
import kotlinx.android.synthetic.main.activity_main.*

class ClockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 调用自定义控件内的方法
        initTime()
        time_view.startTimer()
        getTime()
    }

    private fun initTime(){
        edit_time_button.setOnClickListener {
            if (!TextUtils.isEmpty(hour_editText.text) && !TextUtils.isEmpty(min_editText.text.toString()) && !TextUtils.isEmpty(second_editText.text.toString())) {
                val hour = Integer.parseInt(hour_editText.text.toString())
                val min = Integer.parseInt(min_editText.text.toString())
                val second = Integer.parseInt(second_editText.text.toString())
                time_view.setTimer(hour, min, second)
            }
        }
    }

    private fun getTime(){
        get_time_button.setOnClickListener {
            Toast.makeText(this, time_view.getTime(), Toast.LENGTH_SHORT).show()
        }
    }
}
