package com.mydomain.expensetracker.utils

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


//having refreshToken means user is loggedIn
class TokenManager @Inject constructor(
    @ApplicationContext context: Context
) {

    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    val sharedPreferences: SharedPreferences =
        EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    val editor = sharedPreferences.edit()
    fun getRefreshToken():String?
    {
      return sharedPreferences.getString("access_token",null)
    }
    fun saveRefreshToken(accessToken:String)
    {
      editor.putString("access_token",accessToken).apply()
    }

}