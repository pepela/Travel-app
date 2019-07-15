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
class RegisterUserUseCaseTest {

    companion object {
        const val LOGIN = "user"
        const val EMAIL = "mock_email"
        const val PASSWORD = "mock_password"
    }

    private val userRepository = mock<UserRepository>()
    private val threadExecutor = mock<ThreadExecutor>()
    private val postExecutionThread = mock<PostExecutionThread>()

    private val signUpUserUserCase =
        RegisterUserUseCase(userRepository, threadExecutor, postExecutionThread)

    @Test
    fun `register user calls repository`() {
        signUpUserUserCase.buildUseCase(RegisterUserUseCase.Params(LOGIN, EMAIL, PASSWORD))

        verify(userRepository).register(LOGIN, EMAIL, PASSWORD)
    }

}
