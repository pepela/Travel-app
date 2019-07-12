package com.peranidze.travel.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.peranidze.data.user.interactor.DeleteUserUseCase
import com.peranidze.data.user.interactor.GetUserUseCase
import com.peranidze.data.user.interactor.UpdateUserUseCase
import com.peranidze.data.user.model.User
import com.peranidze.travel.test.factory.DataFactory.Factory.randomUuid
import com.peranidze.travel.test.factory.user.UserFactory
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

class UserViewModelTest {

    companion object {
        private const val USER_ID = 1L
        private val USER = UserFactory.makeUser()
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getUserUseCase = mock<GetUserUseCase>()
    private val updateUserUseCase = mock<UpdateUserUseCase>()
    private val deleteUserUseCase = mock<DeleteUserUseCase>()

    private val userViewModel = UserViewModel(getUserUseCase, updateUserUseCase, deleteUserUseCase)
    

    @Test
    fun getUserReturnsSuccess() {
        stubGetUser(Flowable.just(USER))

        userViewModel.fetchUser(USER_ID)

        assertTrue(userViewModel.getUserLiveData().value is UserState.Success)
    }

    @Test
    fun getUserReturnsUser() {
        stubGetUser(Flowable.just(USER))

        userViewModel.fetchUser(USER_ID)

        assertEquals(userViewModel.getUserLiveData().value?.data, USER)
    }
    
    @Test
    fun getUserReturnsNoErrorMessage() {
        stubGetUser(Flowable.just(USER))

        userViewModel.fetchUser(USER_ID)

        assertEquals(userViewModel.getUserLiveData().value?.errorMessage, null)
    }

    @Test
    fun getUserFailureReturnsError() {
        stubGetUser(Flowable.error(RuntimeException()))

        userViewModel.fetchUser(USER_ID)

        assertTrue(userViewModel.getUserLiveData().value is UserState.Error)
    }

    @Test
    fun getUserFailureReturnsNoUser() {
        stubGetUser(Flowable.error(RuntimeException()))

        userViewModel.fetchUser(USER_ID)

        assertEquals(userViewModel.getUserLiveData().value?.data, null)
    }

    @Test
    fun getUserFailureReturnsErrorMessage() {
        val errorMessage = randomUuid()
        stubGetUser(Flowable.error(RuntimeException(errorMessage)))

        userViewModel.fetchUser(USER_ID)

        assertEquals(userViewModel.getUserLiveData().value?.errorMessage, errorMessage)
    }

    @Test
    fun updateUserReturnsSuccess() {
        stubUpdateUser(Flowable.just(USER))

        userViewModel.updateUser(USER)

        assertTrue(userViewModel.getUserLiveData().value is UserState.Success)
    }

    @Test
    fun updateUserReturnsUser() {
        stubUpdateUser(Flowable.just(USER))

        userViewModel.updateUser(USER)

        assertEquals(userViewModel.getUserLiveData().value?.data, USER)
    }

    @Test
    fun updateUserReturnsNoErrorMessage() {
        stubUpdateUser(Flowable.just(USER))

        userViewModel.updateUser(USER)

        assertEquals(userViewModel.getUserLiveData().value?.errorMessage, null)
    }

    @Test
    fun updateUserFailureReturnsError() {
        stubUpdateUser(Flowable.error(RuntimeException()))

        userViewModel.updateUser(USER)

        assertTrue(userViewModel.getUserLiveData().value is UserState.Error)
    }

    @Test
    fun updateUserFailureReturnsNoUser() {
        stubUpdateUser(Flowable.error(RuntimeException()))

        userViewModel.updateUser(USER)

        assertEquals(userViewModel.getUserLiveData().value?.data, null)
    }

    @Test
    fun updateUserFailureReturnsErrorMessage() {
        val errorMessage = randomUuid()
        stubUpdateUser(Flowable.error(RuntimeException(errorMessage)))

        userViewModel.updateUser(USER)

        assertEquals(userViewModel.getUserLiveData().value?.errorMessage, errorMessage)
    }

    @Test
    fun deleteUserReturnsSuccess() {
        stubDeleteUser(Completable.complete())

        userViewModel.deleteUser(USER_ID)

        assertTrue(userViewModel.getUserLiveData().value is UserState.Success)
    }

    @Test
    fun deleteUserSuccessHasNoData() {
        stubDeleteUser(Completable.complete())

        userViewModel.deleteUser(USER_ID)

        assertEquals(userViewModel.getUserLiveData().value?.data, null)
    }

    @Test
    fun deleteUserReturnsNoErrorMessage() {
        stubDeleteUser(Completable.complete())

        userViewModel.deleteUser(USER_ID)

        assertEquals(userViewModel.getUserLiveData().value?.errorMessage, null)
    }

    @Test
    fun deleteUserFailureReturnsError() {
        stubDeleteUser(Completable.error(RuntimeException()))

        userViewModel.deleteUser(USER_ID)

        assertTrue(userViewModel.getUserLiveData().value is UserState.Error)
    }

    @Test
    fun deleteUserFailureHasNoData() {
        stubDeleteUser(Completable.error(RuntimeException()))

        userViewModel.deleteUser(USER_ID)

        assertEquals(userViewModel.getUserLiveData().value?.data, null)
    }

    @Test
    fun deleteUserFailureReturnsErrorMessage() {
        val errorMessage = randomUuid()
        stubDeleteUser(Completable.error(RuntimeException(errorMessage)))

        userViewModel.deleteUser(USER_ID)

        assertEquals(userViewModel.getUserLiveData().value?.errorMessage, errorMessage)
    }

    private fun stubGetUser(flowable: Flowable<User>) {
        whenever(getUserUseCase.execute(any())).thenReturn(flowable)
    }

    private fun stubUpdateUser(flowable: Flowable<User>) {
        whenever(updateUserUseCase.execute(any())).thenReturn(flowable)
    }

    private fun stubDeleteUser(completable: Completable) {
        whenever(deleteUserUseCase.execute(any())).thenReturn(completable)
    }

}
