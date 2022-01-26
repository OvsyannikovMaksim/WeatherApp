package com.example.weatherapp

import android.content.Context

interface Mapper<Src, Dst> {
    fun map(src: Src, context: Context): Dst
}