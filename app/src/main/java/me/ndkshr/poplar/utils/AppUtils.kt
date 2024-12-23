package me.ndkshr.poplar.utils

import me.ndkshr.poplar.BuildConfig
import me.ndkshr.poplar.PoplarApplication

object AppUtils {

    fun getApplication() = PoplarApplication.appContext

    fun isDebug() = BuildConfig.DEBUG
}
