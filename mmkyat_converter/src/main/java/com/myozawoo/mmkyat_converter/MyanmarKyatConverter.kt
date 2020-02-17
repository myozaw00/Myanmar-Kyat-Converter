package com.myozawoo.mmkyat_converter

import android.util.Log
import java.util.regex.Pattern

object MyanmarKyatConverter {

    private val words = listOf<String>("", "တစ်", "နှစ်", "သုံး", "လေး", "ငါး", "ခြောက်", "ခုနှစ်", "ရှစ်", "ကိုး", "တစ်ဆယ်")

    fun convertToMyanmarKyat(num: CharSequence): String {

        if (num.length > 10) return "OVERFLOW"
        val format = "0000000000"
        val PATTERN = Pattern.compile("^(\\d{1})(\\d{1})(\\d{1})(\\d{2})(\\d{1})(\\d{1})(\\d{1})(\\d{2})\$")
        val n = format.substring(num.length)+num
        val matcher = PATTERN.matcher(n)
        var upperLakh = ""
        var lowerLakh = ""
        if (matcher.matches()) {
            val mr = matcher.toMatchResult()

            upperLakh += if(mr.group(1) != "0") {
                "သိန်း" + words[mr.group(1).toInt()] + "သောင်း"
            }else ""

            upperLakh += if (mr.group(2) != "0") {
                (if (upperLakh != "") "" else "သိန်း") + words[mr.group(2).toInt()] + "ထောင်"
            }else ""

            upperLakh += if(mr.group(3) != "0"){
                (if (upperLakh != "") "" else "သိန်း") + words[mr.group(3).toInt()] + "ရာ"
            }else ""

            if (mr.group(4) != "0") {
                if (mr.group(4)[0].toString() != "0" && mr.group(4)[1].toString() == "0") {
                    upperLakh += (if (upperLakh != "") "" else "သိန်း") + words[mr.group(4).substring(0,1).toInt()] + "ဆယ်"
                }else if (mr.group(4)[0].toString() != "0" && mr.group(4)[1].toString() != "0") {
                    upperLakh += words[mr.group(4).substring(0, 1).toInt()] + "ဆယ်" + words[mr.group(4).substring(1).toInt()] + "သိန်း"
                }else if(mr.group(4)[0].toString() == "0" && mr.group(4)[1].toString() != "0") {
                    upperLakh += words[mr.group(4).substring(1).toInt()] + "သိန်း"
                }
            }

            lowerLakh += if (mr.group(5) != "0") {
                words[mr.group(5).toInt()] + "သောင်း"
            }else ""

            lowerLakh += if (mr.group(6) != "0") {
                words[mr.group(6).toInt()] + "ထောင်"
            }else ""

            lowerLakh += if (mr.group(7) != "0") {
                words[mr.group(7).toInt()] + "ရာ"
            }else ""

            lowerLakh += if (mr.group(8)[0].toString() != "0" && mr.group(8)[1].toString() != "0") {
                words[mr.group(8).substring(0, 1).toInt()] + "ဆယ်" + words[mr.group(8).substring(1).toInt()]
            }else if(mr.group(8)[0].toString() == "0" && mr.group(8)[1].toString() != "0") {
               words[mr.group(8).substring(1).toInt()]
            }else if(mr.group(8)[0].toString() != "0" && mr.group(8)[1].toString() == "0") {
                words[mr.group(8).substring(0,1).toInt()] + "ဆယ်"
            }else ""

            var final = if (upperLakh.isNotEmpty() && lowerLakh.isNotEmpty()) "${upperLakh} နှင့် ${lowerLakh}" else upperLakh+lowerLakh
            return final

        }else {
            return "INVALID FORMAT"
        }
    }
}