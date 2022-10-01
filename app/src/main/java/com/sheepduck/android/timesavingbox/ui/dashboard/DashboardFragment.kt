package com.sheepduck.android.timesavingbox.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sheepduck.android.timesavingbox.R
import com.sheepduck.android.timesavingbox.Task
import com.sheepduck.android.timesavingbox.TaskRepository
import com.sheepduck.android.timesavingbox.databinding.FragmentDashboardBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        displayDBInfo()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun displayDBInfo() {
        //[Android & Kotlin] View Binding はfindViewByIdの後継
        //https://akira-watson.com/android/kotlin/view-binding.html
        //TODO("LocalDate format yyyy-MM-dd 2019-07-04 is expected") //https://codechacha.com/ja/kotlin-examples-current-date-and-time/
        val currentDateTime = LocalDate.now()
        val formatter = DateTimeFormatter.ISO_DATE
        val formattedDateTime = currentDateTime.format(formatter)

        /*
        var task: Task = Task(formattedDateTime,
            binding.edttxtStartTime.text.toString(),
            binding.edttxtEndTime.text.toString(),
            binding.edttxtMemo.text.toString())
         */

        //https://qiita.com/uhooi/items/471b14cb74adadebc33c
        //FragmentにおけるActivityとContextの使い分け
        //TaskRepository.insertTask(applicationContext, task)
        //TaskRepository.insertTask(requireContext(), task)

        val arrayTask = TaskRepository.loadAllTask(requireContext())
        /*
        val singleTaskBuilder = StringBuilder()
        val i = 0
        singleTaskBuilder.append(arrayTask.get(i).id).append(",")
            .append(arrayTask.get(i).date).append(",")
            .append(arrayTask.get(i).starttime).append(",")
            .append(arrayTask.get(i).endtime).append(",")
            .append(arrayTask.get(i).memo)
            .append(System.getProperty("line.separator"))
         */

        var allTaskBuilder = StringBuilder()
        for (i in 0 until arrayTask.size) {
            val singleTaskBuilder = StringBuilder()
            singleTaskBuilder.append(arrayTask.get(i).id).append(",")
                .append(arrayTask.get(i).date).append(",")
                .append(arrayTask.get(i).starttime).append(",")
                .append(arrayTask.get(i).endtime).append(",")
                .append(arrayTask.get(i).memo)
                .append(System.getProperty("line.separator"))
            allTaskBuilder = singleTaskBuilder.append(allTaskBuilder)
        }

        binding.txtvwMsg.text = "" + getResources().getString(R.string.txtvw_got_msg) + System.getProperty("line.separator") + allTaskBuilder
    }
}