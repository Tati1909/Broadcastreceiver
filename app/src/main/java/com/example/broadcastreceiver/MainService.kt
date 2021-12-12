package com.example.broadcastreceiver

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

private const val TAG = "MainServiceTAG"
const val MAIN_SERVICE_INT_EXTRA = "MainServiceIntExtra"

/**
 * Добавим в сервис возможность обрабатывать входящий интент,
 * переопределим методы жизненного цикла сервиса
 * и отобразим логи в консоль:
 * com.example.intentservice D/MainServiceTAG: onCreate
 * com.example.intentservice D/MainServiceTAG: onStartCommand
 * com.example.intentservice D/MainServiceTAG: onHandleIntent Hello from ServiceFragment
 * com.example.intentservice D/MainServiceTAG: onDestroy
 */
class MainService(name: String = "MainService") : IntentService(name) {

    override fun onCreate() {
        Log.d(TAG, "onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "onHandleIntent ${intent?.getIntExtra(MAIN_SERVICE_INT_EXTRA, 0).toString()}")

        intent?.let {
            sendBack(it.getIntExtra(MAIN_SERVICE_INT_EXTRA, 0).toString())
        }
    }

    /**
     *     Отправка уведомления о завершении сервиса
     */
    private fun sendBack(result: String) {

        val broadcastIntent = Intent(TEST_BROADCAST_INTENT_FILTER)
        broadcastIntent.putExtra(THREADS_FRAGMENT_BROADCAST_EXTRA, result)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }
}