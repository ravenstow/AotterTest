package com.mike.aottertest.di

import com.mike.aottertest.data.BookRepository
import com.mike.aottertest.data.BookRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsBookRepository(
        bookRepository: BookRepositoryImpl
    ): BookRepository
}