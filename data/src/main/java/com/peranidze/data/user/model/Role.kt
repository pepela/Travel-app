package com.peranidze.data.user.model

enum class Role(val title: String) {
    ADMIN("Admin"),
    MANAGER("Manager"),
    REGULAR("Regular user");


    companion object {
        fun toRoleEnum(enumString: String?): Role {
            enumString?.let {
                return try {
                    valueOf(enumString)
                } catch (exception: Exception) {
                    REGULAR
                }
            } ?: return REGULAR
        }
    }
}
