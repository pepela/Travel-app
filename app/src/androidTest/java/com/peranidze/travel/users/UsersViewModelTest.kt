package com.peranidze.travel.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.data.user.interactor.GetUsersUseCase
import com.peranidze.data.user.model.User
import com.peranidze.travel.test.factory.DataFactory.Factory.randomUuid
import com.peranidze.travel.test.factory.user.UserFactory
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException


class UsersViewModelTest {

    companion object {
        private val USERS = UserFactory.makeUsers(10)
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getUsersUseCase = mock<GetUsersUseCase>()

    private val usersViewModel = UsersViewModel(getUsersUseCase)

    @Test
    fun getUsersReturnsSuccess() {
        stubGetUsers(Flowable.just(USERS))

        usersViewModel.fetchUsers()

        assertTrue(usersViewModel.getUserLiveData().value is UsersState.Success)
    }

    @Test
    fun getUsersReturnsData() {
        stubGetUsers(Flowable.just(USERS))

        usersViewModel.fetchUsers()

        assertEquals(usersViewModel.getUserLiveData().value?.data, USERS)
    }

    @Test
    fun getUsersReturnsNoErrorMessage() {
        stubGetUsers(Flowable.just(USERS))

        usersViewModel.fetchUsers()

        assertEquals(usersViewModel.getUserLiveData().value?.errorMessage, null)
    }

    @Test
    fun getUsersFailureReturnsError() {
        stubGetUsers(Flowable.error(RuntimeException()))

        usersViewModel.fetchUsers()

        assertTrue(usersViewModel.getUserLiveData().value is UsersState.Error)
    }

    @Test
    fun getUsersFailureReturnsNoData() {
        stubGetUsers(Flowable.error(RuntimeException()))

        usersViewModel.fetchUsers()

        assertEquals(usersViewModel.getUserLiveData().value?.data, null)
    }

    @Test
    fun getUsersFailureReturnsErrorMessage() {
        val errorMessage = randomUuid()
        stubGetUsers(Flowable.error(RuntimeException(errorMessage)))

        usersViewModel.fetchUsers()

        assertEquals(usersViewModel.getUserLiveData().value?.errorMessage, errorMessage)
    }

    private fun stubGetUsers(flowable: Flowable<List<User>>) {
        whenever(getUsersUseCase.execute()).thenReturn(flowable)
    }
}
