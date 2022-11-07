/*
 * Copyright (c) 2011 - 2022, Zingaya, Inc. All rights reserved.
 */

package com.jedi1150.subagram.utils

import android.content.Context

class GetResource(private val context: Context) {

    fun getString(string: Int): String = context.getString(string)

}
