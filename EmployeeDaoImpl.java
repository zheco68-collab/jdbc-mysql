import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public int addEmployee(Employee employee) {
        String sql = "INSERT INTO employee (emp_id, emp_name, emp_gender, emp_age, emp_phone, emp_position, emp_salary, emp_hiredate, dept_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employee.getEmpId());
            pstmt.setString(2, employee.getEmpName());
            pstmt.setString(3, employee.getEmpGender());
            pstmt.setInt(4, employee.getEmpAge());
            pstmt.setString(5, employee.getEmpPhone());
            pstmt.setString(6, employee.getEmpPosition());
            pstmt.setDouble(7, employee.getEmpSalary());
            pstmt.setDate(8, employee.getEmpHiredate());
            pstmt.setString(9, employee.getDeptId());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteEmployee(String empId) {
        String sql = "DELETE FROM employee WHERE emp_id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateEmployee(String empId, String field, String newValue) {
        String column;
        switch (field) {
            case "1": column = "emp_name"; break;
            case "2": column = "emp_gender"; break;
            case "3": column = "emp_age"; break;
            case "4": column = "emp_phone"; break;
            case "5": column = "emp_position"; break;
            case "6": column = "emp_salary"; break;
            case "7": column = "dept_id"; break;
            default: return 0;
        }
        String sql = "UPDATE employee SET " + column + " = ? WHERE emp_id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newValue);
            pstmt.setString(2, empId);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Employee findById(String empId) {
        String sql = "SELECT * FROM employee WHERE emp_id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, empId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractEmployee(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(extractEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Employee> findByDept(String deptId) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee WHERE dept_id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, deptId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(extractEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Employee> findByPosition(String position) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee WHERE emp_position LIKE ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + position + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(extractEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Employee extractEmployee(ResultSet rs) throws SQLException {
        Employee emp = new Employee();
        emp.setEmpId(rs.getString("emp_id"));
        emp.setEmpName(rs.getString("emp_name"));
        emp.setEmpGender(rs.getString("emp_gender"));
        emp.setEmpAge(rs.getInt("emp_age"));
        emp.setEmpPhone(rs.getString("emp_phone"));
        emp.setEmpPosition(rs.getString("emp_position"));
        emp.setEmpSalary(rs.getDouble("emp_salary"));
        emp.setEmpHiredate(rs.getDate("emp_hiredate"));
        emp.setDeptId(rs.getString("dept_id"));
        return emp;
    }
}
