package br.com.rosait.githubrepository.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.rosait.githubrepository.R
import br.com.rosait.githubrepository.common.pagination.PaginationScrollListener
import br.com.rosait.githubrepository.common.util.ProgressUtil
import br.com.rosait.githubrepository.data.repository.GithubRepository
import br.com.rosait.githubrepository.viewmodel.RepositoryViewModel
import br.com.rosait.githubrespositories.data.model.RepositoryItem

class RepositoryActivity : AppCompatActivity() {

    companion object {
        const val PAGE_START = 1
    }

    //TODO: usar DI para o ViewModel
    private val repositoryItemsRecyclerView: RecyclerView by lazy { findViewById(R.id.rvRepositoryItems) }
    private val swipeRefreshLayout: SwipeRefreshLayout by lazy { findViewById(R.id.swipeRefresh) }
    private val viewModel: RepositoryViewModel by lazy { RepositoryViewModel(application, GithubRepository()) }
    private val adapter: RepositoryAdapter by lazy { RepositoryAdapter() }
    private var isLoading = false
    private var isLastPage = false
    private var totalPages = 0
    private var currentPage = PAGE_START

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)
        prepareViews()
    }

    private fun prepareViews() {
        supportActionBar?.title = getString(R.string.app_name)

        viewModel.repositoryItemItem.observe(this) { repositories ->
            loadRepositories(repositories)
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        repositoryItemsRecyclerView.layoutManager = layoutManager
        repositoryItemsRecyclerView.itemAnimator = DefaultItemAnimator()
        repositoryItemsRecyclerView.adapter = adapter

        repositoryItemsRecyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                loadNextPage()
            }

            override fun getTotalPageCount(): Int {
                return totalPages
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })

        isLastPage = false
        loadFirstPage()

        swipeRefreshLayout.setOnRefreshListener(this::doRefresh)
    }

    private fun loadFirstPage() {
        ProgressUtil.showProgressDialog(this)
        Handler(Looper.getMainLooper()).postDelayed({ viewModel.fetchRepositories(PAGE_START) }, 1000)

        viewModel.countTotalPages.observe(this) { countTotalPages ->
            totalPages = countTotalPages
        }
    }

    private fun loadNextPage() {
        Handler(Looper.getMainLooper()).postDelayed({ viewModel.fetchRepositories(currentPage) }, 1000)
    }

    private fun doRefresh() {
        adapter.getRepositoryItems().clear()
        isLastPage = false
        adapter.notifyDataSetChanged()
        loadFirstPage()
        swipeRefreshLayout.isRefreshing = false
    }

    fun loadRepositories(repositoryItems: List<RepositoryItem>) {
        ProgressUtil.hideProgressDialog()

        if(currentPage.equals(PAGE_START)) {
            if(isLoading) isLoading = false
            adapter.addAll(repositoryItems)
            if (currentPage <= totalPages) adapter.addLoadingFooter() else isLastPage = true
        } else {
            adapter.removeLoadingFooter()
            isLoading = false
            adapter.addAll(repositoryItems)
            if(currentPage != totalPages) adapter.addLoadingFooter() else isLastPage = true
        }
    }
}