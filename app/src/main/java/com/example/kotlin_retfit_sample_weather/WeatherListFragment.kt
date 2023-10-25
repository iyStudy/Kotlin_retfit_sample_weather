package com.example.kotlin_retfit_sample_weather

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_retfit_sample_weather.adapter.WeatherAdapter
import com.example.kotlin_retfit_sample_weather.databinding.FragmentWeatherListBinding
import com.example.kotlin_retfit_sample_weather.model.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherListFragment : Fragment() {
    private lateinit var binding: FragmentWeatherListBinding

    // OpenWeatherMapのAPIアクセスに必要なキー
    private val API_KEY = "自分のAPI Key"

    // 対象となる日本の都市のリスト
    private val cities = listOf("Tokyo", "Osaka", "Kyoto", "Hiroshima", "Fukuoka", "Hokkaido", "Okinawa", "Aomori", "Nagano", "Tottori",)

    // 取得した天気情報を保存するためのリスト
    private val weatherList = mutableListOf<Weather>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    // このフラグメントのビューが初めて作成されたときに呼ばれる関数
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerViewのデザインを定義するLayoutManagerを設定
        binding.rvCitylist.layoutManager = LinearLayoutManager(context)
        // APIから天気データを取得する関数を呼び出し
        fetchData()
    }


    // OpenWeatherMap APIから天気データを取得する関数
    private fun fetchData() {
        // 既存のリストのデータをクリア
        weatherList.clear()
        var completedRequests = 0  // 成功したAPIリクエストの数をカウントする変数

        // Retrofitを使用してAPI通信を行う準備
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")  // APIのエンドポイント
            .addConverterFactory(GsonConverterFactory.create())  // 受け取ったJSONをWeatherクラスに変換するためのコンバータ
            .build()
        val api = retrofit.create(WeatherApi::class.java)  // 定義したAPIインターフェースからインスタンスを作成

        // 各都市の天気情報をAPIから取得
        api.fetchWeather("Kyoto", "ja", API_KEY).enqueue(object : Callback<Weather> {
            // APIリクエストが成功したときの処理
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                response.body()?.let { weather ->
                    weatherList.add(weather)  // 取得した天気情報をリストに追加
                    completedRequests++  // 成功したリクエストの数をカウントアップ

                    // 全ての都市の天気情報が取得できた場合

                    // RecyclerViewのアダプターを設定して、取得したデータを表示
                    binding.rvCitylist.adapter =
                        WeatherAdapter(weatherList) { selectedWeather ->
                            // 選択された天気情報を格納
                            parentFragmentManager.setFragmentResult(
                                REQUEST_WEATHER_DETAIL,
                                bundleOf(SELECTED_WEATHER to selectedWeather)
                            )
                            // 各都市のアイテムをクリックした時の動作を定義
                            val fragment = WeatherDetailsFragment()
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, fragment)  // 新しいフラグメントに切り替える
                                .addToBackStack(null)  // バックスタックに追加して、戻るボタンで前のフラグメントに戻れるようにする
                                .commit()  // 変更を確定する
                        }

                }
            }

            // APIリクエストが失敗したときの処理
            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.e("API_ERROR", "Failed to fetch weather data", t)  // エラーログを出力
            }
        })
    }

    companion object {
        val REQUEST_WEATHER_DETAIL = "REQUEST_WEATHER_DETAIL"
        val SELECTED_WEATHER = "SELECTED_WEATHER"
    }


}