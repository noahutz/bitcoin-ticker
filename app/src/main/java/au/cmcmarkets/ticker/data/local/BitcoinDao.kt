package au.cmcmarkets.ticker.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject

/**
 * Local persistence
 * For this demo, used shared preferences to store data
 */
class BitcoinDao @Inject constructor(
    private val preferences: SharedPreferences,
    private val gson: Gson
) {

    fun saveAll(key: String, entity: BitcoinEntity) {
        val json = gson.toJson(entity)
        preferences.edit().putString(key, json).apply()
    }

    fun loadAll(): Map<String, BitcoinEntity> {
        val hashMap = hashMapOf<String, BitcoinEntity>()
        preferences.all.map {
            try {
                val entity = gson.fromJson(it.value.toString(), BitcoinEntity::class.java)
                hashMap.put(it.key, entity)
            } catch (e: Exception) {
                null
            }
        }
        return hashMap
    }
}
