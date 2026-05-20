# jdbc-mysql
jdbc连数据库练习代码-智慧交通企业员工信息管理系统

### 1.1 项目简介

智慧交通企业员工信息管理系统是一个基于 Java JDBC 技术实现的命令行应用程序，用于管理企业员工和部门信息。系统提供完整的员工信息增删改查功能，并支持多种查询方式。

### 1.2 功能特性

| 功能编号 | 功能名称       | 功能描述                                                     |
| :------- | :------------- | :----------------------------------------------------------- |
| 1        | 添加部门       | 新增部门信息（编号、名称、位置）                             |
| 2        | 添加员工       | 新增员工信息（编号、姓名、性别、年龄、电话、职位、薪资、入职日期、部门） |
| 3        | 查询所有部门   | 展示所有部门列表                                             |
| 4        | 查询所有员工   | 展示所有员工列表                                             |
| 5        | 按员工ID查询   | 根据员工编号查询单个员工                                     |
| 6        | 按部门查询员工 | 查询指定部门下的所有员工                                     |
| 7        | 按入职日期查询 | 查询指定日期入职的员工                                       |
| 8        | 修改员工信息   | 修改员工的基本信息（姓名、性别、年龄、电话、职位、部门）     |
| 9        | 更新员工薪资   | 修改员工薪资                                                 |
| 10       | 删除员工       | 根据员工编号删除员工                                         |

---

## 2. 技术架构

### 2.1 技术栈

| 分类     | 技术              | 版本        |
| :------- | :---------------- | :---------- |
| 语言     | Java              | 8+          |
| 数据库   | MySQL             | 5.7+ / 8.0+ |
| JDBC驱动 | MySQL Connector/J | 8.0+        |

### 2.2 架构设计

采用 **分层架构** 设计，职责清晰：

```
┌─────────────────────────────────────────────────────────┐
│                    表现层 (Presentation)                 │
│          TrafficEmployeeManagement (主入口/控制台交互)      │
└─────────────────────────┬───────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────┐
│                    数据访问层 (DAO)                      │
│     EmployeeDao / DepartmentDao (接口定义)                │
│     EmployeeDaoImpl / DepartmentDaoImpl (实现类)          │
└─────────────────────────┬───────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────┐
│                   数据访问工具 (JDBC)                    │
│                 JdbcUtil (数据库连接管理)                 │
└─────────────────────────┬───────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────┐
│                      数据库 (MySQL)                      │
│               traffic_person 数据库                      │
│         employee 表 / department 表                      │
└─────────────────────────────────────────────────────────┘
```

### 2.3 核心设计模式

- **DAO模式**：通过接口与实现分离，降低耦合度
- **单例思想**：JdbcUtil 工具类提供统一的连接管理
- **try-with-resources**：自动资源管理，防止连接泄漏

---

## 3. 数据库设计

### 3.1 数据库连接信息

- **数据库名称**：`traffic_person`
- **用户名**：`root`
- **密码**：`root`
- **端口**：`3306`
- **字符集**：`utf-8`
- **时区**：`UTC`

### 3.2 数据表结构

#### 3.2.1 department 表（部门表）

| 字段名        | 类型         | 约束        | 说明     |
| :------------ | :----------- | :---------- | :------- |
| dept_id       | VARCHAR(20)  | PRIMARY KEY | 部门编号 |
| dept_name     | VARCHAR(50)  | NOT NULL    | 部门名称 |
| dept_location | VARCHAR(100) |             | 部门位置 |

**建表语句**：

```sql
CREATE TABLE department (
    dept_id VARCHAR(20) PRIMARY KEY,
    dept_name VARCHAR(50) NOT NULL,
    dept_location VARCHAR(100)
);
```

#### 3.2.2 employee 表（员工表）

| 字段名       | 类型        | 约束        | 说明         |
| :----------- | :---------- | :---------- | :----------- |
| emp_id       | VARCHAR(20) | PRIMARY KEY | 员工编号     |
| emp_name     | VARCHAR(50) | NOT NULL    | 员工姓名     |
| emp_gender   | VARCHAR(10) |             | 性别         |
| emp_age      | INT         |             | 年龄         |
| emp_phone    | VARCHAR(20) |             | 联系电话     |
| emp_position | VARCHAR(50) |             | 职位         |
| emp_salary   | DOUBLE      |             | 薪资         |
| emp_hiredate | DATE        |             | 入职日期     |
| dept_id      | VARCHAR(20) | FOREIGN KEY | 所属部门编号 |

**建表语句**：

```sql
CREATE TABLE employee (
    emp_id VARCHAR(20) PRIMARY KEY,
    emp_name VARCHAR(50) NOT NULL,
    emp_gender VARCHAR(10),
    emp_age INT,
    emp_phone VARCHAR(20),
    emp_position VARCHAR(50),
    emp_salary DOUBLE,
    emp_hiredate DATE,
    dept_id VARCHAR(20),
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);
```

### 3.3 ER 关系图

```
department          employee
┌─────────┐        ┌─────────┐
│ dept_id │◄───────┤ dept_id │
│(PK)     │   1:N  │(FK)     │
├─────────┤        ├─────────┤
│dept_name│        │emp_id   │
│location │        │(PK)     │
└─────────┘        ├─────────┤
                   │emp_name │
                   │gender   │
                   │age      │
                   │phone    │
                   │position │
                   │salary   │
                   │hiredate │
                   └─────────┘
```

---

## 4. 代码结构

### 4.1 文件清单

| 文件名称                         | 类名                      | 职责描述                   |
| :------------------------------- | :------------------------ | :------------------------- |
| `JdbcUtil.java`                  | JdbcUtil                  | JDBC工具类，管理数据库连接 |
| `Employee.java`                  | Employee                  | 员工实体类，封装员工信息   |
| `Department.java`                | Department                | 部门实体类，封装部门信息   |
| `EmployeeDao.java`               | EmployeeDao               | 员工数据访问接口           |
| `EmployeeDaoImpl.java`           | EmployeeDaoImpl           | 员工数据访问接口实现       |
| `DepartmentDao.java`             | DepartmentDao             | 部门数据访问接口           |
| `DepartmentDaoImpl.java`         | DepartmentDaoImpl         | 部门数据访问接口实现       |
| `TrafficEmployeeManagement.java` | TrafficEmployeeManagement | 主程序入口，控制台交互     |

### 4.2 类关系图

```
                    ┌─────────────────────────┐
                    │TrafficEmployeeManagement│
                    └───────────┬─────────────┘
                                │
            ┌───────────────────┼───────────────────┐
            ▼                   ▼                   ▼
    ┌───────────────┐    ┌───────────────┐    ┌───────────────┐
    │  EmployeeDao  │    │DepartmentDao  │    │    JdbcUtil   │
    │   (interface) │    │   (interface) │    │  (util class) │
    └───────┬───────┘    └───────┬───────┘    └───────────────┘
            │                    │
            ▼                    ▼
    ┌───────────────┐    ┌───────────────┐
    │EmployeeDaoImpl│    │DepartmentDaoImpl│
    └───────┬───────┘    └───────┬───────┘
            │                    │
            └───────────┬────────┘
                        │
            ┌───────────┴───────────┐
            ▼                       ▼
    ┌───────────────┐    ┌───────────────┐
    │   Employee    │    │  Department   │
    │   (Entity)    │    │   (Entity)    │
    └───────────────┘    └───────────────┘
```

### 4.3 核心类设计

#### 4.3.1 JdbcUtil（JDBC工具类）

**功能**：统一管理数据库连接

**核心方法**：

| 方法名            | 返回值       | 功能描述       |
| :---------------- | :----------- | :------------- |
| `getConnection()` | `Connection` | 获取数据库连接 |

**配置参数**：

| 参数名   | 值                                           |
| :------- | :------------------------------------------- |
| DRIVER   | `com.mysql.cj.jdbc.Driver`                   |
| URL      | `jdbc:mysql://localhost:3306/traffic_person` |
| USERNAME | `root`                                       |
| PASSWORD | `root`                                       |

#### 4.3.2 EmployeeDao（员工数据访问接口）

**定义的数据访问方法**：

| 方法名           | 参数                                          | 返回值           | 功能描述         |
| :--------------- | :-------------------------------------------- | :--------------- | :--------------- |
| `addEmployee`    | `Employee employee`                           | `int`            | 添加员工记录     |
| `deleteEmployee` | `String empId`                                | `int`            | 删除员工记录     |
| `updateEmployee` | `String empId, String field, String newValue` | `int`            | 更新员工指定字段 |
| `updateSalary`   | `String empId, double newSalary`              | `int`            | 更新员工薪资     |
| `findById`       | `String empId`                                | `Employee`       | 根据ID查询员工   |
| `findAll`        | 无                                            | `List<Employee>` | 查询所有员工     |
| `findByDept`     | `String deptId`                               | `List<Employee>` | 按部门查询员工   |
| `findByHireDate` | `Date hireDate`                               | `List<Employee>` | 按入职日期查询   |

#### 4.3.3 DepartmentDao（部门数据访问接口）

**定义的数据访问方法**：

| 方法名               | 参数                    | 返回值             | 功能描述     |
| :------------------- | :---------------------- | :----------------- | :----------- |
| `addDepartment`      | `Department department` | `int`              | 添加部门记录 |
| `findAllDepartments` | 无                      | `List<Department>` | 查询所有部门 |

---

## 5. 功能模块详解

### 5.1 主菜单模块

**入口类**：`TrafficEmployeeManagement`

**菜单选项映射**：

| 菜单编号 | 调用方法                   | 功能           |
| :------- | :------------------------- | :------------- |
| 1        | `addDepartment()`          | 添加部门       |
| 2        | `addEmployee()`            | 添加员工       |
| 3        | `showAllDepartments()`     | 查询所有部门   |
| 4        | `showAllEmployees()`       | 查询所有员工   |
| 5        | `findEmployeeById()`       | 按ID查询员工   |
| 6        | `findEmployeeByDept()`     | 按部门查询员工 |
| 7        | `findEmployeeByHireDate()` | 按入职日期查询 |
| 8        | `updateEmployee()`         | 修改员工信息   |
| 9        | `updateEmployeeSalary()`   | 更新员工薪资   |
| 10       | `deleteEmployee()`         | 删除员工       |
| 0        | 退出系统                   |                |

### 5.2 员工管理模块

#### 5.2.1 添加员工流程

```
用户输入 → 构建Employee对象 → EmployeeDaoImpl.addEmployee() → 返回结果
         ↓
     员工编号、姓名、性别、年龄、电话、职位、薪资、入职日期、部门编号
```

#### 5.2.2 查询员工流程

```
用户输入条件 → 调用对应查询方法 → 返回Employee列表/单个对象 → 格式化输出
```

#### 5.2.3 更新员工信息字段映射

| 用户输入 | 数据库字段   | 说明     |
| :------- | :----------- | :------- |
| 1        | emp_name     | 员工姓名 |
| 2        | emp_gender   | 性别     |
| 3        | emp_age      | 年龄     |
| 4        | emp_phone    | 联系电话 |
| 5        | emp_position | 职位     |
| 6        | dept_id      | 部门编号 |

### 5.3 部门管理模块

#### 5.3.1 添加部门流程

```
用户输入 → 构建Department对象 → DepartmentDaoImpl.addDepartment() → 返回结果
         ↓
     部门编号、部门名称、部门位置
```

---

## 6. 运行与部署

### 6.1 环境要求

| 依赖           | 版本要求      |
| :------------- | :------------ |
| JDK            | 1.8 及以上    |
| MySQL          | 5.7 及以上    |
| MySQL JDBC驱动 | 8.0.28 及以上 |

### 6.2 部署步骤

**步骤1：创建数据库**

```sql
CREATE DATABASE traffic_person CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE traffic_person;
```

**步骤2：创建数据表**

执行 3.2 节中的建表语句。

**步骤3：配置JDBC驱动**

将 MySQL Connector/J 驱动添加到项目的类路径中。

**步骤4：编译运行**

```bash
# 编译所有Java文件
javac *.java

# 运行主程序
java TrafficEmployeeManagement
```

### 6.3 运行截图示例

```
===== 智慧交通企业员工信息管理系统 =====
1. 添加部门
2. 添加员工
3. 查询所有部门
4. 查询所有员工
5. 按员工 ID 查询员工
6. 按部门查询员工
7. 按入职日期查询员工
8. 修改员工信息
9. 更新员工薪资
10. 删除员工
0. 退出系统
请选择：
```

---

## 7. 代码优化建议

### 7.1 当前代码问题分析

| 问题类型    | 问题描述                              | 影响               |
| :---------- | :------------------------------------ | :----------------- |
| SQL注入风险 | `updateEmployee`方法使用字符串拼接SQL | 存在SQL注入风险    |
| 硬编码      | 数据库连接信息硬编码在JdbcUtil中      | 不利于维护和部署   |
| 资源泄漏    | 部分ResultSet未使用try-with-resources | 可能导致资源泄漏   |
| 异常处理    | 仅打印异常堆栈，未做业务处理          | 用户体验差         |
| 事务管理    | 无事务管理机制                        | 数据一致性无法保证 |
| 输入验证    | 缺少用户输入验证                      | 可能导致数据异常   |

### 7.2 优化方案

**问题1：SQL注入风险**

**优化前**（`EmployeeDaoImpl.java:53`）：

```java
String sql = "UPDATE employee SET " + column + " = ? WHERE emp_id = ?";
```

**优化后**：

```java
private static final Map<String, String> COLUMN_MAP = new HashMap<>();
static {
    COLUMN_MAP.put("1", "emp_name");
    COLUMN_MAP.put("2", "emp_gender");
    COLUMN_MAP.put("3", "emp_age");
    COLUMN_MAP.put("4", "emp_phone");
    COLUMN_MAP.put("5", "emp_position");
    COLUMN_MAP.put("6", "dept_id");
}

@Override
public int updateEmployee(String empId, String field, String newValue) {
    String column = COLUMN_MAP.get(field);
    if (column == null) {
        return 0;
    }
    String sql = "UPDATE employee SET " + column + " = ? WHERE emp_id = ?";
    // ... 后续代码
}
```

**问题2：硬编码配置**

建议将数据库配置抽取到配置文件（如 `jdbc.properties`）：

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/traffic_person?useSSL=false&serverTimezone=UTC&characterEncoding=utf-8
jdbc.username=root
jdbc.password=root
```

**问题3：资源泄漏**

**优化前**（`EmployeeDaoImpl.java:85`）：

```java
ResultSet rs = pstmt.executeQuery();
```

**优化后**：

```java
try (Connection conn = JdbcUtil.getConnection();
     PreparedStatement pstmt = conn.prepareStatement(sql);
     ResultSet rs = pstmt.executeQuery()) {
    // ...
}
```

---

## 附录：实体类字段说明

### Employee 实体

| 属性名      | 类型   | 含义         | 对应数据库字段 |
| :---------- | :----- | :----------- | :------------- |
| empId       | String | 员工编号     | emp_id         |
| empName     | String | 员工姓名     | emp_name       |
| empGender   | String | 性别         | emp_gender     |
| empAge      | int    | 年龄         | emp_age        |
| empPhone    | String | 联系电话     | emp_phone      |
| empPosition | String | 职位         | emp_position   |
| empSalary   | double | 薪资         | emp_salary     |
| empHiredate | Date   | 入职日期     | emp_hiredate   |
| deptId      | String | 所属部门编号 | dept_id        |

### Department 实体

| 属性名       | 类型   | 含义     | 对应数据库字段 |
| :----------- | :----- | :------- | :------------- |
| deptId       | String | 部门编号 | dept_id        |
| deptName     | String | 部门名称 | dept_name      |
| deptLocation | String | 部门位置 | dept_location  |

---

