package com.example.dushanbe.repositories.register

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dushanbe.utils.ui.normalize
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "patient")
@Keep
@Parcelize
data class Register(
    @SerializedName("id")
    @PrimaryKey
    var id: String,
    @SerializedName("fio")
    var fio: String,
    @SerializedName("publish_date")
    var publishDate: String,
    @SerializedName("hospital_type")
    var type: Int,
    @SerializedName("birthdate")
    var birthday: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("phone_ex")
    var phoneEx: String,
    @SerializedName("passport")
    var passport: String,
    @SerializedName("address")
    var address: String,
    @SerializedName("info_menstruation")
    var infoMenstruation: String,
    @SerializedName("info_estimated_date")
    var infoEstimatedDate: String,
    @SerializedName("info_parity")
    var infoParity: Int,
    @SerializedName("info_birthpermit")
    var infoBirthPermit: Int,
    var synced: Boolean = false
) : Parcelable {
    constructor() : this(
        "", "", "", -1, "", "",
        "", "", "", "", "",
        -1, -1,false
    )

    fun toJson(): String {
        val gson = Gson()
        this.birthday.normalize()
        this.infoEstimatedDate.normalize()
        this.infoMenstruation.normalize()
        return gson.toJson(this)
    }
}

@Keep
data class RegisterResp(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("payload")
    val payload: RegisterModel?
)

@Parcelize
@Keep
data class RegisterModel(
    val accept_date: String?,
    val accept_diagnosis: String?,
    val accept_hospital_id: Int?,
    val accept_hospital_type: Int?,
    val accept_project: Int,
    val address: String,
    val birthdate: String,
    val childbirth_apgar_scale: String?,
    val childbirth_apgar_scale_total: String?,
    val childbirth_complications_child: String?,
    val childbirth_complications_mother: String?,
    val childbirth_date: String?,
    val childbirth_discharged_home: Int,
    val childbirth_emergency: Int,
    val childbirth_emergency_care: Int,
    val childbirth_gender: String,
    val childbirth_height: Double?,
    val childbirth_hospital_id: Int?,
    val childbirth_hospital_type: String?,
    val childbirth_operation_ks: String?,
    val childbirth_referral: String?,
    val childbirth_viviparity: Int?,
    val childbirth_weight: Double?,
    val code: String,
    val egcy2_acpt_date: String?,
    val egcy2_acpt_diagnosis: String?,
    val egcy2_acpt_hospital_id: Int?,
    val egcy2_acpt_hospital_type: Int?,
    val egcy2_acpt_projects: Int,
    val egcy2_init_accompanying: Int,
    val egcy2_init_accompanying_gender: String?,
    val egcy2_init_date: String?,
    val egcy2_init_diagnosis: String?,
    val egcy2_init_hospital_id: Int?,
    val egcy2_init_hospital_type: Int?,
    val egcy2_init_phonogram: Int,
    val egcy2_init_projects: Int,
    val egcy2_init_relatives: Int,
    val egcy2_init_transport_provided: Int?,
    val egcy2_init_treatment: String?,
    val egcy_acpt_date: String?,
    val egcy_acpt_diagnosis: String?,
    val egcy_acpt_hospital_id: Int?,
    val egcy_acpt_hospital_type: Int?,
    val egcy_acpt_projects: Int,
    val egcy_init_accompanying: Int,
    val egcy_init_accompanying_gender: String?,
    val egcy_init_date: String?,
    val egcy_init_diagnosis: String?,
    val egcy_init_hospital_id: Int?,
    val egcy_init_hospital_type: Int?,
    val egcy_init_phonogram: Int,
    val egcy_init_projects: Int,
    val egcy_init_relatives: Int,
    val egcy_init_transport_provided: Int?,
    val egcy_init_treatment: String?,
    val fio: String,
    val hospital_id: Int,
    val hospital_type: Int,
    val id: Int,
    val info_birthpermit: Int,
    val info_estimated_date: String?,
    val info_menstruation: String?,
    val info_parity: Int?,
    val is_mobile: Int?,
    val mlty_child: Int?,
    val mlty_child_cause: String?,
    val mlty_child_hospital_id: String?,
    val mlty_child_recorded_days: String?,
    val mlty_maternal: Int?,
    val mlty_maternal_cause: String?,
    val mlty_maternal_hospital_id: String?,
    val passport: String?,
    val phone: String?,
    val phone_ex: String?,
    val publish_date: String?,
    val region_id: Int = 0,
    val route_hospital_id: Int?,
    val rtn_accept_hospital_id: Int?,
    val rtn_accept_hospital_type: Int?,
    val rtn_accept_newborn_1:  String?,
    val rtn_accept_newborn_2:  String?,
    val rtn_accept_newborn_3:  String?,
    val rtn_accept_newborn_4:  String?,
    val rtn_accept_postpartum: Int?,
    val rtn_accept_qnt:  Int?,
    val rtn_accept_referral: Int?,
    val rtn_ini_hospital_id:  Int?,
    val rtn_ini_postpartum: Int?,
    val rtn_ini_treatment: Int?,
    val rtn_qst_leader:  String?,
    val rtn_vac_bcg:  String?,
    val rtn_vac_hepatitb:  String?,
    val rtn_vac_polio:  String?
) : Parcelable

@Parcelize
@Keep
data class Detail(
    val code: String,
    val address: String,
    val fio: String,
    val birthday: String,
    val phone: String,
    val passport: String
) : Parcelable

