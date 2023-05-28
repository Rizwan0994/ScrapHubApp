package com.example.scrapproject

import android.R
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.scrapproject.api.ScheduleAPI
import com.example.scrapproject.databinding.FragmentScheduleBinding
import com.example.scrapproject.models.ScheduleRequest
import com.example.scrapproject.models.ScheduleResponse
import com.example.scrapproject.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ScheduleFragment : Fragment() {
    private val titlesArrayList = ArrayList<String>()
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private val scheduleViewModel by viewModels<ScheduleViewModel>()
    private val schedulePickup: ScheduleResponse? = null

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

        val client = OkHttpClient()
        val items = arrayOf("FaisalabadSH1","FaisalabadSH2","FaisalabadSH2","Fast Cfd Lonywala")
        val request = Request.Builder()
            .url("https://apitestregs.onrender.com/yards")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonArray = JSONArray(response.body?.string())
                for (i in 0 until jsonArray.length()) {
                    val yard = jsonArray.getJSONObject(i)
                    titlesArrayList.add(yard.getString("name"))
                    items[i]=titlesArrayList[i]
                    println(titlesArrayList[i])


                }


            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to fetch yards: ${e.message}")
            }
        })
//        for (i in titlesArrayList.indices) {
//            items[i] = titlesArrayList[i]
//        }

        val adapter = ArrayAdapter(view.context, R.layout.simple_list_item_1, items)
        binding.EdittxtSelectNearestYard.setAdapter(adapter)

        bindHandlers()
        bindObservers()
    }


    private fun bindObservers() {
        scheduleViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
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

    private fun bindHandlers() {

        binding.apply {

            //timepicker..........
            var selectedTime = ""
            binding.btnTimeSelected.setOnClickListener {
                // Get the current hour and minute values
                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                // Create a timeSetListener to handle the user's selection of a time
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        // Do something with the selected hour and minute values
                        selectedTime = String.format("%02d:%02d", hourOfDay, minute)

                    }
                val context: Context = binding.root.context
                TimePickerDialog(context, timeSetListener, hour, minute, true).show()

            }
            //end time picker...
            binding.btnProceed.setOnClickListener {
                val nearestYard = EdittxtSelectNearestYard.text.toString()
                //date picker set
                val year = datePicker1.year
                val month = datePicker1.month
                val dayOfMonth = datePicker1.dayOfMonth

                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                val formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                val sDate = selectedDate.toString()



                //val sTime=txtSTime.text.toString()
                    val scrapItem = txtScrapItemDetails.text.toString()

                //dialog box...
                val builder = AlertDialog.Builder(context)
                if (nearestYard?.isNotEmpty() && scrapItem?.isNotEmpty() && formattedDate?.isNotEmpty() == true && selectedTime?.isNotEmpty()) {

                    builder.setTitle("Confirmation")
                    builder.setMessage("Are you sure you want to Schedule Pickup?")
                    builder.setPositiveButton("Yes") { dialog, which ->
                        // user clicked Yes
                        val scheduleRequest = ScheduleRequest(
                            nearestYard,
                            scrapItem,
                            formattedDate,
                            selectedTime
                        )
                        if (schedulePickup == null) {
                            scheduleViewModel.createSchedule(scheduleRequest)
                            Toast.makeText(context, "Pickup Scheduled!", Toast.LENGTH_SHORT).show()
                        }

                    }
                    builder.setNegativeButton("No") { dialog, which ->
                        // user clicked No
                        Toast.makeText(context, "Pickup Not Scheduled!", Toast.LENGTH_SHORT).show()
                    }
                    builder.show()

                }else {
                    Toast.makeText(context, "Some thing went wrong!", Toast.LENGTH_SHORT).show()
                }
                }


            }

        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }


}
