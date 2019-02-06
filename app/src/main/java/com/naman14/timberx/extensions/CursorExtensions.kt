/*
 * Copyright (c) 2019 Naman Dwivedi.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 */
package com.naman14.timberx.extensions

import android.database.Cursor

fun Cursor?.forEach(
    closeAfter: Boolean = false,
    each: Cursor.() -> Unit
) {
    if (this == null) return
    if (moveToFirst()) {
        do {
            each(this)
        } while (moveToNext())
    }
    if (closeAfter) {
        close()
    }
}

fun <T> Cursor?.mapList(
    closeAfter: Boolean = false,
    mapper: Cursor.() -> T
): MutableList<T> {
    val result = mutableListOf<T>()
    forEach(closeAfter = closeAfter) {
        result.add(mapper(this))
    }
    return result
}

inline fun <reified T> Cursor.value(name: String): T {
    val index = getColumnIndexOrThrow(name)
    return when (T::class) {
        Short::class -> getShort(index) as T
        Int::class -> getInt(index) as T
        Long::class -> getLong(index) as T
        Boolean::class -> (getInt(index) == 1) as T
        String::class -> getString(index) as T
        Float::class -> getFloat(index) as T
        Double::class -> getDouble(index) as T
        ByteArray::class -> getBlob(index) as T
        else -> throw IllegalStateException("What do I do with ${T::class.java.simpleName}?")
    }
}