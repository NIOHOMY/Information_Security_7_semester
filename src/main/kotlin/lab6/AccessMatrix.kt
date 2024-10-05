package lab6

import lab6.entity.Action
import lab6.entity.Object
import lab6.entity.User

class AccessMatrix {
    // Хранение прав доступа
    private val rights = mutableMapOf<Class<out User>, MutableMap<Object, MutableSet<Action>>>()

    // Назначение прав доступа
    fun grantAccess(userClass: Class<out User>, obj: Object, actions: Set<Action>) {
        rights.computeIfAbsent(userClass) { mutableMapOf() }
            .computeIfAbsent(obj) { mutableSetOf() }
            .addAll(actions)
    }

    // Проверка доступа
    fun hasAccess(user: User, obj: Object, action: Action): Boolean {
        return rights[user.javaClass]?.get(obj)?.contains(action) == true
    }

    // Передача прав между пользователями
    fun transferRights(from: User, to: User, obj: Object, actions: Set<Action>) {
        if (!hasAccess(from, obj, Action.TRANSFER_RIGHTS)) {
            throw IllegalArgumentException("${from.name} does not have the right to transfer access.")
        }
        grantAccess(to.javaClass, obj, actions)
        println("${from.name} transferred access to ${to.name} for ${obj.name}: $actions")
    }
}