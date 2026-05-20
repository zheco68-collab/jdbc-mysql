public class Department {
    private String deptId;
    private String deptName;
    private String deptLocation;

    public Department() {}

    public Department(String deptId, String deptName, String deptLocation) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptLocation = deptLocation;
    }

    public String getDeptId() { return deptId; }
    public String getDeptName() { return deptName; }
    public String getDeptLocation() { return deptLocation; }

    public void setDeptId(String deptId) { this.deptId = deptId; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    public void setDeptLocation(String deptLocation) { this.deptLocation = deptLocation; }

    @Override
    public String toString() {
        return String.format("| %-10s | %-20s | %-15s |", deptId, deptName, deptLocation);
    }
}
