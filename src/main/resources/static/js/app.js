const API_BASE_URL = "http://localhost:8080/api/employees";

document.addEventListener("DOMContentLoaded", () => {
    fetchEmployees();
});

function fetchEmployees() {
    fetch(API_BASE_URL)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector("#employeeTable tbody");
            tableBody.innerHTML = ""; // Clear table
            data.forEach(employee => {
                const row = `
                    <tr>
                        <td>${employee.id}</td>
                        <td>${employee.name}</td>
                        <td>${employee.department}</td>
                        <td>${employee.salary}</td>
                        <td>
                            <button onclick="editEmployee(${employee.id})">Edit</button>
                            <button onclick="deleteEmployee(${employee.id})">Delete</button>
                        </td>
                    </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching employees:", error));
}

function showAddEmployeeForm() {
    document.getElementById("employeeForm").style.display = "block";
}

function hideEmployeeForm() {
    document.getElementById("employeeForm").style.display = "none";
}

function saveEmployee() {
    const name = document.getElementById("employeeName").value;
    const department = document.getElementById("employeeDepartment").value;
    const salary = document.getElementById("employeeSalary").value;

    const employee = { name, department, salary };

    fetch(API_BASE_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(employee)
    })
        .then(() => {
            fetchEmployees();
            hideEmployeeForm();
        })
        .catch(error => console.error("Error saving employee:", error));
}

function deleteEmployee(id) {
    fetch(`${API_BASE_URL}/${id}`, { method: "DELETE" })
        .then(() => fetchEmployees())
        .catch(error => console.error("Error deleting employee:", error));
}
