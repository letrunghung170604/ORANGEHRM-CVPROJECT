**OrangeHRM Automation Testing🧪**

**📌Overview**

This project is an automated testing framework for the OrangeHRM web application, built with Selenium WebDriver, TestNG, and Java.  
It tests core functionalities of the Admin and PIM modules, including user management, job titles, job categories, employment status, locations, and employee management.

The framework supports:

- Parallel execution
- Logging
- Allure reporting
- Screen recording
- Screenshots for failed tests  
    Test data is managed via Excel

**Features 🛠️**

**Admin Module ⚙️**

- User Management: Add, edit, delete users.
- Job Titles: Manage job titles with descriptions and file uploads.
- Job Categories: Add, edit, delete categories.
- Employment Status: Manage employment statuses.
- Locations: Add, edit, delete locations with details (name, city, country).

**PIM Module 👥**

- Employee Management: Add employees with login credentials and verify details.
- Employee Login: Test valid/invalid login credentials.

**Framework Features 📊**

- Parallel execution with TestNG⚡
- Data-driven testing using Excel 📈
- Logging with Log4j 📜
- Allure reports for test results 📊
- Screen recording with Monte Screen Recorder 🎥
- Screenshots for failed tests 📸

**Test Execution Flow 🏃‍♂️**

**Setup 🛠️**

- Initialize WebDriver (Chrome/Firefox/Edge) via testng.xml.
- Load configurations from PropertiesFile.properties.
- Read test data from DataFile.xlsx 📈.

**Execution ⚡**

- PIM Module 👥:
  - Add Employee: Create employees with login credentials.
  - Login Employee just created: Check invalid username, invalid password, and login success.
  - Search employees and verify table data (e.g., employee ID).
- Login: Authenticate with admin credentials (Admin, admin123) 🔐.
- Admin Module ⚙️:
  - Manage User Management, Job Titles, Job Category, Employment Status, Locations (Add, Edit, Delete, etc.).
  - Verify table data after operations.

**Logging & Reporting 📊**

- Log steps using Log4j (LogPropertiesFile.log) 📜
- Capture screenshots for failed tests (ExportData/Images) 📸
- Record videos of test execution (ExportData/Videos) 🎥
- Generate Allure reports for detailed results 📊

**Teardown 🧹**

- Quit WebDriver and clean up resources.

**View Reports**

- Allure: allure serve target/allure-results 📊
- Screenshots: ExportData/Images 📸
- Videos: ExportData/Videos 🎥
- Logs: Logs/LogPropertiesFile.log 📜

**Dependencies 📦**

| **Dependency** | **Version** | **Purpose** |
| --- | --- | --- |
| TestNG | 7.11.0 | Test framework 🧪 |
| WebDriverManager | 6.1.0 | WebDriver binary management 🚀 |
| Apache POI | 5.4.1 | Excel file handling 📈 |
| Log4j | 2.25.1 | Logging 📜 |
| Monte Screen Recorder | 0.7.7.0 | Screen recording 🎥 |
| Allure TestNG | 2.29.1 | Test reporting 📊 |
| DataFaker | 2.4.2 | Test data generation 🎲 |

**Contributors 👤**

- Le Trung Hung
