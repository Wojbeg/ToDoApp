package com.wojbeg.todoapp.db

import androidx.room.TypeConverter
import com.wojbeg.todoapp.utils.Priority
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object Converters {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun priorityToInt(priority: Priority?): Int?{
        return priority?.ordinal
    }

    @TypeConverter
    fun intToPriority(priorityInt: Int?): Priority{
        return enumValues<Priority>()[priorityInt!!]
    }

}