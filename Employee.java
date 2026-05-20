import java.sql.Date;

public class Employee {
    private String empId;
    private String empName;
    private String empGender;
    private int empAge;
    private String empPhone;
    private String empPosition;
    private double empSalary;
    private Date empHiredate;
    private String deptId;

    public Employee() {}

    public Employee(String empId, String empName, String empGender, int empAge,
                    String empPhone, String empPosition, double empSalary,
                    Date empHiredate, String deptId) {
        this.empId = empId;
        this.empName = empName;
        this.empGender = empGender;
        this.empAge = empAge;
        this.empPhone = empPhone;
        this.empPosition = empPosition;
        this.empSalary = empSalary;
        this.empHiredate = empHiredate;
        this.deptId = deptId;
    }

    public String getEmpId() { return empId; }
    public String getEmpName() { return empName; }
    public String getEmpGender() { return empGender; }
    public int getEmpAge() { return empAge; }
    public String getEmpPhone() { return empPhone; }
    public String getEmpPosition() { return empPosition; }
    public double getEmpSalary() { return empSalary; }
    public Date getEmpHiredate() { return empHiredate; }
    public String getDeptId() { return deptId; }

    public void setEmpId(String empId) { this.empId = empId; }
    public void setEmpName(String empName) { this.empName = empName; }
    public void setEmpGender(String empGender) { this.empGender = empGender; }
    public void setEmpAge(int empAge) { this.empAge = empAge; }
    public void setEmpPhone(String empPhone) { this.empPhone = empPhone; }
    public void setEmpPosition(String empPosition) { this.empPosition = empPosition; }
    public void setEmpSalary(double empSalary) { this.empSalary = empSalary; }
    public void setEmpHiredate(Date empHiredate) { this.empHiredate = empHiredate; }
    public void setDeptId(String deptId) { this.deptId = deptId; }

    @Override
    public String toString() {
        return String.format("| %-8s | %-8s | %-4s | %-4d | %-15s | %-15s | %-12.2f | %-12s | %-8s |",
                empId, empName, empGender, empAge, empPhone, empPosition, empSalary, empHiredate, deptId);
    }
}
