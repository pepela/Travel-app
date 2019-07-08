package com.peranidze.data.user.interactor

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.repository.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetUsersUseCaseTest {

    private val userRepository = mock<UserRepository>()
    private val threadExecutor = mock<ThreadExecutor>()
    private val postExecutionThread = mock<PostExecutionThread>()

    private val getUsersUseCase = GetUsersUseCase(userRepository, threadExecutor, postExecutionThread)

    @Test
    fun `getTripsUseCase calls repository`() {
        getUsersUseCase.buildUseCase()

        verify(userRepository).getUsers()
    }
}