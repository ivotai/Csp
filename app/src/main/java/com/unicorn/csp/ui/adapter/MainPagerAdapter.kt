package com.unicorn.csp.ui.adapter

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.unicorn.csp.app.Title
import com.unicorn.csp.ui.fra.ArticleFra
import com.unicorn.csp.ui.fra.TopicFra

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
         val titles = listOf("政策规定", "工作动态", "学习资料", "专题研究", "技术标准", "互动专区")
    }

    override fun getItem(pos: Int) = if (pos != titles.size - 1) ArticleFra().apply {
        arguments = Bundle().apply {
            putString(Title, titles[pos])
        }
    }
    else TopicFra()

    override fun getCount() = titles.size


}