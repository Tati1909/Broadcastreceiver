package com.example.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Регистрируем наш ресивер в манифесте. Это называется статическая регистрация.
 * Даже если приложение не запущено - ресивер, зарегистрированный в манифесте, получает событие.
 * В этом случае система стартует процесс приложения и вызывает BroadcastReceiver.onReceive() в главном потоке.
 * Т. е. будет выведено сообщение о смене языка.
 * Фоновые процессы имеют низкий приоритет, поэтому после завершения метода onReceive()
 * система убивает процесс приложения.
 */

class MainBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        StringBuilder().apply {
            append("СООБЩЕНИЕ ОТ СИСТЕМЫ\n")
            append("Action: ${intent.action}")
            toString().also {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}