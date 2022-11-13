package app.allever.android.sample.jetpack.ui

import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.function.network.AppRepository
import app.allever.android.lib.common.function.network.reponse.ArticleData
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.function.paging.BasePagingSource
import app.allever.android.lib.core.function.paging.PagingHelper.createPagingDiffCallback
import app.allever.android.lib.core.util.TimeUtils
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.lib.widget.recycler.BasePagingAdapter
import app.allever.android.lib.widget.recycler.BaseViewHolder
import app.allever.android.sample.jetpack.BR
import app.allever.android.sample.jetpack.R
import app.allever.android.sample.jetpack.databinding.FragmentPagingBinding
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
            mViewModel.getPagingFlowData(ArticlePagingSource()).collect { pagingData ->
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
}

class ArticlePagingSource : BasePagingSource<ArticleData>() {
    override suspend fun getData(currentPage: Int): MutableList<ArticleData> {
        return (AppRepository.getHomePageArticleList(currentPage).data?.datas
            ?: mutableListOf()) as MutableList<ArticleData>
    }
}

class ArticleAdapter : BasePagingAdapter<ArticleData>(
    R.layout.rv_article_item,
    createPagingDiffCallback { old, new ->
        old.id == new.id
    }) {
    override fun bindHolder(holder: BaseViewHolder, position: Int, data: ArticleData) {
        holder.setText(R.id.tvTitle, data.title)
        holder.setText(
            R.id.tvTime,
            TimeUtils.formatTime(data.publishTime, TimeUtils.FORMAT_yyyy_MM_dd)
        )
        holder.setText(R.id.tvSort, "${data.superChapterName} - ${data.chapterName}")
        holder.setText(
            R.id.tvUser, when {
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
        )
    }
}