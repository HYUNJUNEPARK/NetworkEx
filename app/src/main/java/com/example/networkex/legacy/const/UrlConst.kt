package com.example.networkex.legacy.const

object UrlConst {
    //URL
    const val REL_SERVER_URL = "http://220.72.230.73:8090"
    const val DEV_SERVER_URL = "http://220.72.230.41:8090"
    const val PROD_SERVER_URL = "https://api.mobilemona.co.kr"

    //KONA_CARD
    const val USER_INFO = "konai/card/oasl/api/v1/user/info"
    const val BANK_ACCOUNT_INFO = "konai/card/oasl/api/v1/bankaccounts/info"
    const val POINT_INFO = "konai/card/oasl/api/v1/point/info"
    const val CARD_LIST = "konai/card/oasl/api/v1/user/card/list"

    //SELF_CARE
    const val SELF_CARE_V1 = "real/konai/lgSelfCare/apiserver/cm/v1/selfcare-all"
    const val SELF_CARE_V2 = "real/konai/lgSelfCare/apiserver/cm/v2/apim-selfcare-all" //SELF08, 09, 12, 24
    const val SWITCH_SERVICE = "real/konai/lgSelfCare/apiserver/cc/SwitchService"

    //MIS
    const val OAUTH = "oauth/token"
    const val ACCOUNT_SEARCHING01 = "real/konai/mona/huser/hm/accountsearching"
    const val USER_ID = "api/get/userId"
    const val MEMBERSHIP_INFO = "api/membership/info"
}