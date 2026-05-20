CREATE DATABASE IF NOT EXISTS traffic_employee_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE traffic_employee_db;

CREATE TABLE IF NOT EXISTS department (
    dept_id VARCHAR(20) PRIMARY KEY COMMENT '部门编号',
    dept_name VARCHAR(50) NOT NULL COMMENT '部门名称',
    dept_location VARCHAR(100) COMMENT '部门位置'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

CREATE TABLE IF NOT EXISTS employee (
    emp_id VARCHAR(20) PRIMARY KEY COMMENT '员工编号',
    emp_name VARCHAR(50) NOT NULL COMMENT '员工姓名',
    emp_gender VARCHAR(10) COMMENT '性别',
    emp_age INT COMMENT '年龄',
    emp_phone VARCHAR(20) COMMENT '联系电话',
    emp_position VARCHAR(50) COMMENT '职位',
    emp_salary DECIMAL(10,2) COMMENT '薪资',
    emp_hiredate DATE COMMENT '入职日期',
    dept_id VARCHAR(20) COMMENT '所属部门',
    FOREIGN KEY (dept_id) REFERENCES department(dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';

INSERT INTO department (dept_id, dept_name, dept_location) VALUES
('D001', '智慧交通研发部', 'A栋3楼'),
('D002', '交通数据分析部', 'A栋4楼'),
('D003', '系统运维部', 'B栋2楼'),
('D004', '项目管理部', 'B栋3楼');

INSERT INTO employee (emp_id, emp_name, emp_gender, emp_age, emp_phone, emp_position, emp_salary, emp_hiredate, dept_id) VALUES
('E001', '张伟', '男', 32, '13800138001', '高级工程师', 15000.00, '2018-05-10', 'D001'),
('E002', '李娜', '女', 28, '13800138002', '数据分析师', 12000.00, '2019-08-15', 'D002'),
('E003', '王强', '男', 35, '13800138003', '运维工程师', 13000.00, '2017-03-20', 'D003'),
('E004', '刘芳', '女', 30, '13800138004', '项目经理', 16000.00, '2018-11-05', 'D004'),
('E005', '陈明', '男', 27, '13800138005', '研发工程师', 11000.00, '2020-06-01', 'D001');
