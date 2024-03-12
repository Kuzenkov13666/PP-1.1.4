package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Петр", "Иванов", (byte) 28);
        userService.saveUser("Игорь", "Петров", (byte) 18);
        userService.saveUser("Иван", "Сидоров", (byte) 25);
        userService.saveUser("Сергей", "Волков", (byte) 35);
        System.out.println(userService.getAllUsers());
        userService.removeUserById(2);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
