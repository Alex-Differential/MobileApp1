package com.example.lab1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_integral.*
import kotlinx.android.synthetic.main.templete_function.*
import java.math.BigDecimal
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sin

class IntegralActivity : AppCompatActivity() {

    private var classFunAct = FunctionActivity()
    var NumA = classFunAct.numA
    var NumB = classFunAct.numB
    var NumC = classFunAct.numC
    var NumD = classFunAct.numD

    var functionType = classFunAct.functionType

    var intervalA = classFunAct.intervalA
    var intervalB = classFunAct.intervalB
    var n = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_integral)

        val intent = getIntent()
        NumA = intent.getDoubleExtra("numA",0.0)
        NumB = intent.getDoubleExtra("numB",0.0)
        NumC = intent.getDoubleExtra("numC",0.0)
        NumD = intent.getDoubleExtra("numD",0.0)
        functionType = intent.getIntExtra("funType",0)
        intervalA = intent.getDoubleExtra("intA",0.0)
        intervalB = intent.getDoubleExtra("intB",0.0)

        if(functionType == 0){
            funcInfo.text = "$NumA*x^3 + $NumB*sin($NumC*x)*x^2 + $NumD*x"
        }
        else{
            funcInfo.text = "$NumA*$NumB^($NumC*x)*(ln(x^2+1))^$NumD"
        }

        intervalsNum.text = "[$intervalA;$intervalB]"
        val startMidpoint = System.nanoTime()
        var res1 = MidpointRule(intervalA,intervalB, NumA, NumB, NumC, NumD)
        val finishMidpoint = System.nanoTime()
        var timeMidpoint = (finishMidpoint-startMidpoint)/1000.0
        val startTrap = System.nanoTime()
        var res2 = TrapRule(intervalA,intervalB, NumA, NumB, NumC, NumD)
        val finishTrap = System.nanoTime()
        var timeTrap = (finishTrap-startTrap)/1000.0
        val startSimpson = System.nanoTime()
        var res3 = SimpsonRule(intervalA,intervalB, NumA, NumB, NumC, NumD)
        val finishSimpson = System.nanoTime()
        var timeSimpson = (finishSimpson-startSimpson)/1000.0


        textView15.text = "$res1"
        timeMidpointText.text = "$timeMidpoint mcs"
        textView23.text = "$res2"
        timeTrapText.text = "$timeTrap mcs"
        textView27.text = "$res3"
        timeSimpsonText.text = "$timeSimpson mcs"

        graphBtn.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("NumA", NumA)
            intent.putExtra("NumB", NumB)
            intent.putExtra("NumC", NumC)
            intent.putExtra("NumD", NumD)
            intent.putExtra("funType", functionType)
            intent.putExtra("intA", intervalA)
            intent.putExtra("intB", intervalB)
            startActivity(intent)
        }

        prevIntBtn.setOnClickListener{
            val intent = Intent(this, FunctionActivity::class.java)
            startActivity(intent)
        }
    }

    fun MidpointRule(a:Double, b: Double, NumA:Double, NumB:Double, NumC:Double, NumD:Double): Double {
        val h = (b-a)/n
        var sum = 0.0
        for (i in 0 until n) {
            val x = a + i * h
            if(functionType == 0){
                sum += Func1(x + h / 2.0, NumA, NumB, NumC, NumD)
            }
            if(functionType == 1){
                sum += Func2(x + h / 2.0, NumA, NumB, NumC, NumD)
            }

        }
        return h*sum
    }

    fun TrapRule(a:Double, b: Double, NumA:Double, NumB:Double, NumC:Double, NumD:Double): Double {
        val h = (b-a)/n
        var sum = 0.0
        for (i in 0 until n) {
            val x = a + i * h
            if(functionType == 0){
                sum += (Func1(x , NumA, NumB, NumC, NumD) + Func1(x + h , NumA, NumB, NumC, NumD))/2.0
            }
            if(functionType == 1){
                sum += (Func2(x , NumA, NumB, NumC, NumD) + Func2(x + h , NumA, NumB, NumC, NumD))/2.0
            }

            /*sum[1] += (f(x) + f(x + h)) / 2.0
            sum[2] += (f(x) + 4.0 * f(x + h / 2.0) + f(x + h)) / 6.0*/
        }
        return h*sum
    }

    fun SimpsonRule(a:Double, b: Double, NumA:Double, NumB:Double, NumC:Double, NumD:Double): Double {
        val h = (b-a)/n
        var sum = 0.0
        for (i in 0 until n) {
            val x = a + i * h
            if(functionType == 0){
                sum += (Func1(x , NumA, NumB, NumC, NumD) + 4.0 * Func1(x + h/2.0 , NumA, NumB, NumC, NumD) + Func1(x + h, NumA, NumB, NumC, NumD)) / 6.0
            }
            if(functionType == 1){
                sum += (Func2(x , NumA, NumB, NumC, NumD) + 4.0 * Func2(x + h/2.0 , NumA, NumB, NumC, NumD) + Func2(x + h, NumA, NumB, NumC, NumD)) / 6.0
            }

        }
        return h*sum
    }

    fun Func1(x:Double, a:Double, b:Double, c:Double, d: Double):Double{
        return a*x.pow(3)+b*sin(c*x)*x.pow(2) + d*x
    }

    fun Func2(x:Double, a:Double, b:Double, c:Double, d: Double):Double{
        return a*b.pow(c*x)*ln(x.pow(2)+1).pow(d)
    }

}

typealias Func = (Double) -> Double