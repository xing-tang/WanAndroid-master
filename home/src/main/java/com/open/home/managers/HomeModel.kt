package com.open.home.managers

import android.content.Context
import com.open.baselibrary.managers.storage.BaseModel
import com.open.baselibrary.managers.storage.IStorageService
import com.open.baselibrary.managers.storage.PreferencesStorageService
import com.open.baselibrary.managers.platform.PlatformManager

class HomeModel(
    storageService: IStorageService = PreferencesStorageService.instance,
    @Transient private val context: Context = PlatformManager.instance.applicationContext
) : BaseModel(storageService) {

    companion object {

    }
}
