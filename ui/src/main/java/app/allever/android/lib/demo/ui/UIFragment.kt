package app.allever.android.lib.demo.ui

import android.content.Context
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.FragmentUiBinding
import app.allever.android.lib.demo.ui.autolayout.AutoAdapter
import app.allever.android.lib.demo.ui.widget.OverlapManager
import app.allever.android.lib.demo.util.TextAndPictureUtil
import app.allever.android.lib.mvvm.base.BaseViewModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import eightbitlab.com.blurview.RenderScriptBlur

class UIFragment : BaseFragment<FragmentUiBinding, UIViewModel>() {
    override fun init() {
        mBinding.pressLikeView.setOnClickListener {
            mBinding.pressLikeView.show()
        }

        //毛玻璃效果
        mBinding.blurView.setupWith(
            mBinding.blurBg,
            RenderScriptBlur(requireContext())
        ) // or RenderEffectBlur
            .setBlurRadius(20f)
        mBinding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        mBinding.blurView.clipToOutline = true

        mBinding.blurBg.setOnClickListener {
            ActivityHelper.startActivity<BlurActivity>()
        }


        //自动滚动的RecyclerView
        val listData = mutableListOf<String>()
        for (i in 0..10) {
            listData.add("主播你好啵${i + 1}")
        }
        val autoScrollAdapter = AutoScrollAdapter()
        mBinding.autoScrollRecyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.autoScrollRecyclerView.adapter = autoScrollAdapter
        autoScrollAdapter.data = listData
        mBinding.autoScrollRecyclerView.setScrollOffset(DisplayHelper.dip2px(48))
        mBinding.autoScrollRecyclerView.start()

        //标签换行TextView
        val text = TextAndPictureUtil.getText(
            context,
            "面对同事的请求从来不拒绝，自己却总是在夜晚懊悔,为什么现在的年轻,人都不愿意社交了？",
            R.drawable.default_tag,
            DisplayHelper.dip2px(26),
            DisplayHelper.dip2px(15)
        )
        mBinding.tvTagAutoChangeLine.text = text

        //折叠RecyclerViewItem
        mBinding.recyclerView2.layoutManager = OverlapManager()
        mBinding.recyclerView2.adapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_image, listData) {
                override fun convert(holder: BaseViewHolder, item: String) {}
            }

        //自动换行标签布局
       initAutoLayout()


    }

    private fun initAutoLayout() {
        val list = ArrayList<String>()
        list.add("3333")
        list.add("1")
        list.add("22222222222222222222222222222222222222222222")
        list.add("清空万里")
        list.add("两条相交线")
        list.add("短途")
        list.add("希望在明天会更好")

        val autoAdapter = MyAutoAdapter(requireContext())
        autoAdapter.setOnItemClickListener(object : MyAutoAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, text: String?) {
                toast(text)
            }
        })
        autoAdapter.setData(list)
        mBinding.autoLayout.setAdapter(autoAdapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.autoScrollRecyclerView.stop()
    }

    override fun inflate() = FragmentUiBinding.inflate(layoutInflater)
}

class UIViewModel : BaseViewModel() {
    override fun init() {

    }
}

class MyAutoAdapter(private val mContext: Context) : AutoAdapter() {
    private val mList: MutableList<String> = ArrayList()
    private var mListener: OnItemClickListener? = null
    fun setData(list: ArrayList<String>?) {
        mList.clear()
        mList.addAll(list!!)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
         return mList.size
    }


    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int): View {
        val view = View.inflate(mContext, R.layout.item_tag, null)
        val tv = view.findViewById<View>(R.id.tv) as TextView
        tv.text = mList[position]
        if (mListener != null) {
            tv.setOnClickListener { v -> mListener!!.onItemClick(v, mList[position]) }
        }
        return view
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, text: String?)
    }
}

