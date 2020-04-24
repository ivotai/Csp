package com.unicorn.csp.ui.act

import android.content.res.Configuration
import android.view.View
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.unicorn.csp.R
import kotlinx.android.synthetic.main.act_article_video.*

class ArticleVideoAct : BaseArticleAct() {

    override fun doAfterArticlePrepared() {
        webView.loadData(article.content, "text/html", "utf-8")
        prepareVideo()
    }

    override val layoutId = R.layout.act_article_video

    private var isPlay = false
    private var isPause = false

    private var orientationUtils: OrientationUtils? = null

    private fun prepareVideo() {
        val url = "http://csp.seafa.kjgk.xyz:8000/dist/demo.mp4"

        //增加封面
//        val imageView = ImageView(this)
//        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
//        imageView.setImageResource(R.mipmap.xxx1)

        //增加title
        videoPlayer!!.titleTextView.visibility = View.GONE
        videoPlayer!!.backButton.visibility = View.GONE

        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, videoPlayer)
        //初始化不打开外部的旋转
        orientationUtils!!.isEnable = false
        val gsyVideoOption = GSYVideoOptionBuilder()
        gsyVideoOption
//            .setThumbImageView(imageView)
            .setIsTouchWiget(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setAutoFullWithSize(false)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setUrl(url)
            .setCacheWithPlay(false)
            .setVideoTitle(article.title)
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    //开始播放了才能旋转和全屏
                    orientationUtils!!.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(
                    url: String,
                    vararg objects: Any
                ) {
                    super.onQuitFullscreen(url, *objects)
                    if (orientationUtils != null) {
                        orientationUtils!!.backToProtVideo()
                    }
                }
            }).setLockClickListener { view, lock ->
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils!!.isEnable = !lock
                }
            }.build(videoPlayer)
        videoPlayer!!.fullscreenButton
            .setOnClickListener { //直接横屏
                orientationUtils!!.resolveByClick()
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                videoPlayer!!.startWindowFullscreen(this, true, true)
            }
    }

    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils!!.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        videoPlayer!!.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        videoPlayer!!.currentPlayer.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            videoPlayer!!.currentPlayer.release()
        }
        if (orientationUtils != null) orientationUtils!!.releaseListener()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            videoPlayer!!.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }

}