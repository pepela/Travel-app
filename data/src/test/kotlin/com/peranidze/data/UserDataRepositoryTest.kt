package com.peranidze.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.data.source.user.UserDataStore
import com.peranidze.data.source.user.UserDataStoreFactory
import com.peranidze.data.user.model.Role
import com.peranidze.data.user.model.User
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserDataRepositoryTest {

    companion object {
        const val EMAIL = "mock_email"
        const val PASSWORD = "mock_password"
        val USER = User(1, EMAIL, Role.REGULAR)
    }

    private val remoteDataStore = mock<UserDataStore>()
    private val userDataStoreFactory = mock<UserDataStoreFactory>()

    private val userRepository: UserDataRepository = UserDataRepository(userDataStoreFactory)

    @Before
    fun setup() {
        whenever(userDataStoreFactory.getDataSource()).thenReturn(remoteDataStore)
        whenever(remoteDataStore.logInUser(any(), any())).thenReturn(Single.just(USER))
        whenever(remoteDataStore.signUpUser(any(), any())).thenReturn(Single.just(USER))
        whenever(remoteDataStore.getUsers()).thenReturn(Single.just(listOf(USER, USER, USER)))
    }

    @Test
    fun `logInUser returns user`() {
        val testObserver = userRepository.logInUser(EMAIL, PASSWORD).test()
        testObserver.assertValue(USER)
    }

    @Test
    fun `signUp returns user`() {
        val testObserver = userRepository.signUpUser(EMAIL, PASSWORD).test()
        testObserver.assertValue(USER)
    }

    @Test
    fun `getUsers returns users`() {
        val testObserver = userRepository.getUsers().test()
        testObserver.assertValue(listOf(USER, USER, USER))
    }

}
