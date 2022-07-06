package com.example.dushanbe.repositories.register

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
@Entity(tableName = "form2",
 foreignKeys = [ForeignKey(
     entity = Register::class,
     parentColumns = arrayOf("id"),
     childColumns = arrayOf("id"),
     onDelete = ForeignKey.NO_ACTION,
     onUpdate = ForeignKey.NO_ACTION
 )]
)
data class Form2(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var ch_visit_date_1: Int,
    var visit_date_1: String,
    var ch_visit_date_2: Int,
    var visit_date_2: String
) : Parcelable {
    constructor() : this(-1,0, "", 0, "")
}


@Parcelize
@Keep
@Entity(tableName = "form3",
    foreignKeys = [ForeignKey(
        entity = Register::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
    )]
)
data class Form3(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var egcy_init_hospital_type: Int,
    var egcy_init_date: String,
    var egcy_init_diagnosis: String,
    var egcy_init_treatment: String,
    var egcy_init_phonogram: Int,
    var egcy_init_relatives: Int,
    var egcy_init_projects: Int,
    var egcy_init_accompanying: Int,
    var egcy_init_transport_provided: Int,
    var egcy_init_accompanying_gender: String
) : Parcelable  {
    constructor() : this(
        -1,0, "", "", "",
        0, 0, 0, 0, 0, ""
    )
}


@Parcelize
@Keep
@Entity(tableName = "form4",
    foreignKeys = [ForeignKey(
        entity = Register::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
    )]
)
data class Form4(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var rtn_accept_referral: Int,
    var ch_rtn_accept_newborn_1: Int,
    var rtn_accept_newborn_1: String
) : Parcelable  {
    constructor() : this(-1,0, 0, "")
}


@Parcelize
@Keep
@Entity(tableName = "form5",
    foreignKeys = [ForeignKey(
        entity = Register::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id"),
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
    )]
)
data class Form5(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var mlty_maternal: Int,
    var mlty_maternal_hospital_id: Int,
    var mlty_maternal_cause: String,
    var mlty_child: Int,
    var mlty_child_recorded_days: String,
    var mlty_child_hospital_id: Int,
    var mlty_child_cause: String
) : Parcelable  {
    constructor() : this(
        -1,0, 0, "", 0,
        "", -1, ""
    )
}



