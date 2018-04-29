//import android.graphics.Bitmap
//import android.util.Base64
//import java.io.ByteArrayOutputStream
//
//package team.addon
//
//import android.graphics.Bitmap
//import android.util.Base64
//import java.io.ByteArrayOutputStream
//import java.io.File
//
//
//class KotlinUtilBase64Image{
//    companion object {
//
//    }
//    private lateinit var bytes: ByteArray
//
//    fun BitmapToByteArray(image: Bitmap):String {
//        val stream = ByteArrayOutputStream()
//        image.compress(Bitmap.CompressFormat.PNG, 90, stream)
//        val bytes = stream.toByteArray()
//
//        return Base64.encodeToString(bytes, DEFAULT_BUFFER_SIZE)
//    }
//
//    fun encoder(image: Bitmap): String{
//        val base64 = Base64.en .encodeToString(bytes)
//        return base64
//    }b
//}
//
////fun String.encodeBase64ToString(): String = String(this.toByteArray().encodeBase64())
////fun String.encodeBase64ToByteArray(): ByteArray = this.toByteArray().encodeBase64()
////fun ByteArray.encodeBase64ToString(): String = String(this.encodeBase64())
////
////fun ByteArray.encodeBase64(): ByteArray {
////    val table = (CharRange('A', 'Z') + CharRange('a', 'z') + CharRange('0', '9') + '+' + '/').toCharArray()
////    val output = ByteArrayOutputStream()
////    var padding = 0
////    var position = 0
////    while (position < this.size) {
////        var b = this[position].toInt() and 0xFF shl 16 and 0xFFFFFF
////        if (position + 1 < this.size) b = b or (this[position + 1].toInt() and 0xFF shl 8) else padding++
////        if (position + 2 < this.size) b = b or (this[position + 2].toInt() and 0xFF) else padding++
////        for (i in 0 until 4 - padding) {
////            val c = b and 0xFC0000 shr 18
////            output.write(table[c].toInt())
////            b = b shl 6
////        }
////        position += 3
////    }
////    for (i in 0 until padding) {
////        output.write('='.toInt())
////    }
////    return output.toByteArray()
//}
//
