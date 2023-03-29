package daniel.lop.io.marvelappstarter.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import daniel.lop.io.marvelappstarter.data.local.MarvelDataBase
import daniel.lop.io.marvelappstarter.data.remote.ServiceApi
import daniel.lop.io.marvelappstarter.util.Constants
import daniel.lop.io.marvelappstarter.util.Constants.BASE_URL
import daniel.lop.io.marvelappstarter.util.Constants.DATABASE_NAME
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideMarvelDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
    context,
        MarvelDataBase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun providerMarvelDao(database:MarvelDataBase) = database.marvelDao()

    @Singleton
    @Provides
    fun provideOkHttpClient() :OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder()
            .addInterceptor{chain ->
                val currentTimesTemp = System.currentTimeMillis()
                val neUrl = chain.request().url
                    .newBuilder()
                    .addQueryParameter(Constants.TS,currentTimesTemp.toString())
                    .addQueryParameter(Constants.APIKEY,Constants.PUBLIC_KEY)
                    .addQueryParameter(Constants.HASH,
                        provideToMd5Hash(currentTimesTemp.toString()+Constants.PRIVATE_KEY+Constants.PUBLIC_KEY)
                        )
                    .build()
                val newRequest = chain.request()
                    .newBuilder()
                    .url(neUrl)
                    .build()
                chain.proceed(request = newRequest)
            }
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    @Singleton
    @Provides
    fun provideServiceApi(retrofit: Retrofit):ServiceApi{
        return retrofit.create(ServiceApi::class.java)
    }

    @Singleton
    @Provides
    fun provideToMd5Hash(encrypted:String):String{
        var pas = encrypted
        var encryptedString:String ? =null
        val md5 :MessageDigest
        try {
            md5 = MessageDigest.getInstance("MD5")
            md5.update(pas.toByteArray(),0,pas.length)
            pas = BigInteger(1,md5.digest()).toString(16)
            while (pas.length<32){
                pas = "0$pas"
            }
            encryptedString = pas

        }catch (e1:NoSuchAlgorithmException){
            e1.printStackTrace()
        }

        Timber.d("hash -> $encryptedString")
        return encryptedString ?:""
    }


}