import java.util.List;

public interface EmployeeDao {
    int addEmployee(Employee employee);
    int deleteEmployee(String empId);
    int updateEmployee(String empId, String field, String newValue);
    Employee findById(String empId);
    List<Employee> findAll();
    List<Employee> findByDept(String deptId);
    List<Employee> findByPosition(String position);
}
