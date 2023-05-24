package com.example.networkex.const

object ApiConst {
    //*LG SelfCare
    //COMMON
    const val APP_TYPE = "AOS" //호출 경로
    const val ENT_ID = "APP" //호출자 아이디
    const val FREE_SERVICE = "F"
    const val PAID_SERVICE = "P"

    //SWITCH
    const val SERVICE_CODE_FORWARDING_CALL_ONLY = "LRZ0000061" //착신전환(통화만)
    const val SERVICE_CODE_FORWARDING_CALL_AND_TEXT = "LRZ0000283" //착신전환(통화와 문자)
    const val SWITCH01_HEADER_MSG_TYPE = "SWITCH01"
    const val SWITCH02_HEADER_MSG_TYPE = "SWITCH02"
    const val SWITCH03_HEADER_MSG_TYPE = "SWITCH03"
    const val SWITCH_SERVICE_CODE_CALL_ONLY = "12"
    const val SWITCH_SERVICE_CODE_CALL_AND_TEXT = "10"
    //안내설정(GuideMentFlag) cf. 1: 안내 설정 ON
    const val SWITCH01_GUIDE_COMMENT_OFF = "0" //안내 설정 OFF
    //연결유형(TraceFlag) cf. 2: 미응답 시 연결
    const val SWITCH01_TRACE_RIGHT_NOW = "1" //즉시 전환

    //SELF01
    const val SELF01_HEADER_MSG_TYPE = "SELF01"
    const val SELF01_CODE = "CM078"

    //SELF03
    const val SELF03_HEADER_MSG_TYPE = "SELF03"
    const val SELF03_CODE = "CM043"
    const val SELF03_INVOICE_TYPE_EMAIL_Y = "Y"
    const val SELF03_INVOICE_TYPE_TEXT_C = "C"

    //SELF04
    const val SELF04_HEADER_MSG_TYPE = "SELF04"
    const val SELF04_CODE = "CM556"

    //SELF05
    const val SELF05_HEADER_MSG_TYPE = "SELF05"
    const val SELF05_CODE = "CM019"

    //SELF06
    const val SELF06_HEADER_MSG_TYPE = "SELF06"
    const val SELF06_CODE = "SB638"

    //SELF07
    const val SELF07_HEADER_MSG_TYPE = "SELF07"
    const val SELF07_CODE = "SB695"
    //신청 해제 구분(entrSttsChngRsnCd)
    const val SELF07_PAUSE_REQUEST_SUS = "SUS"
    const val SELF07_PAUSE_CANCEL_RSP = "RSP"
    //상태 변경 상세 사유 코드(entrSttsChngRsnDtlCd)
    const val SELF07_REASON_ECONOMIC_EX = "EX" //경제적사유
    const val SELF07_REASON_TRAVELING_ZA = "ZA" //해외출장/여행(단기부재)
    const val SELF07_REASON_ETC = "ETC" //기타
    const val SELF07_REASON_CANCEL_CR = "CR" //해제

    //SELF08
    const val SELF08_HEADER_MSG_TYPE = "SELF08"
    const val SELF08_CODE = "BIL039"

    //SELF09
    const val SELF09_HEADER_MSG_TYPE = "SELF09"
    const val SELF09_CODE = "BIL414"

    //SELF10
    const val SELF10_HEADER_MSG_TYPE = "SELF10"
    const val SELF10_CODE = "DV168"

    //SELF11
    const val SELF11_HEADER_MSG_TYPE = "SELF11"
    const val SELF11_CODE = "DV189"
    //방문 구분 코드(vsitDv) cf. A: 내방
    const val SELF11_CONTACT_TYPE_CALL_B = "B" //전화
    //접수 구분 코드(rcptDvCd)
    const val SELF11_REASON_MISSING_1 = "1" //분실
    const val SELF11_REASON_STOLEN_2 = "2" //도난
    const val SELF11_REASON_BROKEN_3 = "3" //고장 및 파손
    const val SELF11_REASON_CANCEL_9 = "9" //해제
    //분실 대상(selUnit) cf. 2: 로밍단말기, 3:SIM
    const val SELF11_LOSS_THING_PCS_1 = "1" //PCS
    const val SELF11_LOSS_THING_USIM_5 = "5" //USIM
    //착신여부(icallPhbYn)
    const val SELF11_CALL_INCOMING_POSSIBLE_1 = "1"
    const val SELF11_CALL_INCOMING_IMPOSSIBLE_0 = "0"

    //SELF12
    const val SELF12_HEADER_MSG_TYPE = "SELF12"
    const val SELF12_CODE = "ARC233"
    //요금 완납 여부(chrgCpayYn) cf. O: 미납
    const val SELF12_PAYMENT_STATUS_FULL_PAYMENT_C = "C" //완납

    //SELF19
    const val SELF19_HEADER_MSG_TYPE = "SELF19"
    const val SELF19_CODE = "SM483"
    //신청해제구분(type) cf. U 해제
    const val SELF19_EXTRA_SERVICE_REQUEST_I = "I" //신청

    //SELF21
    const val SELF21_HEADER_MSG_TYPE = "SELF21"
    const val SELF21_CODE = "SM483"

    //SELF24
    const val SELF24_HEADER_MSG_TYPE = "SELF24"
    const val SELF24_CODE = "MPS146"
    //업무 구분 코드(wrkTypeCd) cf. 1: 무료 잔여량(dsGetEntrSvcSmry), 2: 월별 사용량(dsGetEntrMonthAlowsmry), 3: 월별 데이터 초과량(dsGetEntrSvcOver)
    const val SELF24_ALL_AMOUNT_USAGE_4 = "4" //1+2+3 (dsGetEntrSvcSmry, dsGetEntrMonthAlowsmry, dsGetEntrSvcOver)
    //서비스 유형 코드(svcTypCd)
    const val VOICE_SERVICE_VC = "VC"
    const val VIDEO_MEDIA_VM = "VM"
    const val PACKET_DATA_SERVICE_PT = "PT" //패킷 데이터
    const val TEXT_MESSAGING_SERVICE_SS = "SS" //문자
    const val BASIC_SUPPLY_Z = "Z" //기본 제공
    //상품 유형 코드(prodTypeCd) cf. B1: 할인 옵션(기본)
    const val BASIC_PLAN_A1 = "A1" //기본 요금제
    const val DEPENDENT_PLAN_A2 = "A2" //요금제종속

    //*코나카드
    //[그룹사 제공용] 비밀번호 확인
    const val KONA_CARD_CODE_SUCCESS = "000_000"
    const val KONA_CARD_CODE_PIN_NOT_MATCH = "001_045"

    //*MIS
    //LOG_IN01 cf 0000 : 로그인 성공
    const val LOG_IN01_CODE_WRONG_PARAMETER_0002 = "0002" //입력값 오류
    const val LOG_IN01_CODE_WRONG_ID_1001 = "1001" //아이디 오류로 로그인 실패
    const val LOG_IN01_CODE_WRONG_PASSWORD_1002 = "1002" //비밀 번호 오류로 로그인 실패
    const val LOG_IN01_CODE_WRONG_VERIFICATION_NUMBER_1003 = "1003" //인증 번호 오류로 인한 로그인 실패
    const val LOG_IN01_CODE_OVER_TRY_COUNT_1004 = "1004" //로그인시도횟수 초과
    const val LOG_IN01_CODE_ACCOUNT_LOCK_1005 = "1005" //계정잠금상태
    const val LOG_IN01_CODE_WITHDRAWAL_USER_1006 = "1006" //탈퇴회원
    const val LOG_IN01_CODE_UNOPENED_LINE_1007 = "1007" //미개통 회선
    const val LOG_IN01_CODE_NOT_MONA_USER_1008 = "1008" //전화 번호 로그인 시- 개통 정보 또는 가입 정보 없음
    const val LOG_IN01_CODE_OVER_VERIFICATION_TIME_1009 = "1009" //인증 시간 초과(3분)
    const val LOG_IN01_CODE_FAIL_CHECK_VERIFICATION_REQ_HISTORY_1010 = "1010" //인증 요청 내역 확인 실패

    //ACCOUNTSERCHING01
    const val ACCOUNTSEARCHING01_HEADER_MSG_TYPE = "ACCOUNTSEARCHING01"
    const val ACCOUNTSEARCHING01_REQ_TYPE_EMAIL = "EMAIL"
    const val ACCOUNTSEARCHING01_REQ_TYPE_MOBILE = "MOBILE"

    //CUSINFO01
    const val CUSINFO01_HEADER_MSG_TYPE = "CUSINFO01"

    //ADDON01
    const val ADDON01_HEADER_MSG_TYPE = "ADDON01"
    //부가서비스 구분(svcType) cf. C: 통신부가서비스, A: 제휴부가서비스, M: MONA 부가서비스
    //금엑 분류 cf. F: 무료, P: 유료
    const val ADDON01_ALL_SERVICE = "ALL"
    //카테고리(rsvctype) cf. HE: 헬스, OT: OTT, SP: 스팸/스미싱차단
    const val ADDON01_EXTRA_SERVICE_CATEGORY_ALL = "ALL" //전체
    const val ADDON01_EXTRA_SERVICE_CATEGORY_BLOCK_BL = "BL" //차단/제한
    const val ADDON01_EXTRA_SERVICE_CATEGORY_DATA_DA = "DA" //데이터
    const val ADDON01_EXTRA_SERVICE_CATEGORY_ROAMING_RO = "RO" //로밍
    const val ADDON01_EXTRA_SERVICE_CATEGORY_INFO_IN = "IN" //편의/정보저장
    const val ADDON01_EXTRA_SERVICE_CATEGORY_VOICE_CA = "CA" //음성
    const val ADDON01_EXTRA_SERVICE_CATEGORY_CONVENIENCE_CO = "CO" //통합/편의
}