package com.myozawoo.mmkyat_converter

import android.util.Log
import java.text.DecimalFormat

object EnglishNumberToWords {
    private val tensNames = arrayOf(
        "", " Ten", " Twenty", " Thirty", " Forty",
        " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"
    )
    private val numNames = arrayOf(
        "", " One", " Two", " Three", " Four", " Five",
        " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve", " Thirteen",
        " Fourteen", " Fifteen", " Sixteen", " Seventeen", " Eighteen", " Nineteen"
    )

    private fun convertLessThanOneThousand(number: Int): String {
        var number = number
        var soFar: String
        if (number % 100 < 20) {
            soFar = numNames[number % 100]
            number /= 100
        } else {
            soFar = numNames[number % 10]
            number /= 10
            soFar = tensNames[number % 10] + soFar
            number /= 10
        }
        return if (number == 0) soFar else numNames[number] + " Hundred" + soFar
    }

    fun convert(number: CharSequence): String { // 0 to 999 999 999 999
        Log.d(javaClass.simpleName, number.toString())
        if (number.toString() == "") {
            return ""
        }
        var snumber = number
        // pad with "0"
        val mask = "000000000000"
        val df = DecimalFormat(mask)
        snumber = df.format(number.toString().toLong())
        // XXXnnnnnnnnn
        val billions = snumber.substring(0, 3).toInt()
        // nnnXXXnnnnnn
        val millions = snumber.substring(3, 6).toInt()
        // nnnnnnXXXnnn
        val hundredThousands = snumber.substring(6, 9).toInt()
        // nnnnnnnnnXXX
        val thousands = snumber.substring(9, 12).toInt()
        val tradBillions: String
        tradBillions = when (billions) {
            0 -> ""
            1 -> convertLessThanOneThousand(billions) + " Billion "
            else -> convertLessThanOneThousand(billions) + " Billion "
        }
        var result = tradBillions
        val tradMillions: String
        tradMillions = when (millions) {
            0 -> ""
            1 -> convertLessThanOneThousand(millions) + " Million "
            else -> convertLessThanOneThousand(millions) + " Million "
        }
        result = result + tradMillions
        val tradHundredThousands: String
        tradHundredThousands = when (hundredThousands) {
            0 -> ""
            1 -> "One Thousand "
            else -> convertLessThanOneThousand(hundredThousands) + " Thousand "
        }
        result = result + tradHundredThousands
        val tradThousand: String
        tradThousand = convertLessThanOneThousand(thousands)
        result = result + tradThousand
        // remove extra spaces!
        return result.replace("^\\s+".toRegex(), "").replace("\\b\\s{2,}\\b".toRegex(), " ")
    }
}