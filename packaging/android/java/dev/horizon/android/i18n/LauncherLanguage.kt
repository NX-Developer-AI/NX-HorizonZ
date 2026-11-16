package dev.horizon.android.i18n

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.annotation.StringRes
import dev.horizon.android.R
import java.util.Locale

enum class LauncherLanguage(val storageValue: String, val languageTag: String?, @StringRes val labelResource: Int) {
    SYSTEM("system", null, R.string.language_system_default),
    ENGLISH("en", "en", R.string.language_english),
    TURKISH("tr", "tr", R.string.language_turkish);

    companion object {
        fun fromStorageValue(value: String?): LauncherLanguage =
            entries.firstOrNull { it.storageValue == value } ?: SYSTEM
    }
}

object LauncherLocale {
    private const val PREFERENCES_NAME = "horizonz_language"
    private const val LANGUAGE_KEY = "language"

    fun readLanguage(context: Context): LauncherLanguage {
        val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return LauncherLanguage.fromStorageValue(preferences.getString(LANGUAGE_KEY, null))
    }

    fun writeLanguage(context: Context, language: LauncherLanguage) {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(LANGUAGE_KEY, language.storageValue)
            .commit()
    }

    fun wrap(base: Context): Context {
        val language = readLanguage(base)
        val tag = language.languageTag ?: return base
        val locale = Locale.forLanguageTag(tag)
        Locale.setDefault(locale)
        val configuration = Configuration(base.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocales(LocaleList(locale))
        } else {
            @Suppress("DEPRECATION")
            configuration.locale = locale
        }
        configuration.setLayoutDirection(locale)
        return base.createConfigurationContext(configuration)
    }

    fun applyAndRestart(context: Context, language: LauncherLanguage) {
        writeLanguage(context, language)
        val launchIntent = context.packageManager.getLaunchIntentForPackage(context.packageName) ?: return
        launchIntent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(launchIntent)
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}

object LauncherText {
    @Volatile
    private var localizedContext: Context? = null

    @JvmStatic
    fun attach(context: Context) {
        localizedContext = LauncherLocale.wrap(context.applicationContext ?: context)
    }

    @JvmStatic
    fun get(@StringRes resource: Int, vararg arguments: Any?): String {
        val context = localizedContext ?: return ""
        return if (arguments.isEmpty()) context.getString(resource) else context.getString(resource, *arguments)
    }
}
