package br.com.rosait.githubrepository.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.rosait.githubrepository.data.repository.GithubRepository
import br.com.rosait.githubrespositories.data.model.RepositoryItem
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class RepositoryViewModel(
    application: Application)
    : AndroidViewModel(application) {

    private val githubRepository: GithubRepository by inject(GithubRepository::class.java)

    private val _repositoryItems by lazy { MutableLiveData<List<RepositoryItem>>() }
    val repositoryItemItem: LiveData<List<RepositoryItem>> get() = _repositoryItems
    private val _countTotalPages by lazy { MutableLiveData<Int>() }
    val countTotalPages: LiveData<Int> get() = _countTotalPages

    fun fetchRepositories(page: Int) {
        viewModelScope.launch {
            val repositories = githubRepository.loadRepositories(LANGUAGE, SORT, page, PER_PAGE)
            _countTotalPages.postValue(repositories.total_count / PER_PAGE)
            _repositoryItems.postValue(repositories.items)
        }
    }

    companion object {
        const val PER_PAGE: Int = 10
        const val SORT: String = "stars"
        const val LANGUAGE: String = "language:kotlin"
    }
}