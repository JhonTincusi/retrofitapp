package com.example.appointmentsapp.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.example.appointmentsapp.model.User
import com.google.gson.Gson

object PreferenceHelper {

    fun defaultPrefs(context: Context): SharedPreferences
            = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPrefs(context: Context, name: String): SharedPreferences
            = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * puts a value for the given [key].
     */
    operator fun SharedPreferences.set(key: String, value: Any?)
            = when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }

    /**
     * finds a preference based on the given [key].
     * [T] is the type of value
     * @param defaultValue optional defaultValue - will take a default defaultValue if it is not specified
     */
    inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T
            = when (T::class) {
        String::class -> getString(key, defaultValue as? String ?: "") as T
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
    fun clearPreferences(context: Context) {
        val prefs = defaultPrefs(context)
        prefs.edit().clear().apply()
    }
    fun showAllPreferences(context: Context) {
        val prefs = PreferenceHelper.customPrefs(context, "UserLoginPrefs")
        val allEntries = prefs.all

        if (allEntries.isEmpty()) {
            Log.d("Preferences", "No hay preferencias guardadas.")
        } else {
            for ((key, value) in allEntries) {
                Log.d("Preferences", "$key: $value")
            }
        }

        // Set value
        //val sharedPreferences = context.getSharedPreferences("UserLoginPrefs", Context.MODE_PRIVATE)

        /*
        val valorGuardado = sharedPreferences.getInt("id", -1)
        val valorGuardado2 = sharedPreferences.getString("username", "Valor por defecto si clave3 no existe")
        Log.d("SharedPreferences", "Valor de clave3: $valorGuardado")
        Log.d("SharedPreferences", "Valor de clave3: $valorGuardado2") */
    }


}