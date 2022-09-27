package com.test.idea;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;

public class sql {
    public static void TablesOutput () throws SQLException, ClassNotFoundException {
        //регистрируем драйвер для дальнейшей работы (управление jdbc)
        //КЛАСС DriverManager, МЕТОД registerDriver
        DriverManager.registerDriver(new Driver());

        //имя драйвера
        //КЛАСС Class, МЕТОД forName
        Class.forName("com.mysql.cj.jdbc.Driver");

        //пытаемся установить соединение с заданным url бд
        //ОБЪЕКТ Connection для работы с бд
        //КЛАСС DriverManager, МЕТОД getConnection
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
        System.out.println("Успешно законнектились к БД!");

        //ОБЪЕКТ statement для выполнения sql запросов
        //МЕТОД createStatement ОБЪЕКТА Statement создает этот объект для работы с бд
        Statement stmt = con.createStatement();

        //ОБЪЕКТ ResultSet - объект java, содержащий результаты выполнения sql запросов
        //МЕТОД executeQuery ОБЪЕКТА ResultSet
        ResultSet rs = stmt.executeQuery("Show tables");
        System.out.println("Таблицы из текущей БД: ");

        //МЕТОД rs.next() ОБЪЕКТА ResultSet - построчный вывод названий таблиц в цикле
        while (rs.next()) {
            System.out.print(rs.getString(1));
            System.out.println();
        }
    }

    public static void CreatingSQLTable (String tablename, String columns_type) throws SQLException, ClassNotFoundException {
        //регистрируем драйвер для дальнейшей работы (управление jdbc)
        //КЛАСС DriverManager, МЕТОД registerDriver
        DriverManager.registerDriver(new Driver());

        //имя драйвера
        //КЛАСС Class, МЕТОД forName
        Class.forName("com.mysql.cj.jdbc.Driver");

        //пытаемся установить соединение с заданным url бд
        //ОБЪЕКТ Connection для работы с бд
        //КЛАСС DriverManager, МЕТОД getConnection
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
        System.out.println("Успешно законнектились к БД!");

        //ОБЪЕКТ statement для выполнения sql запросов
        //МЕТОД createStatement ОБЪЕКТА Statement создает этот объект для работы с бд
        Statement stmt1 = con1.createStatement();

        //задаем запрос СОЗДАНИЯ, как строку
        String query = "CREATE TABLE IF NOT EXISTS " + tablename + columns_type;

        //отправляем серверу бд sql-выражение
        //вызваем МЕТОД executeQuery ОБЪЕКТА Statement и в качестве аргумента передаем скрипт запроса
        stmt1.executeUpdate(query);

        //ОБЪЕКТ ResultSet - объект java, содержащий результаты выполнения sql запросов
        //МЕТОД executeQuery ОБЪЕКТА ResultSet
        ResultSet rs1 = stmt1.executeQuery("Show tables");
        System.out.println("Таблицы из текущей БД: ");

        //МЕТОД rs.next() ОБЪЕКТА ResultSet - построчный вывод названий таблиц в цикле
        while (rs1.next()) {
            System.out.print(rs1.getString(1));
            System.out.println();
        }
    }
}
