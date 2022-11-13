package app.allever.android.sample.jetpack.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.allever.android.lib.common.function.network.AppRepository
import app.allever.android.lib.common.function.network.reponse.ArticleData
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.toast
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
        return PagingDataRepo.getHomePageArticleListPagingData().cachedIn(viewModelScope)
    }
}

class ArticlePagingSource : PagingSource<Int, ArticleData>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleData> {
        return try {
            val page = params.key ?: 1 // set page 1 as default
            log("加载第${page}页")
            val prevKey = if (page > 1) page - 1 else null
            val repoResponse = AppRepository.getHomePageArticleList(page)
            val repoItems =
                repoResponse.data?.datas ?: return LoadResult.Page(listOf(), prevKey, null)
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

object PagingDataRepo {
    fun getHomePageArticleListPagingData(): Flow<PagingData<ArticleData>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { ArticlePagingSource() }
        ).flow
    }
}

class ArticleAdapter : PagingDataAdapter<ArticleData, ArticleAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ArticleData>() {
            override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem == newItem
            }
        }
    }

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