package com.example.broadcastreceiver

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Регистрируем BroadcastReceiver программно(ни в манифесте).
 * Это называется динамическая регистрация.
 * Для этого мы просто создаем
 * экземпляр нашего ресивера и регистрируем его в MainActivity
 */

class MainActivity : AppCompatActivity() {

    private val receiver = MainBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * регистрируем ресивер через метод registerReceiver, который принимает
        наш ресивер и интент-фильтр с указанием, на какое сообщение ОС хотим подписаться. Например, на
        переход в режим самолёта Intent.ACTION_AIRPLANE_MODE_CHANGED.
         */
        registerReceiver(
            receiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )
    }

    override fun onDestroy() {

        unregisterReceiver(receiver)
        super.onDestroy()
    }
}