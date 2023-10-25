package com.example.kotlin_retfit_sample_weather

import com.example.kotlin_retfit_sample_weather.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
// 天気情報を取得するためのAPIのインターフェース
interface WeatherApi {

    // @GETアノテーションを使用して、特定のエンドポイントにHTTP GETリクエストを行うメソッドを定義
    // この場合、エンドポイントは"data/2.5/weather"です
    @GET("data/2.5/weather")
    fun fetchWeather(
        // @Queryアノテーションを使用して、リクエストのクエリパラメータを定義
        // "q"は都市の名前を表すクエリパラメータ（例：Tokyo）
        @Query("q") city: String,

        // "lang"はレスポンスの言語を指定するクエリパラメータ（例：ja）
        @Query("lang") lang: String,

        // "appid"はAPIキーを指定するクエリパラメータ
        // APIキーはOpenWeatherMapから取得したものを使用
        @Query("appid") apiKey: String
    ): Call<Weather> // レスポンスとして`Weather`オブジェクトを期待するCall型を返す
}
