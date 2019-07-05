package com.peranidze.data.executor

import io.reactivex.Scheduler

interface PostExecutionThread {

    val scheduler: Scheduler

}
