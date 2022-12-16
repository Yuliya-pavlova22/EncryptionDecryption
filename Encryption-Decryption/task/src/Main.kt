package encryptdecrypt

import java.io.File
import java.security.Key
object EncDec {

}

//kotlinc Main.kt -include-runtime -d main.jar && java -jar main.jar --help

fun main(args: Array<String>) = try {

    if (args.isEmpty()) {
        throw Exception("Error")
    }

    var mode = "enc"
    var data = ""
    var key = 0
    var inFiletxt = ""
    var outFiletxt = ""
    var algoritm = ""

    for (element in args) {
        when (element) {
            "-mode" ->  mode = args[args.indexOf("-mode") + 1]
            "-data" -> data = args[args.indexOf("-data") + 1]
            "-key" -> key = args[args.indexOf("-key") + 1].toInt()
            "-in" -> inFiletxt = args[args.indexOf("-in") + 1]
            "-out" -> outFiletxt = args[args.indexOf("-out") + 1]
            "-alg" -> algoritm = args[args.indexOf("-alg") + 1]
            }
    }
    var str = ""
  //   Создаем файлы для входа и выхода
    if (inFiletxt != "" && outFiletxt != "") {
        var inFile = File(inFiletxt)
        data = inFile.readText()
        var outFile = File(outFiletxt)
        outFile.createNewFile()
        str = cryption(algoritm, data, key, mode)

         outFile.writeText(str)
        } else {
        str = cryption(algoritm, data, key, mode)
        println(str)
    }
}
    catch (ex: RuntimeException) {
    println(ex.message)
}

fun cryption(algoritm: String, data: String, key: Int, mode: String): String {
    var str = ""
    data.forEach {
        when (algoritm){
            "unicode" -> str += unicode(it, key, mode).toString()
            "shift" -> {
                when (mode) {
                    "enc" -> str += shiftEnc(it, key).toString()
                    "dec" -> str += shiftDec(it, key).toString()
                }
            }
        }
    }
    return  str
}

fun unicode(it: Char, key: Int, mode: String): Char {
    return when (mode) {
        "enc" -> it + key
        "dec" -> it - key
        else -> it + key
    }
}


fun shiftDec(ch: Char, key: Int): Char{
    var isUpp = ch.isUpperCase()
    var rez =  if (ch.isLetter()) {
        if (ch.lowercaseChar() - 'a'< key) 'z'- (key + ('a' - ch.lowercaseChar() - 1))
        else ch.lowercaseChar() - key
    } else ch
    return if (isUpp) rez.uppercaseChar() else rez

}
fun shiftEnc(ch: Char, key: Int): Char{
    val alphabet = "abcdefghijklmnopqrstuvwxyz"
    var isUpp = ch.isUpperCase()
    var rez =  if (ch.isLetter()) {
        if ( 'z' - ch.lowercaseChar() < key) 'a'+ (key -('z' - ch.lowercaseChar()) - 1)
        else ch.lowercaseChar() + key
    } else ch
    return if (isUpp) rez.uppercaseChar() else rez
}

