package au.cmcmarkets.ticker.core.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides


@Module
class DbModule {
    @Provides
    fun provideSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("bitcoin", Context.MODE_PRIVATE)
    }
}
