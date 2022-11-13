package app.allever.android.sample.jetpack.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.paging.cachedIn
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.allever.android.lib.common.function.network.AppRepository
import app.allever.android.lib.common.function.network.reponse.ArticleData
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.function.paging.AbstractPagingSource
import app.allever.android.lib.core.function.paging.PagingHelper
import app.allever.android.lib.core.function.paging.PagingHelper.createPagingDiffCallback
import app.allever.android.lib.core.util.TimeUtils
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.sample.jetpack.BR
import app.allever.android.sample.jetpack.R
import app.allever.android.sample.jetpack.databinding.FragmentPagingBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Jetpack新成员，Paging3从吐槽到真香
 *
 * https://mp.weixin.qq.com/s/qBxhtgjLluUZsHCinNHUAQ
 */
class PagingFragment : BaseMvvmFragment<FragmentPagingBinding, PagingViewModel>() {

    private val articleAdapter = ArticleAdapter()

    override fun getMvvmConfig() = MvvmConfig(R.layout.fragment_paging, BR.pagingVM)

    override fun init() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mBinding.recyclerView.adapter = articleAdapter
        lifecycleScope.launch {
            mViewModel.getPagingData().collect { pagingData ->
                articleAdapter.submitData(pagingData)
            }
        }
        articleAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
//                    mBinding.recyclerView.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
//                    mBinding.recyclerView.visibility = View.INVISIBLE
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    toast("Load Error: ${state.error.message}")
                }
            }
        }
    }
}

class PagingViewModel : BaseViewModel() {
    override fun init() {

    }

    fun getPagingData(): Flow<PagingData<ArticleData>> {
        return PagingHelper.getPager(ArticlePagingSource()).cachedIn(viewModelScope)
    }
}

class ArticlePagingSource : AbstractPagingSource<ArticleData>() {
    override suspend fun getData(currentPage: Int): MutableList<ArticleData> {
        return (AppRepository.getHomePageArticleList(currentPage).data?.datas
            ?: mutableListOf()) as MutableList<ArticleData>
    }
}

class ArticleAdapter :
    PagingDataAdapter<ArticleData, ArticleAdapter.ViewHolder>(createPagingDiffCallback { old, new ->
        old.id == new.id
    }) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvSort: TextView = itemView.findViewById(R.id.tvSort)
        val tvUser: TextView = itemView.findViewById(R.id.tvUser)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_article_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.tvTitle.text = data.title
            holder.tvTime.text = TimeUtils.formatTime(data.publishTime, TimeUtils.FORMAT_yyyy_MM_dd)
            holder.tvSort.text = "${data.superChapterName} - ${data.chapterName}"
            holder.tvUser.text = when {
                data.author.isNotEmpty() -> {
                    data.author
                }
                data.shareUser.isNotEmpty() -> {
                    data.shareUser
                }
                else -> {
                    ""
                }
            }
        }
    }
}