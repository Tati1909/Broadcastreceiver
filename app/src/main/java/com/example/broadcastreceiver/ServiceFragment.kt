package com.example.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.broadcastreceiver.databinding.FragmentServiceBinding
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * переменные для передачи данных и создания
 * собственного интент-фильтра.
 */
const val TEST_BROADCAST_INTENT_FILTER = "TEST BROADCAST INTENT FILTER"
const val THREADS_FRAGMENT_BROADCAST_EXTRA = "THREADS_FRAGMENT_EXTRA"

class ServiceFragment : Fragment() {

    /**
     * Создаём свой BroadcastReceiver (получатель широковещательного сообщения)
     */
    private val testReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            /**
             * Достаём данные из интента
             */
            intent.getStringExtra(THREADS_FRAGMENT_BROADCAST_EXTRA)?.let {
                binding.mainContainer
                    .addView(AppCompatTextView(context)
                        .apply {
                            text = it
                            textSize =
                                resources.getDimension(R.dimen.main_container_text_size)
                        })
            }
        }
    }

    private var _binding: FragmentServiceBinding? = null
    private val binding get() = _binding!!

    /**
     * Подпишемся на события
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(
                    testReceiver,
                    IntentFilter(TEST_BROADCAST_INTENT_FILTER)
                )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         *  В EditText мы вводим количество секунд, на которое
        хотим «загрузить» основной поток нашего приложения. Как только расчёты закончатся, выводим
        значение секунд справа и название потока ниже.
         */

        binding.serviceWithBroadcastButton.setOnClickListener {
            binding.textView.text =
                startCalculations(binding.editText.text.toString().toInt())

            context?.let {
                it.startService(Intent(
                    it,
                    MainService::class.java
                ).apply {
                    putExtra(
                        MAIN_SERVICE_INT_EXTRA,
                        binding.editText.text.toString().toInt()
                    )
                })
            }
        }
    }

    /**
     * Метод startCalculations в цикле высчитывает дату, пока она не станет меньше одной секунды.
    Грубо говоря, мы загружаем расчётами даты основной поток приложения на одну секунду. Вызываем
    метод startCalculations каждый раз, когда нажимаем на кнопку «Расчёт».
     */
    private fun startCalculations(seconds: Int): String {
        val date = Date()
        var diffInSec: Long
        do {
            val currentDate = Date()
            val diffInMs: Long = currentDate.time - date.time
            diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs)
        } while (diffInSec < seconds)
        return diffInSec.toString()
    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(testReceiver)
        }
        super.onDestroy()
    }
}