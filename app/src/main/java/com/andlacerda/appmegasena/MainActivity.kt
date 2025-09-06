package com.andlacerda.appmegasena

import android.os.Bundle
import android.util.Log
import android.view.View
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editText: EditText = findViewById(R.id.edit_number);
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        if (text.isNotEmpty()){
            val qtd = text.toInt()
            if (qtd >= 6 && qtd <= 15){
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

            }else{
                Toast.makeText(this, "Informe um número enre 6 e 15", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Informe um número enre 6 e 15", Toast.LENGTH_LONG).show()
        }
    }
}