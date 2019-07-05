package com.peranidze.data.source.user

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class UserDataStoreFactoryTest {

    private val remoteDataStore = mock<UserDataStore>()
    private val userDataStoreFactory = UserDataStoreFactory(remoteDataStore)

    @Test
    fun `getDataStore return remote data store`() {
        val dataStore = userDataStoreFactory.getDataSource()
        assertEquals(dataStore, remoteDataStore)
    }
}
