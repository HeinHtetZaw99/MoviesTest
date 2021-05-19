package co.daniel.moviegasm.datasources.cache

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import co.daniel.moviegasm.R
import javax.inject.Inject

/**
 * created by Daniel McCoy @ 28th, May , 2019
 */
class SharePrefUtils @Inject constructor(private val context: Context) {
    private val PREFERENCES_NAME = "moviegasm-pref"

    private val sharedPreferences: SharedPreferences =
        EncryptedSharedPreferences.create(
            PREFERENCES_NAME, MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC), context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )


    fun delete(key: KEYS) {
        sharedPreferences.edit().putString(context.getString(key.label), key.defaultValue).apply()
    }

    fun delete(key: KEYB) {
        sharedPreferences.edit().putBoolean(context.getString(key.label), key.defaultValue).apply()
    }

    fun delete(key: KEYI) {
        sharedPreferences.edit().putInt(context.getString(key.label), key.defaultValue).apply()
    }

    fun save(key: KEYS, value: String) {
        sharedPreferences.edit().putString(context.getString(key.label), value).apply()
    }

    fun load(key: KEYS): String? {
        return sharedPreferences.getString(context.getString(key.label), key.defaultValue)
    }

    fun save(key: KEYI, value: Int) {
        sharedPreferences.edit().putInt(context.getString(key.label), value).apply()
    }

    fun load(key: KEYI): Int {
        return sharedPreferences.getInt(context.getString(key.label), key.defaultValue)
    }

    fun save(key: KEYB, value: Boolean) {
        sharedPreferences.edit().putBoolean(context.getString(key.label), value).apply()
    }

    fun load(key: KEYB): Boolean {
        return sharedPreferences.getBoolean(context.getString(key.label), key.defaultValue)
    }

    fun delete(key: KEYL) {
        sharedPreferences.edit().putLong(context.getString(key.label), key.defaultValue).apply()
    }

    fun save(key: KEYL, value: Long) {
        sharedPreferences.edit().putLong(context.getString(key.label), value).apply()
    }

    fun load(key: KEYL): Long {
        return sharedPreferences.getLong(context.getString(key.label), key.defaultValue)
    }

    /**
     * Define your keys here and set Default Values here
     * In case if u had more SP_VAlUES , just modify here
     */

    enum class KEYS(val label: Int, val defaultValue: String) {
        ACCESS_TOKEN(R.string.pref_access_token, "")
    }

    enum class KEYB(val label: Int, val defaultValue: Boolean) {

    }

    enum class KEYI(val label: Int, val defaultValue: Int) {

    }

    enum class KEYL(val label: Int, val defaultValue: Long) {

    }

}