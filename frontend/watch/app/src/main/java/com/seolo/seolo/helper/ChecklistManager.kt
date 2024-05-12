package com.seolo.seolo.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.seolo.seolo.model.ChecklistItem

object ChecklistManager {
    private const val PREFS_FILE_NAME = "encrypted_prefs_checklist"
    private const val PREF_CHECKLIST = "checklist_data"

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

    // 체크리스트 데이터를 저장하는 메서드
    fun setChecklist(context: Context, checklist: List<ChecklistItem>) {
        val jsonData = Gson().toJson(checklist)
        getPreferences(context).edit().putString(PREF_CHECKLIST, jsonData).apply()
    }

    // 저장된 체크리스트 데이터를 가져오는 메서드
    fun getChecklist(context: Context): List<ChecklistItem>? {
        val jsonData = getPreferences(context).getString(PREF_CHECKLIST, null)
        return jsonData?.let {
            Gson().fromJson(it, Array<ChecklistItem>::class.java).toList()
        }
    }

    // 저장된 체크리스트 데이터를 삭제하는 메서드
    fun clearChecklist(context: Context) {
        getPreferences(context).edit().remove(PREF_CHECKLIST).apply()
    }
}
