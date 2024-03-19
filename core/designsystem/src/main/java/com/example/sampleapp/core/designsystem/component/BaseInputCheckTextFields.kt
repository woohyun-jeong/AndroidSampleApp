package com.example.sampleapp.core.designsystem.component

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes

interface BaseInputCheckTextFields {
    class DefaultView(
        @IdRes val textFieldId: Int,
        @IdRes val activeIndicatorId: Int,
        @IdRes val trailingIconId: Int,
    )

    enum class DefaultVerifyType {
        MaxInputTextError,
        AlreadyExistTextError,
        TextVerifyError,
        Ok
    }

    interface Style{
        fun style(@LayoutRes id: Int, defaultView: DefaultView): BaseInputCheckTextFields

        fun style(@StyleRes id: Int): BaseInputCheckTextFields
    }

    fun <VerifyType : DefaultVerifyType> verify(check: (input: String) -> VerifyType): BaseInputCheckTextFields

    fun maxLength(length: Int): BaseInputCheckTextFields

    fun inputType(): BaseInputCheckTextFields

}