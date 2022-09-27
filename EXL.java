package com.test.idea;

//для работы с потоками (будем использовать в блоке с excel)
import java.io.FileOutputStream;
//в особенности потом понадобятся Connection, ResultSet и Statement
import java.sql.*;
//импортируем библиотеки для работы с excel
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;


//главный КЛАСС
public class EXL {

    //точка входа в программу
    //public - модификатор доступа, static - статичный неизменяемый метод, void ничего не возвращает, КЛАСС main()
    public static void ExcelConvector (String table_name) {

        //реализуем через try - catch, чтобы программы не руинилась в случае ошибки
        try {

            //создаем название excel файла с учетом введеного имени таблицы
            String filename = "c:/" + table_name + ".xls";

            //создаём ОБЪЕКТ HSSFWorkBook
            HSSFWorkbook hwb = new HSSFWorkbook();

            //создаём лист, используя объект, созданный в предыдущем шаге, ОБЪЕКТ createSheet()
            HSSFSheet sheet = hwb.createSheet("new sheet");

            //регистрируем драйвер для дальнейшей работы (управление jdbc)
            //КЛАСС DriverManager, МЕТОД registerDriver
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            //url
            String mysqlUrl1 = "jdbc:mysql://localhost/testik";
            Connection con = DriverManager.getConnection(mysqlUrl1, "root", "кщще");

            //ОБЪЕКТ statement для выполнения sql запросов
            //МЕТОД createStatement ОБЪЕКТА Statement создает этот объект для работы с бд
            Statement st = con.createStatement();

            //ОБЪЕКТ ResultSet - объект java, содержащий результаты выполнения sql запросов
            //МЕТОД executeQuery ОБЪЕКТА ResultSet
            ResultSet rs = st.executeQuery("Select * from " + table_name);

            //ОБЪЕКТ ResultSetMetaData содержит информацию о результирующей таблице
            //- количество колонок, тип значений колонок и т.д.
            //МЕТОД getMetaData ОБЪЕКТА ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            //создаём на листе строку, используя ОБЪЕКТ createRow()
            HSSFRow rowhead = sheet.createRow((short) 0);

            //создаём в строке ячейку — МЕТОД createCell()
            //задаём значение ячейки через МЕТОД setCellValue()
            //и всё это через цикл, чтобы заполнить все строчки
            int count_of_col = rsmd.getColumnCount();
            for (int i = 1; i <= count_of_col; i++) {
                rowhead.createCell((short) i - 1).setCellValue(rsmd.getColumnLabel(i));
            }

            //начальное значение i для while
            int i = 1;

            //создаём в строке ячейку — МЕТОД createCell()
            //задаём значение ячейки через МЕТОД setCellValue()
            //получение строки МЕТОД getString()
            //и всё это через цикл, чтобы заполнить все строчки
            while (rs.next()) {
                HSSFRow row = sheet.createRow((short) i);
                for (int j = 1; j <= count_of_col; j++) {
                    row.createCell((short) j-1).setCellValue(rs.getString(rsmd.getColumnLabel(j)));
                }
                i++;
            }

            //записываем workbook в file через ОБЪЕКТ FileOutputStream
            FileOutputStream fileOut = new FileOutputStream(filename);

            //записывает строки в файл
            //МЕТОД write ОБЪЕКТА HSSFWorkbook
            hwb.write(fileOut);

            //закрываем workbook, вызывая МЕТОД close()
            //МЕТОД close ОБЪЕКТА FileOutputStream
            fileOut.close();
            System.out.println("Данные успешно занесены в Exel");
        }

        //если что-то пойде не так, программа выведет тект ошибки, но не ошибку
        catch ( Exception ex ) {
            System.out.println(ex);
            System.out.println(ex.toString());
        }
    }
}
