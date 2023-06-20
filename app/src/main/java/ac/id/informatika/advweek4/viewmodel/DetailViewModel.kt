package ac.id.informatika.advweek4.viewmodel

import ac.id.informatika.advweek4.model.Student
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val studentsLD = MutableLiveData<Student>()
    private var queue: RequestQueue? = null
    val TAG = "volleyTag"

//    fun fetch() {
//        val student1 = Student("16055","Nonie","1998/03/28","5718444778","http://dummyimage.com/75x100.jpg/cc0000/ffffff")
//        studentLD.value = student1
//    }

    fun fetch(nrp: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://jitusolution.com/student.php?id=$nrp"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Student>>() {}.type
                val result = Gson().fromJson<Student>(it, Student::class.java)
                studentsLD.value = result
//                Log.d("showvoley", it)
                Log.d("showvoley", url)

            },
            {
                Log.d("showvoley", it.toString())
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}
