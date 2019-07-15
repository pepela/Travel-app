package com.peranidze.data.user.interactor

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.repository.UserRepository
import com.peranidze.data.test.factory.DataFactory.Factory.randomUuid
import com.peranidze.data.user.model.Role
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreateUserUseCaseTest {
    companion object {
        private val LOGIN = randomUuid()
        private val EMAIL = randomUuid()
        private val ROLES = listOf(Role.REGULAR)
        private val params = CreateUserUseCase.Params(LOGIN, EMAIL, ROLES)
    }

    private val userRepository = mock<UserRepository>()
    private val threadExecutor = mock<ThreadExecutor>()
    private val postExecutionThread = mock<PostExecutionThread>()

    private val createUserUseCase = CreateUserUseCase(userRepository, threadExecutor, postExecutionThread)

    @Test
    fun `createUserUseCase calls repository`() {
        createUserUseCase.buildUseCase(params)

        verify(userRepository).createUser(LOGIN, EMAIL, ROLES)
    }
}
