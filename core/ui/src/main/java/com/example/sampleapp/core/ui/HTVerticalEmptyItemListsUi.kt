package com.example.sampleapp.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.sampleapp.core.designsystem.base.VerifyType
import com.example.sampleapp.core.designsystem.component.DefaultVerifyTypeVerticalList
import com.example.sampleapp.core.designsystem.component.EmptyItem
import com.example.sampleapp.core.designsystem.component.EmptyItemData
import com.example.sampleapp.core.designsystem.component.HTVerticalLists
import com.example.sampleapp.core.designsystem.component.TestSampleRepository

@Composable
@Preview
fun EmptyItemPreview() {
    EmptyItem(data = EmptyItemData(0,"test6","hello world")) {
        // nothing...
    }
}
@Composable
@Preview
fun HTVerticalEmptyItemLists(out : (page: Int) -> Unit = {}) {
    val view = HTVerticalLists(
        TestSampleRepository().getAllData(),
        verification = object : HTVerticalLists.Verification<VerifyType> {
            override fun verify(input: String): VerifyType {
                return DefaultVerifyTypeVerticalList.VerifyNoDataError
            }
        },
        out)
    view.OnDraw()
}