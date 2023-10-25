package com.example.kotlin_retfit_sample_weather

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_retfit_sample_weather.databinding.FragmentWeatherListBinding
import com.example.kotlin_retfit_sample_weather.model.Weather



class WeatherListFragment : Fragment() {
    private lateinit var binding: FragmentWeatherListBinding

    // OpenWeatherMapのAPIアクセスに必要なキー
    private val API_KEY = "自分のkey"

    // 対象となる日本の都市のリスト
    private val cities = listOf("Tokyo", "Osaka", "Kyoto", "Hiroshima", "Fukuoka", "Hokkaido", "Okinawa", "Aomori", "Nagano", "Tottori", "Nagoya")

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


        // 各都市の天気情報をAPIから取得

    }

    companion object {
        val REQUEST_WEATHER_DETAIL = "REQUEST_WEATHER_DETAIL"
        val SELECTED_WEATHER = "SELECTED_WEATHER"
    }


}