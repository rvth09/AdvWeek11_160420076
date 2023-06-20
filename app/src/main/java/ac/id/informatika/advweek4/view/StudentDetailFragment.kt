package ac.id.informatika.advweek4.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ac.id.informatika.advweek4.R
import ac.id.informatika.advweek4.util.loadImage
import ac.id.informatika.advweek4.viewmodel.DetailViewModel
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class StudentDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }
    fun observeViewModel(){
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch(StudentDetailFragmentArgs.fromBundle(requireArguments()).idStudent)
        viewModel.studentsLD.observe(viewLifecycleOwner, Observer {
            val progressBarDetail = view?.findViewById<ProgressBar>(R.id.progressBarDetailFoto)
            val photoImage = view?.findViewById<ImageView>(R.id.imageView3)
            val txtId = view?.findViewById<TextInputEditText>(R.id.txtID)
            val txtName = view?.findViewById<TextInputEditText>(R.id.txtName)
            val txtBodate = view?.findViewById<TextInputEditText>(R.id.txtBodate)
            val txtPhone = view?.findViewById<TextInputEditText>(R.id.txtPhone)

            if (progressBarDetail != null) {
                photoImage?.loadImage(viewModel.studentsLD.value?.photoUrl, progressBarDetail)
            }
            txtId?.setText(viewModel.studentsLD.value?.id)
            txtName?.setText(viewModel.studentsLD.value?.name)
            txtBodate?.setText(viewModel.studentsLD.value?.dob)
            txtPhone?.setText(viewModel.studentsLD.value?.phone)

            var student = it
            val btnNotif = view?.findViewById<Button>(R.id.btnNotif)
            btnNotif?.setOnClickListener{
                Observable.timer(5, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.d("Messages", "five seconds")
                        MainActivity.showNotification(student.name.toString(), "A new notification created",
                            R.drawable.ic_baseline_person_24)
                    }
            }



        })

    }

    fun observeViewModel() {
        viewModel.studentsLD.observe(viewLifecycleOwner, Observer {
            db.student = it
        })
    }
}