/*
 * Copyright (c) 2011 - 2022, Zingaya, Inc. All rights reserved.
 */

package com.jedi1150.subagram.di

import android.content.Context
import androidx.room.Room
import com.jedi1150.subagram.data.room.SubagramDatabase
import com.jedi1150.subagram.utils.GetResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideResources(
        @ApplicationContext
        context: Context,
    ) = GetResource(context)

    @Provides
    fun provideWordDb(
        @ApplicationContext
        context: Context,
    ) = Room.databaseBuilder(
        context,
        SubagramDatabase::class.java,
        "database-subagram"
    ).build()
}
