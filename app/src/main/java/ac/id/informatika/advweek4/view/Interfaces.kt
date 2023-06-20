package ac.id.informatika.advweek4.view

import android.view.View
import androidx.navigation.Navigation

interface ButtonDetailClickListener {
    fun onButtonDetailClick(v:View) {
        val action = StudentListFragmentDirections.actionStudentDetail((v.tag.toString()))
        Navigation.findNavController(v).navigate(action)
    }
}