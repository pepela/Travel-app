package com.peranidze.data.user.interactor

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.repository.UserRepository
import com.peranidze.data.test.factory.DataFactory.Factory.randomLong
import com.peranidze.data.test.factory.DataFactory.Factory.randomUuid
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DeleteUserUseCaseTest {
    companion object {
        private val USER_LOGIN = randomUuid()
    }

    private val userRepository = mock<UserRepository>()
    private val threadExecutor = mock<ThreadExecutor>()
    private val postExecutionThread = mock<PostExecutionThread>()

    private val deleteUserUseCase = DeleteUserUseCase(userRepository, threadExecutor, postExecutionThread)

    @Test
    fun `deleteUserUseCase calls repository`() {
        deleteUserUseCase.buildUseCase(USER_LOGIN)

        verify(userRepository).deleteUser(USER_LOGIN)
    }
}
