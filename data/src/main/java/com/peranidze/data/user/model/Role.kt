package com.peranidze.data.user.model

enum class Role {
    REGULAR,
    MANAGER,
    ADMIN;

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
