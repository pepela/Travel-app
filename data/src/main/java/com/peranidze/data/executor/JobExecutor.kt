package com.peranidze.data.executor

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class JobExecutor : ThreadExecutor {

    private val workQueue: LinkedBlockingQueue<Runnable>
    private val threadPostExecutor: ThreadPoolExecutor
    private val threadFactory: ThreadFactory

    init {
        workQueue = LinkedBlockingQueue()
        threadFactory = JobThreadFactory()
        threadPostExecutor = ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
            TIME_UNIT, workQueue, threadFactory
        )
    }

    companion object {
        const val CORE_POOL_SIZE = 3
        const val MAXIMUM_POOL_SIZE = 5
        const val KEEP_ALIVE_TIME = 10L
        val TIME_UNIT = TimeUnit.SECONDS
    }

    override fun execute(runnable: Runnable?) {
        if (runnable == null) {
            throw IllegalArgumentException("Runnable can't be null")
        }
        threadPostExecutor.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {

        private var threadCounter = 0

        companion object {
            const val THREAD_NAME = "trip_planner_"
        }

        override fun newThread(runnable: Runnable?) =
            Thread(runnable, "$THREAD_NAME+$threadCounter++")

    }

}
