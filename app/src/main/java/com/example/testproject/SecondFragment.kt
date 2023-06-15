package com.example.testproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.testproject.api.ApiInterfaces
import com.example.testproject.api.RetrofitClient
import com.example.testproject.databinding.FragmentSecondBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var coinAdapter: CoinListAdapter
    private val coinApi: ApiInterfaces.GetCoinList by lazy { RetrofitClient.getCoinList() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loader.pbLoading.visibility = View.VISIBLE
        apiCalling()
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    private fun apiCalling(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = coinApi.coinList(true).awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Coin List Updated",  response.toString())
                    if (response.isSuccessful){
                        Log.d("Coin List",  response.body().toString())
                        //Toast.makeText(context, response.body()?.message.toString() , Toast.LENGTH_SHORT).show()
                        binding.loader.pbLoading.visibility = View.GONE
                        response.body()?.let {
                            binding.apply {
                                tvCoinId.text = "ID: " + it[0].id
                                tvCoinName.text = "Name: " + it[0].name
                                tvCoinSymbol.text = "Symbol: " + it[0].symbol
                            }
                        }
                    }
                }
            }catch (e: Exception) {
                Log.d(" Error Coin ", e.toString())

            }
        }
    }

}