package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_ongoing.*
import kotlinx.android.synthetic.main.item_ongoing.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ongoingFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ongoingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ongoingFragment : androidx.fragment.app.Fragment(), rememoRecyclerAdapter.MyItemClickListener {
    // TODO: Rename and change types of parameters
    lateinit var myAdapter: rememoRecyclerAdapter
    private var listener1: ongoingFragment.OnRecyclerInteractionListener? = null
    private var param1: String? = null
    private var param2: String? = null
    private var listener: ongoingFragment.OnFragmentInteractionListener? = null
    private var position = -1
    private var rememo: ReminderData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAdapter = rememoRecyclerAdapter(view.context, 1)
        var layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(view.context)
        rview_ongoing.hasFixedSize()
        rview_ongoing.layoutManager = layoutManager
        myAdapter.setMyItemClickListener(this)
        rview_ongoing.adapter = myAdapter
// default Item Animator
        rview_ongoing.itemAnimator?.addDuration = 1000L
        rview_ongoing.itemAnimator?.removeDuration = 1000L
        rview_ongoing.itemAnimator?.moveDuration = 1000L
        rview_ongoing.itemAnimator?.changeDuration = 1000L
        /*view.findViewById<RadioButton>(R.id.rvDelete).setOnClickListener{

        }*/

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_ongoing, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(rememo: ReminderData) {
        listener1?.onItemClicked(rememo)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else if (context is OnRecyclerInteractionListener) {
            listener1 = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        listener1 = null
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ongoingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ongoingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClickedFromAdapter(rememo: ReminderData) {
        /*
        var memoDetailFragment:memoDetailFragment=memoDetailFragment()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.rview_ongoing, memoDetailFragment())
        transaction?.addToBackStack(null)
        transaction?.commit()
         */
        onItemClickedFromRecyclerViewFragment(rememo)
    }

    interface OnRecyclerInteractionListener {
        fun onItemClicked(rememo: ReminderData)
    }

    fun onItemClickedFromRecyclerViewFragment(rememo: ReminderData) {
        listener1?.onItemClicked(rememo)
    }
}
