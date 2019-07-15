package com.peranidze.travel.utils

import java.util.*

class DateUtils {
    companion object {
        fun getFirstDayOfNextMonth(): Date {
            val calendar = Calendar.getInstance()

            calendar.add(Calendar.MONTH, 1)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY))
            calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE))
            calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND))

            return calendar.time
        }

        fun getLastDayOfNextMonth(): Date {
            val calendar = Calendar.getInstance()

            calendar.add(Calendar.MONTH, 1)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY))
            calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE))
            calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND))

            return calendar.time
        }
    }
}
