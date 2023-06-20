package ac.id.informatika.advweek4.view

import ac.id.informatika.advweek4.R
import ac.id.informatika.advweek4.viewmodel.ListViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class StudentListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val studentListAdapter = StudentListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        val recyclerViewList = view.findViewById<RecyclerView>(R.id.recListStudent);

        recyclerViewList.layoutManager = LinearLayoutManager(context)
        recyclerViewList.adapter = studentListAdapter
        observeViewModel()

        view.findViewById<SwipeRefreshLayout>(R.id.refreshLayout).setOnRefreshListener {
            recyclerViewList.visibility = View.GONE
            view.findViewById<TextView>(R.id.txtError).visibility = View.GONE
            view.findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE

            viewModel.refresh()
            view.findViewById<SwipeRefreshLayout>(R.id.refreshLayout).isRefreshing = false
        }
    }

    fun observeViewModel() {
        viewModel.studentsLD.observe(viewLifecycleOwner, Observer {
            studentListAdapter.updateStudentList(it)
        })

        viewModel.studentLoadErrorLD.observe(viewLifecycleOwner, Observer {
            val errorTxt = view?.findViewById<TextView>(R.id.txtError);
            if(it == true) {
                errorTxt?.visibility = View.VISIBLE
            } else {
                errorTxt?.visibility = View.GONE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            val recyclerViewList = view?.findViewById<RecyclerView>(R.id.recListStudent);
            val progressBar = view?.findViewById<ProgressBar>(R.id.progressBar);

            if(it == true) {
                recyclerViewList?.visibility = View.GONE
                progressBar?.visibility = View.VISIBLE
            } else {
                recyclerViewList?.visibility = View.VISIBLE
                progressBar?.visibility = View.GONE
            }
        })
        
    }

}