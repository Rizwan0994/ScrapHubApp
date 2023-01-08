package com.example.scrapproject

import android.R
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.scrapproject.api.ScheduleAPI
import com.example.scrapproject.databinding.FragmentScheduleBinding
import com.example.scrapproject.models.ScheduleRequest
import com.example.scrapproject.models.ScheduleResponse
import com.example.scrapproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private val scheduleViewModel by viewModels<ScheduleViewModel>()
    private val schedulePickup:ScheduleResponse? = null

    @Inject
    lateinit var scheduleAPI: ScheduleAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items= arrayOf("FaisalabadSH1","FaisalabadSH2","FaisalabadSH3")
        val adapter= ArrayAdapter(view.context, R.layout.simple_list_item_1, items)
        binding.EdittxtSelectNearestYard.setAdapter(adapter)
        bindHandlers()
        bindObservers()
    }

    private fun bindObservers() {
        scheduleViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when(it) {
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
            }
        })
    }

    private fun bindHandlers(){

        binding.apply {
        binding.btnProceed.setOnClickListener{
        val nearestYard= EdittxtSelectNearestYard.text.toString()
        val sDate=txtSDate.text.toString()
        val sTime=txtSTime.text.toString()
        val scrapItem=txtScrapItemDetails.text.toString()
        val scheduleRequest=ScheduleRequest(nearestYard,sDate,sTime,scrapItem)
        if(schedulePickup==null){
            scheduleViewModel.createSchedule(scheduleRequest)
        }
        }


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}