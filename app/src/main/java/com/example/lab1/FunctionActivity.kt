package com.example.lab1

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.templete_function.*
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.time.Duration

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

                }
            }


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

            }
            saveIntoFileBtn.setOnClickListener{
            val file = textFileName2.text.toString()
            val data = "$functionType,$numA,$numB,$numC,$numD,$intervalA,$intervalB"

            val fileOutputStream: FileOutputStream

            if(file == null || file.trim() == ""){
                showToast("Filename must be not empty!")
            }
             else{
                try{
                    fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
                    fileOutputStream.write(data.toByteArray())
                }
                catch(e: FileNotFoundException){
                    e.printStackTrace()
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
                showToast("Saved to file"+getExternalFilesDir(file))
            }
                textFileName2.setText("")
        }

        readFileBtn.setOnClickListener{

            val filename = textFileName.text.toString()
            if(filename != null && filename.trim() != ""){
                var fileInputStream: FileInputStream? = null
                try{
                    fileInputStream = openFileInput(filename)
                    var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                    val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

                    val stringBuilder: StringBuilder = StringBuilder()
                    var text: String? = null
                    while({text = bufferedReader.readLine(); text}() != null){
                        stringBuilder.append(text)
                    }
                    val listParam = stringBuilder.split(",")
                    if(listParam.size == 7){
                        if(listParam[0].toInt() == 0 || listParam[0].toInt() == 1){
                            result.text = options.get(listParam[0].toInt())
                            editNumA.setText(listParam[1])
                            editNumB.setText(listParam[2])
                            editNumC.setText(listParam[3])
                            editNumD.setText(listParam[4])
                            editIntA.setText(listParam[5])
                            editIntB.setText(listParam[6])
                        }
                        else{
                            showToast("Incorrect format of file! ")
                        }
                    }
                    else{
                        showToast("Incorrect format of file! ")
                    }
                }
                catch(e: Exception){
                    showToast("Haven`t such file in system! ")
                }

            }
            else{
                showToast("Filename must be not empty!")
            }
            textFileName.setText("")
        }

    }
    fun Context.showToast (text:CharSequence, duration: Int = Toast.LENGTH_SHORT){
            Toast.makeText(this, text, duration).show()
    }

    override fun onResume() {
        super.onResume()

        goFindIntegral.isEnabled = false;
        goFindIntegral.isClickable = false;
        saveIntoFileBtn.isEnabled = false;
        saveIntoFileBtn.isClickable = false;

        editNumA.setText("")
        editNumB.setText("")
        editNumC.setText("")
        editNumD.setText("")
        textFileName.setText("")
        textFileName2.setText("")
    }
}