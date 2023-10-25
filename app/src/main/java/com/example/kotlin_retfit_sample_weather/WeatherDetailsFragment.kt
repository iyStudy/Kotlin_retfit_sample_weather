package com.example.kotlin_retfit_sample_weather

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.kotlin_retfit_sample_weather.databinding.FragmentWeatherDetailsBinding
import com.example.kotlin_retfit_sample_weather.model.Weather
import kotlin.math.roundToInt


// 天気の詳細情報を表示するフラグメント
class WeatherDetailsFragment : Fragment() {

    // bindingを利用するときはnullでないことを保証するためのgetter
    lateinit var binding:FragmentWeatherDetailsBinding

    // フラグメントのビューが作成されるときに呼ばれるメソッド
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // ビューバインディングのインスタンスを作成し、そのルートビューを返す
        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    // フラグメントのビューが作成された後に呼ばれるメソッド
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // argumentsを用いて、フラグメントに渡された天気のデータを取得
        arguments?.let { bundle ->
            val weather = bundle.getParcelable<Weather>("selected_city")
            // 取得した天気のデータを利用して、各TextViewに情報をセット
            weather?.let {
                binding.tvCityName.text = it.name
                binding.tvWeatherDescription.text = "天気: ${it.weather[0].description}"
                binding.tvFeelsLike.text = "体感温度: ${(it.main.feels_like - 273.15).roundToInt()}°C"
                binding.tvMinTemp.text = "最低気温: ${(it.main.temp_min - 273.15).roundToInt()}°C"
                binding.tvMaxTemp.text = "最高気温: ${(it.main.temp_max - 273.15).roundToInt()}°C"
                binding.tvPressure.text = "気圧: ${it.main.pressure} hPa"
                binding.tvHumidity.text = "湿度: ${it.main.humidity}%"
                binding.tvWindSpeed.text = "風速: ${it.wind.speed} m/s"
                binding.tvClouds.text = "雲の割合: ${it.clouds.all}%"
                binding.tvCountry.text = "国: ${it.sys.country}"
            }
        }
    }

    // 新しいWeatherDetailsFragmentインスタンスを作成するためのヘルパーメソッド
    companion object {
        fun newInstance(city: Weather): WeatherDetailsFragment {
            val fragment = WeatherDetailsFragment()
            val args = Bundle()
            // 渡された天気のデータをBundleに格納
            args.putParcelable("selected_city", city)
            // そのBundleをフラグメントのargumentsとしてセット
            fragment.arguments = args
            return fragment
        }
    }
}
