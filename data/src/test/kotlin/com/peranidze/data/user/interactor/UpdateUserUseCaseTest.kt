package com.peranidze.data.user.interactor

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.peranidze.data.executor.PostExecutionThread
import com.peranidze.data.executor.ThreadExecutor
import com.peranidze.data.repository.UserRepository
import com.peranidze.data.test.factory.DataFactory.Factory.randomLong
import com.peranidze.data.test.factory.DataFactory.Factory.randomUuid
import com.peranidze.data.test.factory.UserFactory
import com.peranidze.data.user.model.Role
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UpdateUserUseCaseTest {
    companion object {
        private val USER = UserFactory.makeUser()
    }

    private val userRepository = mock<UserRepository>()
    private val threadExecutor = mock<ThreadExecutor>()
    private val postExecutionThread = mock<PostExecutionThread>()

    private val updateUserUseCase = UpdateUserUseCase(userRepository, threadExecutor, postExecutionThread)

    @Test
    fun `updateUserUseCase calls repository`() {
        updateUserUseCase.buildUseCase(USER)

        verify(userRepository).updateUser(USER)
    }
}
