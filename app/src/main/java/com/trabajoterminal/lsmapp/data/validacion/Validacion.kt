package com.trabajoterminal.lsmapp.data.validacion

class Validacion(palabraRec: String) {

    var resultado: Boolean = validaPalabra(palabraRec)

    fun getResultadoValidacion(): Boolean {
        return resultado
    }

}

fun validaPalabra(palabra: String): Boolean {

    var res: Boolean = false

    val expresionRegular = Regex("[a-il-no-ps-uwy]+",RegexOption.IGNORE_CASE)

    res = expresionRegular.matches(palabra)

    return res

}