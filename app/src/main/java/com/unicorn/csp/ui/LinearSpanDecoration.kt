package com.unicorn.csp.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ConvertUtils

class LinearSpanDecoration(private val padding: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)


        val p16 = ConvertUtils.dp2px(16f)
        val p24 =  ConvertUtils.dp2px(24f)
        val linearLayoutManager = parent.layoutManager as LinearLayoutManager
        val position = linearLayoutManager.getPosition(view)
        outRect.left = p16
        outRect.right = p16
        outRect.top = p24 / 2
        outRect.bottom = p24/ 2

        if (position == 0) outRect.top = p24
        if (position == linearLayoutManager.itemCount - 1) outRect.bottom = p24
    }

}