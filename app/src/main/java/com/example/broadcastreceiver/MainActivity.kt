package com.example.broadcastreceiver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Регистрируем BroadcastReceiver программно(ни в манифесте).
 * Это называется динамическая регистрация.
 * Для этого мы просто создаем
 * экземпляр нашего ресивера и регистрируем его в MainActivity
 */

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}