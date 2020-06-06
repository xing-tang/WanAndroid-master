package com.open.baselibrary.base

import java.io.Serializable

/**
 * 基类
 */
open class BaseResponse<T>(
    var data: T,
    var errorCode: Int,
    var errorMsg: String
) : Serializable {
    fun isSuccessful(): Boolean {
        return errorCode == 0
    }
}
