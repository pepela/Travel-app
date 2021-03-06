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
class GetUserUseCaseTest {
    companion object {
        private const val USER_LOGIN = "mock_user"
    }

    private val userRepository = mock<UserRepository>()
    private val threadExecutor = mock<ThreadExecutor>()
    private val postExecutionThread = mock<PostExecutionThread>()

    private val getUserUseCase = GetUserUseCase(userRepository, threadExecutor, postExecutionThread)

    @Test
    fun `getUserUseCase calls repository`() {
        getUserUseCase.buildUseCase(USER_LOGIN)

        verify(userRepository).getUser(USER_LOGIN)
    }
}
