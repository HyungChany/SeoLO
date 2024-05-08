package com.seolo.seolo.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object TokenManager {
    private const val PREFS_FILE_NAME = "encrypted_prefs"
    private const val PREF_ACCESS_TOKEN = "access_token"

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

    fun setAccessToken(context: Context, token: String) {
        getPreferences(context).edit().putString(PREF_ACCESS_TOKEN, token).apply()
    }

    fun getAccessToken(context: Context): String? {
        return getPreferences(context).getString(PREF_ACCESS_TOKEN, null)
    }

    fun clearTokens(context: Context) {
        getPreferences(context).edit().clear().apply()
    }
}
