package com.example.sampleapp.core.designsystem.component

/**
 * 유연한 상속을 위해서 interface를 사용함
 */
interface VerifyType

/**
 * 검증을 위한 interface
 *
 * @param type
 */
interface Verification<type : VerifyType> {
    fun verify(input: String): type

    class VerificationTypeError(msg: String) : Throwable(msg)
}

/**
 * 첫 번째 테스트 Verify Type
 */
sealed class DefaultVerifyType : VerifyType {
    object VerifyMaxInputTextError : DefaultVerifyType()
    object VerifyAlreadyExistTextError : DefaultVerifyType()
    object VerifyTextVerifyError : DefaultVerifyType()
    object VerifyOk : DefaultVerifyType()
}