package lab6

import lab6.entity.Action
import lab6.entity.roles.RegularUser
import lab6.entity.roles.Admin
import lab6.entity.roles.Guest
import lab6.entity.Object


fun main() {
    val accessMatrix = AccessMatrix()

    val admin = Admin("Администратор")
    val user1 = RegularUser("Пользователь_1")
    val guest = Guest("Гость")

    val file1 = Object("Файл_1")
    val file2 = Object("Файл_2")

    // Администратору назначаются полные права на все файлы
    accessMatrix.grantAccess(admin.javaClass, file1, setOf(Action.READ, Action.WRITE, Action.TRANSFER_RIGHTS))
    accessMatrix.grantAccess(admin.javaClass, file2, setOf(Action.READ, Action.WRITE, Action.TRANSFER_RIGHTS))

    // Пользователю_1 назначаются права на чтение и запись в Файл_2 без права передачи
    accessMatrix.grantAccess(user1.javaClass, file2, setOf(Action.READ, Action.WRITE))

    // Гостю назначено право на чтение всех файлов
    accessMatrix.grantAccess(guest.javaClass, file1, setOf(Action.READ))
    accessMatrix.grantAccess(guest.javaClass, file2, setOf(Action.READ))

    try {

    } catch (e: IllegalArgumentException) {
        println(e.message)
    }
    // Проверка доступа для Пользователя_1 к Файлу_2
    if (accessMatrix.hasAccess(user1, file2, Action.READ)) {
        println("${user1.name} can read ${file2.name}")
    } else {
        println("${user1.name} cannot read ${file2.name}")
    }
    if (accessMatrix.hasAccess(user1, file2, Action.WRITE)) {
        println("${user1.name} can write to ${file2.name}")
    } else {
        println("${user1.name} cannot write to ${file2.name}")
    }

    if (accessMatrix.hasAccess(user1, file1, Action.READ)) {
        println("${user1.name} can read ${file1.name}")
    } else {
        println("${user1.name} cannot read ${file1.name}")
    }
    if (accessMatrix.hasAccess(user1, file1, Action.WRITE)) {
        println("${user1.name} can write to ${file1.name}")
    } else {
        println("${user1.name} cannot write to ${file1.name}")
    }

    // Попытка передачи прав от Пользователя_1
    try {
        accessMatrix.transferRights(user1, guest, file2, setOf(Action.READ))
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }

    // Администратор передает право на чтение Файла_2 Пользователю_1
    accessMatrix.transferRights(admin, user1, file1, setOf(Action.READ))

    if (accessMatrix.hasAccess(user1, file1, Action.READ)) {
        println("${user1.name} can read ${file1.name}")
    } else {
        println("${user1.name} cannot read ${file1.name}")
    }

    accessMatrix.transferRights(admin, user1, file2, setOf(Action.TRANSFER_RIGHTS))

    // Теперь Пользователь_1 может передать право читателю
    try {
        accessMatrix.transferRights(user1, guest, file2, setOf(Action.READ))
    } catch (e: IllegalArgumentException) {
        println(e.message)
    }

    // Проверка доступа для Гостя к Файлу_2 после передачи прав
    if (accessMatrix.hasAccess(guest, file2, Action.READ)) {
        println("${guest.name} can read ${file2.name} after transfer")
    } else {
        println("${guest.name} cannot read ${file2.name} after transfer")
    }
}