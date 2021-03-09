package com.example.homework2

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    val numerics = arrayOf(R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7, R.id.num8, R.id.num9)
    val operators = arrayOf(R.id.add, R.id.sub, R.id.mult, R.id.div, R.id.pow, R.id.mod)
    val binaryOps = arrayOf(R.id.posNeg, R.id.sqrt)

    var num1 = ""
    var num2 = ""
    var operator = ""

    private fun updateDisplay(){
        val funcDisplay = findViewById<TextView>(R.id.displayFunction)
        val resDisplay = findViewById<TextView>(R.id.resultDisplay)

        if(num1.length > 13){
            num1 = num1.take(13)
            Toast.makeText(applicationContext,"Can't display more than 13 digits", Toast.LENGTH_SHORT).show()
        }
        if(num2.length > 13){
            num2 = num2.take(13)
            Toast.makeText(applicationContext,"Can't display more than 13 digits", Toast.LENGTH_SHORT).show()
        }

        funcDisplay.text = num1
        if(operator != "")
        {   funcDisplay.text = funcDisplay.text.toString() + "\n" + operator + num2   }
        resDisplay.text = computeResult()?.toString() ?: ""
    }

    private fun addDecimal(){
        if(operator == "" && !num1.contains('.'))
        {
            if(num1 == "")
            {   num1 = "0." }
            else
            {   num1 += "." }
        }
        else if(operator != "" && !num2.contains('.'))
        {
            if(num2 == "")
            {   num2 = "0." }
            else
            {   num2 += "." }
        }
        updateDisplay()
    }

    private fun updateNum(num: TextView) = run {
        if(operator == "")
        {   num1 += num.text.toString()    }
        else
        {   num2 += num.text.toString() }
        updateDisplay()
    }

    private fun updateOperator(op: TextView) = run{
        if(num1 != "") {
            operator = op.text.toString()
            if (operator == "Mod") {
                operator = "%"
            }
            updateDisplay()
        }
    }

    private fun computeResult(): Double? {
        if(num1 != "" && num2 != ""){
            val leftOperand = num1.toDouble()
            val rightOperand = num2.toDouble()
            return when(operator){
                "+" -> leftOperand + rightOperand
                "-" -> leftOperand - rightOperand
                "*" -> leftOperand * rightOperand
                "/" -> leftOperand / rightOperand
                "^" -> leftOperand.pow(rightOperand)
                "%" -> leftOperand % rightOperand
                else -> 0.0
            }
        }
        return null
    }

    private fun equality(eq: TextView) = run{
        val result = findViewById<TextView>(R.id.resultDisplay).text
        if(result != ""){
            cls()
            num1 = result.toString()
            updateDisplay()
        }
    }

    private fun cls() = run{
        num1 = ""
        num2 = ""
        operator = ""
        updateDisplay()
    }

    private fun binaryOp(op: TextView) = run{
        val previousResult = findViewById<TextView>(R.id.resultDisplay).text
        val operand: Double
        val result: Double
        if(previousResult != "")
        {   operand = previousResult.toString().toDouble()  }
        else{
            operand = num1.toDouble()
        }
        result = if(op.text == "+/-"){
            operand * -1
        }
        else{
            sqrt(operand)
        }
        cls()
        num1 = result.toString()
        updateDisplay()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set on-click listeners
        for(v in numerics) {
            val num = findViewById<TextView>(v)
            num.setOnClickListener { updateNum(num) }
        }
        for(v in operators){
            val op = findViewById<TextView>(v)
            op.setOnClickListener{updateOperator(op)}
        }
        for(v in binaryOps){
            val op = findViewById<TextView>(v)
            op.setOnClickListener{binaryOp(op)}
        }

        val eq = findViewById<TextView>(R.id.equals)
        eq.setOnClickListener{equality(eq)}

        val dec = findViewById<TextView>(R.id.decimal)
        dec.setOnClickListener{addDecimal()}

        val clr = findViewById<TextView>(R.id.clear)
        clr.setOnClickListener{cls()}

    }
}

