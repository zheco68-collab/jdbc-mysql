import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class TrafficEmployeeManagement {
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: addEmployee(); break;
                case 2: deleteEmployee(); break;
                case 3: updateEmployee(); break;
                case 4: findEmployee(); break;
                case 5: showAllEmployees(); break;
                case 6: findByDept(); break;
                case 7: findByPosition(); break;
                case 0: System.out.println("退出系统"); return;
                default: System.out.println("无效选择");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n===== 智慧交通企业员工信息管理系统 =====");
        System.out.println("1. 添加员工");
        System.out.println("2. 删除员工");
        System.out.println("3. 修改员工信息");
        System.out.println("4. 查询员工");
        System.out.println("5. 显示所有员工");
        System.out.println("6. 按部门查询");
        System.out.println("7. 按职位查询");
        System.out.println("0. 退出系统");
        System.out.print("请选择: ");
    }

    private static void addEmployee() {
        System.out.print("员工编号: ");
        String empId = scanner.next();
        System.out.print("姓名: ");
        String empName = scanner.next();
        System.out.print("性别: ");
        String empGender = scanner.next();
        System.out.print("年龄: ");
        int empAge = scanner.nextInt();
        System.out.print("联系电话: ");
        String empPhone = scanner.next();
        System.out.print("职位: ");
        String empPosition = scanner.next();
        System.out.print("薪资: ");
        double empSalary = scanner.nextDouble();
        System.out.print("入职日期(YYYY-MM-DD): ");
        String hiredate = scanner.next();
        System.out.print("部门编号: ");
        String deptId = scanner.next();

        Employee emp = new Employee(empId, empName, empGender, empAge,
                empPhone, empPosition, empSalary, Date.valueOf(hiredate), deptId);
        int result = employeeDao.addEmployee(emp);
        System.out.println(result > 0 ? "添加成功" : "添加失败(编号已存在)");
    }

    private static void deleteEmployee() {
        System.out.print("请输入要删除的员工编号: ");
        String empId = scanner.next();
        int result = employeeDao.deleteEmployee(empId);
        System.out.println(result > 0 ? "删除成功" : "删除失败(员工不存在)");
    }

    private static void updateEmployee() {
        System.out.print("请输入要修改的员工编号: ");
        String empId = scanner.next();
        if (employeeDao.findById(empId) == null) {
            System.out.println("员工不存在");
            return;
        }
        System.out.println("要修改的字段: 1-姓名 2-性别 3-年龄 4-电话 5-职位 6-薪资 7-部门");
        String field = scanner.next();
        System.out.print("新值: ");
        String newValue = scanner.next();

        int result = employeeDao.updateEmployee(empId, field, newValue);
        System.out.println(result > 0 ? "修改成功" : "修改失败");
    }

    private static void findEmployee() {
        System.out.print("请输入要查询的员工编号: ");
        String empId = scanner.next();
        Employee emp = employeeDao.findById(empId);
        if (emp != null) {
            printHeader();
            System.out.println(emp);
        } else {
            System.out.println("员工不存在");
        }
    }

    private static void showAllEmployees() {
        List<Employee> list = employeeDao.findAll();
        printHeader();
        for (Employee e : list) {
            System.out.println(e);
        }
        System.out.println("共 " + list.size() + " 条记录");
    }

    private static void findByDept() {
        System.out.print("请输入部门编号: ");
        String deptId = scanner.next();
        List<Employee> list = employeeDao.findByDept(deptId);
        if (list.isEmpty()) {
            System.out.println("该部门没有员工");
        } else {
            printHeader();
            for (Employee e : list) {
                System.out.println(e);
            }
        }
    }

    private static void findByPosition() {
        System.out.print("请输入职位关键字: ");
        String position = scanner.next();
        List<Employee> list = employeeDao.findByPosition(position);
        if (list.isEmpty()) {
            System.out.println("没有找到该职位的员工");
        } else {
            printHeader();
            for (Employee e : list) {
                System.out.println(e);
            }
        }
    }

    private static void printHeader() {
        System.out.println("| 员工编号 | 姓名   | 性别 | 年龄 | 联系电话     | 职位         | 薪资        | 入职日期    | 部门编号 |");
        System.out.println("-------------------------------------------------------------------------------------------");
    }
}
