import java.sql.*;

/**
 Assignment 5: Connect to the university database and perform various queries.

 Programmer: Braxton Elrod

 Due: Oct 18th 10am
 */

public class JavaUniversity {

    public static void main(String[] args) {
        String url = "jdbc:mysql://10.0.6.1:3306/University_be987632?autoReconnect=true&useSSL=false&serverTimezone=UTC";
        String userName = "be987632";
        String password = "Ny9cvWYP"; // this is the password you use for mysql workbench
        try {
            // load the JDBC driver
            // this command will register the driver with the driver manager and make it available to the program
            Class.forName("com.mysql.cj.jdbc.Driver");
            // setup the connection to the db
            Connection con = DriverManager.getConnection(url, userName, password);
            Statement st = con.createStatement();  // Creates a Statement object for sending SQL statements to the database.
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //First query
            String sql1 = "select i.id, i.name, count(t.sec_id) from instructor as i, teaches as t where i.id = t.id group by i.id order by i.name;";
            ResultSet rs1 = st.executeQuery(sql1); // run the query from a Java program
            System.out.println("All instructors id, names, and amount of sections taught. Ordered by name");
            System.out.println("-----  --------------------  --------------");
            System.out.println("id     Name                  Total sections"); // This will display the table headings
            System.out.println("-----  --------------------  --------------");

            while (rs1.next()) {
                System.out.print(String.format("%-7s", rs1.getString(1) ));
                System.out.print(String.format("%-22s", rs1.getString(2)));
                System.out.println(String.format("%,.0f", rs1.getDouble(3)));
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Second query
            System.out.println("");
            System.out.println("The instructors that have not taught any section");
            String sql2 = "select distinct(i.name) from instructor as i, teaches as t where i.id not in(select id from teaches);";
            ResultSet rs2 = st.executeQuery(sql2);
            System.out.println("------------------");
            System.out.println("Name                "); // This will display the table headings
            System.out.println("------------------");
            while (rs2.next()) {
                System.out.println(String.format("%-20s", rs2.getString(1)));
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Third Query
            System.out.println("");
            System.out.println("Courses that were offered in the Spring of 2010 along with professor's name. Ordered by professor's name");
            String sql3 = "select t.course_id, t.sec_id, i.name from teaches as t, instructor as i where semester = 'Spring' and year = 2010 and t.id = i.id order by i.name;";
            ResultSet rs3 = st.executeQuery(sql3);
            System.out.println("---------  --------  ------------------");
            System.out.println("Course_id  Sec_id    Name                "); // This will display the table headings
            System.out.println("---------  --------  ------------------");
            while (rs3.next()) {
                System.out.print(String.format("%-11s", rs3.getString(1) ));//11 so it lines up with the header
                System.out.print(String.format("%-10s", rs3.getString(2)));
                System.out.println(String.format("%-20s", rs3.getString(3)));
            }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Fourth Query
            System.out.println("");
            System.out.println("List of all the departments and the amount of instructors in each department. Ordered by department");
            String sql4 = "select d.dept_name, count(i.name) from department as d, instructor as i where d.dept_name = i.dept_name group by d.dept_name;";
            ResultSet rs4 = st.executeQuery(sql4);
            System.out.println("--------------------  -------------");
            System.out.println("Dept_name             Teacher Count                "); // This will display the table headings
            System.out.println("--------------------  -------------");
            while (rs4.next()) {
                System.out.print(String.format("%-22s", rs4.getString(1) ));//11 so it lines up with the header
                System.out.println(String.format("%,.0f", rs4.getDouble(2)));
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Fifth Query
            System.out.println("");
            System.out.println("The name, department, and salary of the highest paid instructor");
            String sql5 = "select name, dept_name, salary from instructor where salary = (select max(salary) from instructor);";
            ResultSet rs5 = st.executeQuery(sql5);
            System.out.println("--------------------  --------------------  ---------");
            System.out.println("Name                  Dept_name             Salary   "); // This will display the table headings
            System.out.println("--------------------  --------------------  ---------");
            while (rs5.next()) {
                System.out.print(String.format("%-22s", rs5.getString(1) ));
                System.out.print(String.format("%-22s", rs5.getString(2)));
                System.out.println(String.format("%,.2f", rs5.getDouble(3)));
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            con.close(); // close connection
        }
        catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
            System.err.println(cnfe);
        }
        catch (SQLException e){
            e.printStackTrace();
            System.err.println(e);
        }

    }
}