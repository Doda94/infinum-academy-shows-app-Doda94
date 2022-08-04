package com.doda.shows

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    val gson = Gson()

    @TypeConverter
    fun userToString(user: User): String {
        return gson.toJson(user)
    }
    @TypeConverter
    fun stringToUser(string: String): User{
        val objectType = object : TypeToken<User>() {}.type
        return gson.fromJson(string, objectType)
    }
}