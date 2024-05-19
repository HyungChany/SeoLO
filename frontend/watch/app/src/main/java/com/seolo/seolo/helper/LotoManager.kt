package com.seolo.seolo.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object LotoManager{
    private const val PREFS_FILE_NAME = "session_encrypted_prefs"
    private const val PREF_LOTO_STATUS_CODE = "loto_status_code"
    private const val PREF_LOTO_UID = "loto_uid"
    private const val PREF_LOTO_MACHINE_ID = "loto_machine_id"
    private const val PREF_LOTO_BATTERY_INFO = "loto_battery_info"
    private const val PREF_LOTO_USER_ID = "loto_user_id"
    private const val PREF_TOKEN_VALUE = "token_value"

    // SharedPreferences를 안전하게 가져오는 메서드
    private fun getPreferences(context: Context): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        return EncryptedSharedPreferences.create(
            PREFS_FILE_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // LOTO 상태 코드 설정 메서드
    fun setLotoStatusCode(context: Context, statusCode: String) {
        getPreferences(context).edit().putString(PREF_LOTO_STATUS_CODE, statusCode).apply()
    }

    // LOTO 상태 코드 가져오는 메서드
    fun getLotoStatusCode(context: Context): String? {
        return getPreferences(context).getString(PREF_LOTO_STATUS_CODE, "INIT")
    }

    // LOTO UID 설정 메서드
    fun setLotoUid(context: Context, uid: String) {
        getPreferences(context).edit().putString(PREF_LOTO_UID, uid).apply()
    }

    // LOTO UID 가져오는 메서드
    fun getLotoUid(context: Context): String? {
        return getPreferences(context).getString(PREF_LOTO_UID, "")
    }

    // LOTO 머신 ID 설정 메서드
    fun setLotoMachineId(context: Context, machineId: String) {
        getPreferences(context).edit().putString(PREF_LOTO_MACHINE_ID, machineId).apply()
    }

    // LOTO 머신 ID 가져오는 메서드
    fun getLotoMachineId(context: Context): String? {
        return getPreferences(context).getString(PREF_LOTO_MACHINE_ID, "")
    }

    // LOTO 배터리 정보 설정 메서드
    fun setLotoBatteryInfo(context: Context, batteryInfo: String) {
        getPreferences(context).edit().putString(PREF_LOTO_BATTERY_INFO, batteryInfo).apply()
    }

    // LOTO 배터리 정보 가져오는 메서드
    fun getLotoBatteryInfo(context: Context): String? {
        return getPreferences(context).getString(PREF_LOTO_BATTERY_INFO, "")
    }

    // LOTO 사용자 ID 설정 메서드
    fun setLotoUserId(context: Context, userId: String) {
        getPreferences(context).edit().putString(PREF_LOTO_USER_ID, userId).apply()
    }

    // LOTO 사용자 ID 가져오는 메서드
    fun getLotoUserId(context: Context): String? {
        return getPreferences(context).getString(PREF_LOTO_USER_ID, "")
    }

    // 1회용 토큰 설정 메서드
    fun setTokenValue(context: Context, token: String) {
        getPreferences(context).edit().putString(PREF_TOKEN_VALUE, token).apply()
    }

    // 1회용 토큰 가져오는 메서드
    fun getTokenValue(context: Context): String? {
        return getPreferences(context).getString(PREF_TOKEN_VALUE, "")
    }



    // 저장된 모든 세션 정보 삭제하는 메서드
    fun clearLoto(context: Context) {
        getPreferences(context).edit().clear().apply()
    }
}
