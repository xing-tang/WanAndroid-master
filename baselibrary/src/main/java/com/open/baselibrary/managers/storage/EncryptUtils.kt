package com.open.baselibrary.managers.storage

import android.util.Base64
import com.open.baselibrary.managers.sms.SmsManager
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.PBEParameterSpec

private const val UTF8 = "utf-8"

private fun getPassword() =
    Base64.encodeToString(SmsManager.getSettingsPassword(), Base64.NO_WRAP).toCharArray()

fun String.encrypt(salt: String): String {
    try {

        val bytes = toByteArray(charset(UTF8))
        val keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
        val key = keyFactory.generateSecret(PBEKeySpec(getPassword()))
        val pbeCipher = Cipher.getInstance("PBEWithMD5AndDES")
        pbeCipher.init(
            Cipher.ENCRYPT_MODE,
            key,
            PBEParameterSpec(salt.toByteArray(charset(UTF8)), 20)
        )
        return String(Base64.encode(pbeCipher.doFinal(bytes), Base64.NO_WRAP), charset(UTF8))
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

fun String.decrypt(salt: String): String {
    try {
        val bytes = Base64.decode(this, Base64.DEFAULT)
        val keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
        val key = keyFactory.generateSecret(PBEKeySpec(getPassword()))
        val pbeCipher = Cipher.getInstance("PBEWithMD5AndDES")
        pbeCipher.init(
            Cipher.DECRYPT_MODE,
            key,
            PBEParameterSpec(salt.toByteArray(charset(UTF8)), 20)
        )
        return String(pbeCipher.doFinal(bytes), charset(UTF8))
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

fun Boolean.encrypt(salt: String) = this.toString().encrypt(salt)
fun Boolean.decrypt(salt: String) = this.toString().encrypt(salt)

fun Float.encrypt(salt: String) = this.toString().encrypt(salt)
fun Float.decrypt(salt: String) = this.toString().encrypt(salt)

fun Int.encrypt(salt: String) = this.toString().encrypt(salt)
fun Int.decrypt(salt: String) = this.toString().encrypt(salt)

fun Long.encrypt(salt: String) = this.toString().encrypt(salt)
fun Long.decrypt(salt: String) = this.toString().encrypt(salt)

fun Set<String>.encrypt(salt: String) = this.map { it.encrypt(salt) }.toSet()
fun Set<String>.decrypt(salt: String) = this.map { it.decrypt(salt) }.toSet()