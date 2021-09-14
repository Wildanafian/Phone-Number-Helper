package com.lib.phonenumberhelper

private var tempNumber = ""

fun String.checkPhoneNumberValidity(): Boolean {
    return try {
        val regex =
            "((0852)|(0853)|(0811)|(0812)|(0813)|(0821)|(0822)|(0851)|(0817)|(0818)|(0819)|(0859)|(0877)|(0878)|(0879)|(0814)|(0815)|(0816)|(0855)|(0856)|(0857)|(0858)|(0896)|(0897)|(0898)|(0899)|(0831)|(0838)|(0881)|(0882)|(0883)|(0884))[0-9]{4,9}".toRegex()
        tempNumber = when {
            this.take(2) == "62" -> this.replaceRange(0, 2, "0")
            this.take(3) == "+62" -> this.replaceRange(0, 3, "0")
            else -> this
        }
        tempNumber.matches((regex))
    } catch (e: Exception) {
        throw e
    }
}

fun String.checkPhoneNumberISP(errorMessage: String = "Nomor tidak dikenali"): String {
    return try {
        if (this.checkPhoneNumberValidity()) {
            when (tempNumber.take(4)) {
                "0852", "0853", "0811", "0812", "0813", "0821", "0822" -> "Telkomsel"
                "0851" -> "By U"
                "0881", "0882", "0883", "0884" -> "Smartfren"
                "0831", "0838" -> "Axis"
                "0896", "0897", "0898", "0899" -> "Three"
                "0814", "0815", "0816", "0855", "0856", "0857", "0858" -> "Indosat"
                "0817", "0818", "0819", "0859", "0877", "0878", "0879" -> "XL"
                else -> errorMessage
            }
        } else {
            errorMessage
        }
    } catch (e: Exception) {
        throw e
    }
}

fun String.addPhoneNumberSeparator(spaceEvery: Int, separator: String): String {
    return try {
        this.replace(".".repeat(spaceEvery).toRegex(), "$0$separator").dropLast(1)
    } catch (e: Exception) {
        throw e
    }
}

fun String.maskPhoneNumber(maskUntil: Int, maskSymbol: String): String {
    return try {
        val regex = """^(?:\D*\d){$maskUntil}""".toRegex()
        val temp = if (this.contains("+")) this.replace("+", "")
        else this
        temp.replace(regex) { it.value.replace(Regex("""\d"""), maskSymbol) }
    } catch (e: Exception) {
        throw e
    }
}

fun String.maskPhoneNumberWithSeparator(maskUntil: Int, spaceEvery: Int, maskSymbol: String, separator: String): String {
    return try {
        val regex = """^(?:\D*\d){$maskUntil}""".toRegex()
        val temp = if (this.contains("+")) this.replace("+", "")
        else this
        temp.addPhoneNumberSeparator(spaceEvery, separator).replace(regex) { it.value.replace(Regex("""\d"""), maskSymbol) }
    } catch (e: Exception) {
        throw e
    }
}