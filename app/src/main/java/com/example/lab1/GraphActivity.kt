package com.example.lab1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.android.synthetic.main.activity_integral.*
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sin

class GraphActivity : AppCompatActivity() {

    private var classFunAct = FunctionActivity()
    var NumA = classFunAct.numA
    var NumB = classFunAct.numB
    var NumC = classFunAct.numC
    var NumD = classFunAct.numD

    var functionType = classFunAct.functionType

    var intervalA = classFunAct.intervalA
    var intervalB = classFunAct.intervalB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        val intent = getIntent()
        NumA = intent.getDoubleExtra("NumA", 0.0)
        NumB = intent.getDoubleExtra("NumB", 0.0)
        NumC = intent.getDoubleExtra("NumC", 0.0)
        NumD = intent.getDoubleExtra("NumD", 0.0)
        functionType = intent.getIntExtra("funType", 0)
        intervalA = intent.getDoubleExtra("intA", 0.0)
        intervalB = intent.getDoubleExtra("intB", 0.0)

        if(functionType == 0){
            graphFuncInfo.text = "$NumA*x^3 + $NumB*sin($NumC*x)*x^2 + $NumD*x"
        }
        else{
            graphFuncInfo.text = "$NumA*$NumB^($NumC*x)*(ln(x^2+1))^$NumD"
        }

        graphIntervalsNum.text = "[$intervalA;$intervalB]"

        val graph: GraphView = findViewById(R.id.graph)
        val series = LineGraphSeries(arrayOf())
        var y = 0.0
        var x = intervalA
        while(x <= intervalB) {
            if(functionType == 0){
                y = NumA*x.pow(3)+NumB*sin(NumC * x)*x.pow(2) + NumD*x
            }
            if(functionType == 1){
               y = NumA*NumB.pow(NumC*x) * ln(x.pow(2)+1).pow(NumD)
            }
            series.appendData(DataPoint(x, y), true, ((intervalB-intervalA)*1000).toInt())
            x += 0.01

        }

        graph.viewport.isScrollable = true
        graph.viewport.isScalable = true
        graph.addSeries(series)

    }
}