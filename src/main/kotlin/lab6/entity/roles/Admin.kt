package lab6.entity.roles

import lab6.entity.User

data class Admin(override val name: String) : User(name)