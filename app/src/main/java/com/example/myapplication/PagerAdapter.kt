package layout

import android.util.Log
import com.example.myapplication.finishedFragment
import com.example.myapplication.ongoingFragment
import com.example.myapplication.trashcanFragment

class PagerAdapter(fragmentManager: androidx.fragment.app.FragmentManager)
    : androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {

     override fun getItem(pos : Int) : androidx.fragment.app.Fragment {
          when (pos) {
             0 -> {
                 return ongoingFragment()
             }
             1 -> {
                 return finishedFragment()
             }
             2 -> {
                 return trashcanFragment()
             }
         }
         return ongoingFragment()
    }

    override fun getCount(): Int {
        return 3
    }
}