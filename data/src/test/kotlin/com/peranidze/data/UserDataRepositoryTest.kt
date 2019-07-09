package com.peranidze.data

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.data.source.user.UserDataStore
import com.peranidze.data.source.user.UserDataStoreFactory
import com.peranidze.data.test.factory.UserFactory
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserDataRepositoryTest {

    companion object {
        private const val USER_ID = 1L
        private const val EMAIL = "mock_email"
        private const val PASSWORD = "mock_password"
        private val USER = UserFactory.makeUser()
        private val USERS = UserFactory.makeUsers()
    }

    private val remoteDataStore = mock<UserDataStore>()
    private val userDataStoreFactory = mock<UserDataStoreFactory>()

    private val userRepository: UserDataRepository = UserDataRepository(userDataStoreFactory)

    @Before
    fun setup() {
        whenever(userDataStoreFactory.getDataSource()).thenReturn(remoteDataStore)
        whenever(remoteDataStore.logInUser(any(), any())).thenReturn(Flowable.just(USER))
        whenever(remoteDataStore.signUpUser(any(), any())).thenReturn(Flowable.just(USER))
        whenever(remoteDataStore.getUsers()).thenReturn(Flowable.just(USERS))
        whenever(remoteDataStore.getUser(any())).thenReturn(Flowable.just(USER))
        whenever(remoteDataStore.updateUser(any())).thenReturn(Flowable.just(USER))
        whenever(remoteDataStore.deleteUser(any())).thenReturn(Completable.complete())
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
    fun `getUser returns user`() {
        val testObserver = userRepository.getUser(USER_ID).test()
        testObserver.assertValue(USER)
    }

    @Test
    fun `getUsers returns users`() {
        val testObserver = userRepository.getUsers().test()
        testObserver.assertValue(USERS)
    }


    @Test
    fun `updateUser returns user`() {
        val testObserver = userRepository.updateUser(USER).test()
        testObserver.assertValue(USER)
    }

    @Test
    fun `deleteUser completes`() {
        val testObserver = userRepository.deleteUser(USER_ID).test()
        testObserver.assertComplete()
    }

}
