package com.example.myapplication

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_ongoing.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_MOV = "idx"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [memoDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [memoDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class memoDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var rememoList: List<ReminderData>
    var rememoIndex:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)


        arguments?.let {
            rememoIndex = it.getInt(ARG_MOV)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memo_detail, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onStart() {
        var args:Bundle?=getArguments()
        if(args!=null){
            rememoIndex=args.getInt("index")
            Log.i("aa","${rememoIndex}")
        }else{
            rememoIndex=0
        }
        super.onStart()
        loadRememoInfo()
    }

    private fun loadRememoInfo() {
        val data: ReminderData
        arguments?.let{
            data = it.getSerializable(ARG_PASS)!! as ReminderData
            rvHeader.text = data.header
            rvDetail.text = data.content
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private val ARG_PASS = "Mdata"
        fun newInstance(pass: ReminderData): memoDetailFragment {
            val args: Bundle = Bundle()
            args.putSerializable(ARG_PASS, pass)
            val fragment = memoDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
