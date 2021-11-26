package `in`.specmatic.core

class KeyCheck(val patternKeyCheck: KeyErrorCheck = CheckOnlyPatternKeys,
               var unexpectedKeyCheck: UnexpectedKeyCheck = ValidateUnexpectedKeys,
               private val overrideUnexpectedKeyCheck: OverrideUnexpectedKeyCheck? = ::overrideUnexpectedKeyCheck
) {
    fun disableOverrideUnexpectedKeycheck(): KeyCheck {
        return KeyCheck(patternKeyCheck, unexpectedKeyCheck, null)
    }

    fun withUnexpectedKeyCheck(unexpectedKeyCheck: UnexpectedKeyCheck): KeyCheck {
        return this.overrideUnexpectedKeyCheck?.invoke(this, unexpectedKeyCheck) ?: this
    }

    fun validate(
        pattern: Map<String, Any>,
        actual: Map<String, Any>
    ): KeyError? {
        return patternKeyCheck.validate(pattern, actual) ?: unexpectedKeyCheck.validate(pattern, actual)
    }

}

private fun overrideUnexpectedKeyCheck(keyCheck: KeyCheck, unexpectedKeyCheck: UnexpectedKeyCheck): KeyCheck {
    return KeyCheck(keyCheck.patternKeyCheck, unexpectedKeyCheck)
}

typealias OverrideUnexpectedKeyCheck = (KeyCheck, UnexpectedKeyCheck) -> KeyCheck

val DefaultKeyCheck = KeyCheck()