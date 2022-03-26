package com.example.patient.repositories.register

import androidx.annotation.Keep

@Keep
data class Form2(
    var ch_visit_date_1: Boolean,
    var visit_date_1: String,
    var ch_visit_date_2: Boolean,
    var visit_date_2: String
){
    constructor():this(false,"",false,"")
}

@Keep
data class Form3(
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
){
    constructor():this(0,"","","",
        0, 0,0,0,0,"")
}


@Keep
data class Form4(
    var rtn_accept_referral: Int,
    var ch_rtn_accept_newborn_1: Int,
    var rtn_accept_newborn_1: String
) {
    constructor():this(0,0,"")
}

@Keep
data class Form5(
    var mlty_maternal: Int,
    var mlty_maternal_hospital_id: Int,
    var mlty_maternal_cause: String,
    var mlty_child: Boolean,
    var mlty_child_recorded_days:String,
    var mlty_child_hospital_id:String,
    var mlty_child_cause:String
) {
    constructor():this(0,0,"",false,
    "","","")
}


