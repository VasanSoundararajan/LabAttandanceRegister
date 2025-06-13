# 🧪 Lab Attendance Management System (Java + MySQL)

This is a simple Lab Attendance Management System developed using **Java Swing** and **MySQL**. It consists of two main modules:

1. `LabRegister.java` – A registration form for students to record their lab session details.
2. `MasterFrame.java` – A display dashboard for faculty/admins to view all submitted entries.

---

## 🚀 Features

### ✅ LabRegister (Student Interface)
- GUI for entering:
  - Name
  - Register Number
  - System Number
  - Year, Section, Session
  - Auto-fills current date
- Validates inputs before submission
- Saves data to a MySQL database table named `register`
- Confirmation dialog before submission
- Reset option to clear fields

### 📊 MasterFrame (Admin Interface)
- Displays all saved records in a scrollable `JTable`
- Auto-fetches data from the `register` table
- Real-time view of:
  - Reg No, Name, Date, System No, and Session

---

## 🛠️ Technologies Used

- **Java Swing** for GUI
- **JDBC** for database connectivity
- **MySQL** as the backend database
- **GridLayout** and **JTable** for GUI structure

---

## 🗃️ Database Schema

Database: `lab_db`  
Table: `register`

| Column     | Type     |
|------------|----------|
| id         | INT      |
| name       | VARCHAR  |
| date       | DATE     |
| system_no  | INT      |
| session    | VARCHAR  |

> **Note:** You must create the `lab_db` database and `register` table before running the application.

---

## 🧑‍💻 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/lab-attendance-system.git
cd lab-attendance-system
````

### 2. Configure MySQL

Update DB credentials in both Java files:

```java
final String user = "root";
final String pass = "your_password";
final String url  = "jdbc:mysql://localhost:3306/lab_db";
```

### 3. Compile & Run

Compile both files using your IDE (e.g., IntelliJ, Eclipse) or terminal:

```bash
javac LabRegister.java
javac MasterFrame.java
java LabRegister
java MasterFrame
```

---

## 📸 Screenshots

<details>
<summary>Click to expand</summary>

![LabRegister GUI](screenshots/lab_register.png)
![MasterFrame Viewer](screenshots/master_frame.png)

</details>

---

## 📌 Notes

* Make sure MySQL is running on `localhost:3306`.
* If you face any connection issues, check the JDBC driver and your database privileges.
* Ensure you have the MySQL JDBC Connector in your classpath.

---

## 📄 License

This project is open-source and free to use for educational purposes.

---

## 👨‍💻 Author

**Vasan S**
📧 [vasansoundararajan.21@gmail.com](mailto:vasansoundararajan.21@gmail.com)
🌐 [vasanportfolio.com](https://vasansoundararajan.github.io/Portfolio/)
