package com.example.lab1

import android.content.Context
import android.content.Intent
import android.icu.number.IntegerWidth
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.templete_function.*
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.io.PrintWriter
import java.lang.Exception

class FunctionActivity : AppCompatActivity() {

    private val LOG_TAG = FunctionActivity::class.java.simpleName
    private val FILE_NAME = "example.txt"

    lateinit var option : Spinner
    lateinit var result: TextView

    lateinit var editNumA : EditText
    lateinit var editNumB : EditText
    lateinit var editNumC : EditText
    lateinit var editNumD : EditText

    lateinit var editIntA : EditText
    lateinit var editIntB : EditText

    var numA = 0.0
    var numB = 0.0
    var numC = 0.0
    var numD = 0.0

    var intervalA = 0.0
    var intervalB = 0.0

    var functionType = 0

    public  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.templete_function)

        goFindIntegral.isEnabled = false;
        goFindIntegral.isClickable = false;
        saveIntoFileBtn.isEnabled = false;
        saveIntoFileBtn.isClickable = false;
        button2.isEnabled = false;
        button2.isClickable = false;

        option = findViewById(R.id.spTypeFunc) as Spinner
        result = findViewById(R.id.typeResult) as TextView

        editNumA = findViewById(R.id.numA)
        editNumB = findViewById(R.id.numB)
        editNumC = findViewById(R.id.numC)
        editNumD = findViewById(R.id.numD)

        editIntA = findViewById(R.id.intervalA)
        editIntB = findViewById(R.id.intervalB)

        val options = arrayOf("f(x)=A*x^3 + B*sin(C*x)*x^2+D*X","f(x)=A*B^(C*x)*(ln(x^2+1))^D")

        option.adapter = ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, options)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                result.text = options.get(position)
                functionType = position
            }
        }

        saveNumBtn.setOnClickListener{
            if(editNumA.text.toString().isEmpty()){
                editNumA.setText("0")
            }
            if(editNumB.text.toString().isEmpty()){
                editNumB.setText("0")
            }
            if(editNumC.text.toString().isEmpty()){
                editNumC.setText("0")
            }
            if(editNumD.text.toString().isEmpty()){
                editNumD.setText("0")
            }
            numA = editNumA.text.toString().toDouble()
            numB = editNumB.text.toString().toDouble()
            numC = editNumC.text.toString().toDouble()
            numD = editNumD.text.toString().toDouble()

            if(editIntA.text.toString().isEmpty() or editIntB.text.toString().isEmpty()){
                Toast.makeText(this,"Поля інтервалів мають бути непорожніми!",Toast.LENGTH_LONG).show()
            }else{
                if(editIntA.text.toString().toDouble() >= editIntB.text.toString().toDouble()){
                    Toast.makeText(this,"Невірно введені межі інтервалу!",Toast.LENGTH_LONG).show()
                }
                else{
                    intervalA = editIntA.text.toString().toDouble()
                    intervalB = editIntB.text.toString().toDouble()

                    goFindIntegral.isEnabled = true;
                    goFindIntegral.isClickable = true;
                    saveIntoFileBtn.isEnabled = true;
                    saveIntoFileBtn.isClickable = true;
                    button2.isEnabled = true;
                    button2.isClickable = true;
                }
            }

            textView10.setText("$numA,$numB")

        }

        goFindIntegral.setOnClickListener {
            val intent = Intent(this, IntegralActivity::class.java)
            intent.putExtra("numA", numA)
            intent.putExtra("numB", numB)
            intent.putExtra("numC", numC)
            intent.putExtra("numD", numD)
            intent.putExtra("funType", functionType)
            intent.putExtra("intA", intervalA)
            intent.putExtra("intB", intervalB)
            startActivity(intent)
        }

        saveIntoFileBtn.setOnClickListener{
            if(editNumA.text.toString().isEmpty()){
                editNumA.setText("0")
            }
            if(editNumB.text.toString().isEmpty()){
                editNumB.setText("0")
            }
            if(editNumC.text.toString().isEmpty()){
                editNumC.setText("0")
            }
            if(editNumD.text.toString().isEmpty()){
                editNumD.setText("0")
            }
            numA = editNumA.text.toString().toDouble()
            numB = editNumB.text.toString().toDouble()
            numC = editNumC.text.toString().toDouble()
            numD = editNumD.text.toString().toDouble()
/*
        var fos : FileOutputStream? = null
        var oos : ObjectOutputStream? = null

            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            oos = ObjectOutputStream(fos)
            oos.writeObject(("$numA,$numB").toString())
            Toast.makeText(this,"Save to" + getExternalFilesDir(FILE_NAME),Toast.LENGTH_LONG).show()

        try {
            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            oos = ObjectOutputStream(fos)
            oos.writeObject(("$numA,$numB").toByte())

            editNumA.text.clear()
            Toast.makeText(this,"Save to" + getExternalFilesDir(FILE_NAME),Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Log.e(LOG_TAG, "Cannot write!")
            e.printStackTrace()
        }
        finally {
            try{
                oos?.close()
                fos?.close()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }*/
            val sd_main = File(Environment.getExternalStorageState()+"")
            Toast.makeText(this,sd_main.toString(),Toast.LENGTH_LONG).show()
            var success = true
            if(!sd_main.exists())
                success = sd_main.mkdir()
            if(success){
                val sd =File("Filename.txt")
                if(!sd.exists())
                    success =sd.mkdir()
                if(success){
                    val dest = File(sd,"1qwer.txt")
                    PrintWriter(dest).use { out -> println(numA) }
                    try{
                        PrintWriter(dest).use { out -> println(numA) }
                    }catch (e:Exception){

                    }
                }
            }
        }

    }


}