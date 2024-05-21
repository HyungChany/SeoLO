package com.seolo.seolo.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.seolo.seolo.model.BasicChecklistItem
import com.seolo.seolo.model.ChecklistTextItem

object ChecklistManager {
    private const val PREFS_FILE_NAME = "encrypted_prefs_checklist"
    private const val BASIC_CHECKLISTS_KEY = "basic_checklists"
    private const val CHECKLISTS_KEY = "checklists"

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

    // 기본 체크리스트 데이터 저장
    fun setBasicChecklist(context: Context, basicChecklists: List<BasicChecklistItem>) {
        val sharedPreferences = getPreferences(context)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(basicChecklists)
        editor.putString(BASIC_CHECKLISTS_KEY, json)
        editor.apply()
    }

    // 체크리스트 텍스트 데이터 저장
    fun setChecklistText(context: Context, checklists: List<ChecklistTextItem>) {
        val sharedPreferences = getPreferences(context)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(checklists)
        editor.putString(CHECKLISTS_KEY, json)
        editor.apply()
    }

    // 저장된 기본 체크리스트 데이터 가져오기
    fun getBasicChecklist(context: Context): List<BasicChecklistItem>? {
        val jsonData = getPreferences(context).getString(BASIC_CHECKLISTS_KEY, null)
        return jsonData?.let {
            Gson().fromJson(it, Array<BasicChecklistItem>::class.java).toList()
        }
    }

    // 저장된 체크리스트 텍스트 데이터 가져오기
    fun getChecklistText(context: Context): List<ChecklistTextItem>? {
        val jsonData = getPreferences(context).getString(CHECKLISTS_KEY, null)
        return jsonData?.let {
            Gson().fromJson(it, Array<ChecklistTextItem>::class.java).toList()
        }
    }

    // 저장된 체크리스트 데이터를 삭제
    fun clearChecklist(context: Context) {
        getPreferences(context).edit().remove(BASIC_CHECKLISTS_KEY).apply()
        getPreferences(context).edit().remove(CHECKLISTS_KEY).apply()
    }
}
