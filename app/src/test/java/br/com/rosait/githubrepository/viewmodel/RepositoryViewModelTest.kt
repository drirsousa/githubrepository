package br.com.rosait.githubrepository.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import br.com.rosait.githubrepository.data.repository.GithubRepository
import br.com.rosait.githubrespositories.data.model.RepositoryItem
import br.com.rosait.githubrespositories.data.model.ResponseResult
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class RepositoryViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @RelaxedMockK
    lateinit var applicationMock: Application

    @MockK
    lateinit var  githubRepositoryMock: GithubRepository

    lateinit var viewModelSpy: RepositoryViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(testDispatcher)

        startKoin {
            modules(module {
                factory { githubRepositoryMock }
            })
        }

        viewModelSpy = spyk(RepositoryViewModel(applicationMock), recordPrivateCalls = true)
    }

    @After
    fun tearDown() {
        confirmVerified(applicationMock, githubRepositoryMock)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        unmockkAll()
        stopKoin()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetch repositories`() = runBlockingTest {
        val listItemsMock: MutableLiveData<List<RepositoryItem>> = mockk()
        val totalCountPagesMock: MutableLiveData<Int> = mockk()
        val responseResultMock: ResponseResult = mockk()
        val itemMock: RepositoryItem = mockk()
        val repositories = listOf(itemMock)
        val language = "language:kotlin"
        val sort = "stars"
        val page = 1
        val perPage = 10
        val totalCount = 100

        every { responseResultMock.total_count } returns totalCount
        every { responseResultMock.items } returns repositories
        coEvery { githubRepositoryMock.loadRepositories(language, sort, page, perPage) } returns responseResultMock
        every { totalCountPagesMock.postValue(totalCount / perPage) } answers { nothing }
        every { viewModelSpy getProperty "_countTotalPages" } returns  totalCountPagesMock
        every { listItemsMock.postValue(repositories) } answers { nothing }
        every { viewModelSpy getProperty "_repositoryItems" } returns listItemsMock

        viewModelSpy.fetchRepositories(page)

        verify { responseResultMock.total_count }
        verify { responseResultMock.items }
        coVerify { githubRepositoryMock.loadRepositories(language, sort, page, perPage) }
        verify { totalCountPagesMock.postValue(totalCount / perPage) }
        verify { viewModelSpy getProperty "_countTotalPages" }
        verify { listItemsMock.postValue(repositories) }
        verify { viewModelSpy getProperty "_repositoryItems" }
        confirmVerified(listItemsMock, totalCountPagesMock, responseResultMock, itemMock)
    }

    @Test
    fun testGetRepositoryItemItem() {
        val listItemsMock: MutableLiveData<List<RepositoryItem>> = mockk()

        every { viewModelSpy getProperty "_repositoryItems" } returns listItemsMock

        assertEquals(listItemsMock, viewModelSpy.repositoryItemItem)
        verify { listItemsMock.equals(listItemsMock) }
        confirmVerified(listItemsMock)
    }

    @Test
    fun testGetCountTotalPages() {
        val totalCountPagesMock: MutableLiveData<Int> = mockk()

        every { viewModelSpy getProperty "_countTotalPages" } returns totalCountPagesMock

        assertEquals(totalCountPagesMock, viewModelSpy.countTotalPages)
        verify { totalCountPagesMock.equals(totalCountPagesMock) }
        confirmVerified(totalCountPagesMock)
    }
}