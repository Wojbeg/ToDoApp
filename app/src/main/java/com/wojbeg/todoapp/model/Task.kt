package com.wojbeg.todoapp.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.room.*
import com.wojbeg.todoapp.db.Converters
import com.wojbeg.todoapp.utils.Formatter.formatter
import com.wojbeg.todoapp.utils.Priority
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime


@Entity(tableName = "tasks")
@TypeConverters(Converters::class)
@Parcelize
data class Task(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var status: Boolean?,
    var name: String?,
    var type: String?,
    var date: OffsetDateTime?,
    var priority: Priority?

    ): Parcelable {


    private constructor(parcel: Parcel):this(
        parcel.readInt(),
        (parcel.readByte() == 1.toByte()),
        parcel.readString(),
        parcel.readString(),
        formatter.parse(parcel.readString(), OffsetDateTime::from) ,
        parcel.readString()?.let { Priority.valueOf(it) }
    )


    companion object : Parceler<Task> {
        override fun Task.write(parcel: Parcel, i: Int) {

            parcel.writeInt(id!!)
            parcel.writeByte((if (status!!) 1 else 0).toByte())
            parcel.writeString(name.toString())
            parcel.writeString(type.toString())
            val dateS = date?.format(formatter)
            parcel.writeString(date!!.format(formatter))
            parcel.writeString(priority?.name)
        }

        override fun create(parcel: Parcel): Task {

            return Task(parcel)
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false
        if (status != other.status) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (date != other.date) return false
        if (priority != other.priority) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + (priority?.hashCode() ?: 0)
        return result
    }


}