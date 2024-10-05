package lab6.entity.roles

import lab6.entity.User

data class Guest(override val name: String) : User(name)