package app.allever.android.lib.demo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.demo.databinding.FragmentCenterRvBinding
import app.allever.android.lib.demo.databinding.ItemRvCenterBinding
import app.allever.android.lib.demo.ui.widget.CenterLayoutManager
import app.allever.android.lib.demo.ui.widget.centerrv.FlingOneLinearSnapHelper
import app.allever.android.lib.mvvm.base.BaseViewModel

class CenterRvFragment: BaseFragment<FragmentCenterRvBinding, BaseViewModel>() {
    override fun inflate() = FragmentCenterRvBinding.inflate(layoutInflater)

    override fun init() {
        mBinding.apply {
            FlingOneLinearSnapHelper().attachToRecyclerView(rvFlag)
            val layoutManager = CenterLayoutManager()
            rvFlag.layoutManager = layoutManager
            val adapter = RvAdapter(mutableListOf()).apply {
                setOnItemClick(object : ItemClick {
                    override fun onClick(position: Int) {
                        rvFlag.smoothScrollBy(layoutManager.getCenterOffset(position), 0)
                    }
                })
            }
            rvFlag.adapter = adapter
            
            rvFlag.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    }
                }
            })

            val list = mutableListOf<String>()
            for (i in 1..20) {
                list.add(i.toString())
            }
            adapter.data.clear()
            adapter.data.addAll(list)
            adapter.notifyDataSetChanged()

        }

    }

    inner class ViewHolder(val binding: ItemRvCenterBinding): RecyclerView.ViewHolder(binding.root)

    inner class RvAdapter(val data: MutableList<String>): RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemRvCenterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount() = data.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.binding.apply {
                ivFlag.text = data[position]
                root.setOnClickListener {
                    itemClick?.onClick(position)
                }
            }
        }

        private var itemClick: ItemClick? = null

        fun setOnItemClick(itemClick: ItemClick) {
            this.itemClick = itemClick
        }
    }


    interface ItemClick{
        fun onClick(position: Int)
    }

}