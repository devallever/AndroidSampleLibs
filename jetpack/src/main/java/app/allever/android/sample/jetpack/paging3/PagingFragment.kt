package app.allever.android.sample.jetpack.paging3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.function.network.AppRepository
import app.allever.android.lib.common.function.network.reponse.ArticleData
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.function.paging.BasePagingSource
import app.allever.android.lib.core.function.paging.PagingHelper.createPagingDiffCallback
import app.allever.android.lib.core.util.TimeUtils
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.widget.recycler.binding.BaseBindingViewHolder
import app.allever.android.lib.widget.recycler.binding.BasePagingBindingAdapter
import app.allever.android.sample.jetpack.R
import app.allever.android.sample.jetpack.databinding.FragmentPagingBinding
import app.allever.android.sample.jetpack.databinding.RvArticleItemBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.coroutines.launch

/**
 * Jetpack新成员，Paging3从吐槽到真香
 *
 * https://mp.weixin.qq.com/s/qBxhtgjLluUZsHCinNHUAQ
 */
class PagingFragment : BaseMvvmFragment<FragmentPagingBinding, PagingViewModel>() {

    private val articleAdapter = ArticleBindingAdapter()


    override fun inflate() = FragmentPagingBinding.inflate(layoutInflater)

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

class ArticleBindingAdapter :
    BasePagingBindingAdapter<ArticleData, RvArticleItemBinding>(
        R.layout.rv_article_item,
        createPagingDiffCallback { old, new ->
            old.id == new.id
        }) {

    override fun inflate(parent: ViewGroup?): RvArticleItemBinding {
        return RvArticleItemBinding.inflate(LayoutInflater.from(App.context), parent, false)
    }

    override fun convert(
        holder: BaseBindingViewHolder<RvArticleItemBinding>,
        position: Int,
        item: ArticleData
    ) {
        holder.binding.tvTitle.text = item.title
        holder.binding.tvTime.text =
            TimeUtils.formatTime(item.publishTime, TimeUtils.FORMAT_yyyy_MM_dd)
        holder.binding.tvSort.text = "${item.superChapterName} - ${item.chapterName}"
        holder.binding.tvUser.text = when {
            item.author.isNotEmpty() -> {
                item.author
            }
            item.shareUser.isNotEmpty() -> {
                item.shareUser
            }
            else -> {
                ""
            }
        }
    }
}