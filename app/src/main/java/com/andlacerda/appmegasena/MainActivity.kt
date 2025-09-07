package com.andlacerda.appmegasena

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import java.util.Random
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)
        val txt_1: TextView = findViewById(R.id.txt_result_1)
        val txt_2: TextView = findViewById(R.id.txt_result_2)
        val txt_3: TextView = findViewById(R.id.txt_result_3)
        val txt_4: TextView = findViewById(R.id.txt_result_4)
        val txt_5: TextView = findViewById(R.id.txt_result_5)


        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)
        txt_1.text = prefs.getString("txt1", "")
        txt_2.text = prefs.getString("txt2", "")
        txt_3.text = prefs.getString("txt3", "")
        txt_4.text = prefs.getString("txt4", "")
        txt_5.text = prefs.getString("txt5", "")
        if (result != null) {
            txtResult.text = "Ultima aposta: $result"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(text, txtResult)
            val allLast= listOf(txt_1, txt_2, txt_3, txt_4, txt_5)
            val editor = prefs.edit()
            allLast.forEachIndexed { index, item ->
                if (item.text == ""){
                    item.text = prefs.getString("result", "")
                    editor.putString("txt${index + 1}", item.text.toString())
                    editor.apply()
                    return@setOnClickListener
                }
            }
            txt_5.text = txt_4.text
            txt_4.text = txt_3.text
            txt_3.text = txt_2.text
            txt_2.text = txt_1.text
            txt_1.text = prefs.getString("result", null)
            editor.apply{
                putString("txt1", txt_1.text.toString())
                putString("txt2", txt_2.text.toString())
                putString("txt3", txt_3.text.toString())
                putString("txt4", txt_4.text.toString())
                putString("txt5", txt_5.text.toString())
                apply()
            }
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        if (text.isEmpty()){
            Toast.makeText(this, "Informe um número enre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt()
        if (qtd < 6 || qtd > 15){
            Toast.makeText(this, "Informe um número enre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while(true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }

        txtResult.text = numbers.joinToString ( " - " )
        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()
}
}