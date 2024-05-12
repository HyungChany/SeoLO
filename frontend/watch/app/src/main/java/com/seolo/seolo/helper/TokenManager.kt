package com.seolo.seolo.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object TokenManager {
    private const val PREFS_FILE_NAME = "encrypted_prefs"
    private const val PREF_ACCESS_TOKEN = "access_token"
    private const val PREF_REFRESH_TOKEN = "refresh_token"
    private const val PREF_COMPANY_CODE = "company_code"
    private const val PREF_USERNAME = "username"

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

    // 액세스 토큰 설정 메서드
    fun setAccessToken(context: Context, token: String) {
        getPreferences(context).edit().putString(PREF_ACCESS_TOKEN, token).apply()
    }

    // 액세스 토큰 가져오는 메서드
    fun getAccessToken(context: Context): String? {
        return getPreferences(context).getString(PREF_ACCESS_TOKEN, null)
    }

    // 리프레시 토큰 설정 메서드
    fun setRefreshToken(context: Context, token: String) {
        getPreferences(context).edit().putString(PREF_REFRESH_TOKEN, token).apply()
    }

    // 회사 코드 설정 메서드
    fun setCompanyCode(context: Context, companyCode: String) {
        getPreferences(context).edit().putString(PREF_COMPANY_CODE, companyCode).apply()
    }

    // 회사 코드 가져오는 메서드
    fun getCompanyCode(context: Context): String? {
        return getPreferences(context).getString(PREF_COMPANY_CODE, null)
    }

    // 사용자 이름 설정 메서드
    fun setUserName(context: Context, username: String) {
        getPreferences(context).edit().putString(PREF_USERNAME, username).apply()
    }

    // 사용자 이름 가져오는 메서드
    fun getUserName(context: Context): String? {
        return getPreferences(context).getString(PREF_USERNAME, null)
    }

    // 저장된 토큰 모두 삭제하는 메서드
    fun clearTokens(context: Context) {
        getPreferences(context).edit().clear().apply()
    }
}
