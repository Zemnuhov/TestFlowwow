package com.example.testflowwow

data class NumberPOJOItem(
    val cc: String,
    val desc_en: String,
    val desc_ru: String,
    val mask: String,
    val name_en: String,
    val name_ru: String
){
    fun getCode(): String{
        val codeLength =
            if(mask.indexOf("(") != -1)
                mask.indexOf("(")
            else
                mask.indexOf("-")

        return mask.substring(0,codeLength)
    }

    fun getNumberMask(): String{
        val mask = mask.replace("("," ")
            .replace(")"," ")
            .replace("-"," ")
        return mask.map {
            when(it){
                '+'->'+'
                ' '->' '
                else -> '_'
            }
        }.joinToString(separator = "")
    }
}
