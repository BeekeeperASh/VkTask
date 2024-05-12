package com.example.vktask.di

import com.example.vktask.data.api.PokemonApi
import com.example.vktask.repository.PokemonRepository
import com.example.vktask.tools.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonApi(): PokemonApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokemonApi::class.java)
    }

    @Singleton
    @Provides
    fun providePokemonRepository(pokemonApi: PokemonApi): PokemonRepository {
        return PokemonRepository(pokemonApi)
    }

}