package com.open.baselibrary.utils

class Optional<out T : Any>(val value: T?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Optional<*>) return false

        if (value != other.value) return false

        return true
    }

    override fun hashCode() = value?.hashCode() ?: 0

    companion object {
        @JvmStatic
        fun <V : Any> empty() = Optional<V>(null)
    }
}