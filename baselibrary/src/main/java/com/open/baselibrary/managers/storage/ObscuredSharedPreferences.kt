package com.open.baselibrary.managers.storage

import android.content.Context
import android.content.SharedPreferences
import com.open.baselibrary.managers.config.Config
import com.open.baselibrary.managers.config.ConfigManager

class ObscuredSharedPreferences(
        context: Context,
        private var delegate: SharedPreferences,
        private var config : Config = ConfigManager.instance.config) : SharedPreferences {

    private val salt = config.salt

    class Editor(private val delegateEditor: SharedPreferences.Editor, private val salt: String) : SharedPreferences.Editor {
        override fun putBoolean(key: String, value: Boolean) = apply { delegateEditor.putString(key.encrypt(salt), value.encrypt(salt)) }
        override fun putFloat(key: String, value: Float) = apply { delegateEditor.putString(key.encrypt(salt), value.encrypt(salt)) }
        override fun putInt(key: String, value: Int) = apply { delegateEditor.putString(key.encrypt(salt), value.encrypt(salt)) }
        override fun putLong(key: String, value: Long) = apply { delegateEditor.putString(key.encrypt(salt), value.encrypt(salt)) }
        override fun putString(key: String, value: String?): SharedPreferences.Editor? = apply { delegateEditor.putString(key.encrypt(salt), value?.encrypt(salt)) }
        override fun putStringSet(key: String, value: Set<String>?): SharedPreferences.Editor? = apply { delegateEditor.putStringSet(key.encrypt(salt), value?.encrypt(salt)) }
        override fun apply() = delegateEditor.apply()
        override fun clear() = apply { delegateEditor.clear() }
        override fun commit() = delegateEditor.commit()
        override fun remove(s: String) = apply { delegateEditor.remove(s) }
    }

    override fun edit(): Editor {
        return Editor(delegate.edit(), salt)
    }

    override fun getAll(): Map<String, *> {
        return delegate.all.mapValues { (_, v) ->
            when (v) {
                is Boolean -> v.decrypt(salt)
                is Float -> v.decrypt(salt)
                is Int -> v.decrypt(salt)
                is Long -> v.decrypt(salt)
                is String -> v.decrypt(salt)
                else -> null
            }
        }
    }

    private fun <T> getValue(key: String, defValue: T, cast: String.() -> T): T {
        val v: String? = delegate.getString(key.encrypt(salt), null)
        return v?.decrypt(salt)?.cast() ?: defValue
    }

    override fun getBoolean(key: String, defValue: Boolean) = getValue(key, defValue, String::toBoolean)

    override fun getFloat(key: String, defValue: Float) = getValue(key, defValue, String::toFloat)

    override fun getInt(key: String, defValue: Int) = getValue(key, defValue, String::toInt)

    override fun getLong(key: String, defValue: Long) = getValue(key, defValue, String::toLong)

    override fun getString(key: String, defValue: String?) = getValue(key, defValue, { this })

    override fun getStringSet(key: String, defValues: Set<String>?): Set<String>? {
        val stringSet: Set<String>? = delegate.getStringSet(key, null)
        return stringSet?.decrypt(salt) ?: defValues
    }

    override fun contains(key: String) = delegate.contains(key.encrypt(salt))

    override fun registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener) =
            delegate.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)


    override fun unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener) =
            delegate.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
}