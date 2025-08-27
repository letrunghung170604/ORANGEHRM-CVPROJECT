OrangeHRM Automation Testing Framework 🧪
This project is an automated testing framework for the OrangeHRM web application, built with Selenium WebDriver, TestNG, and Java. It tests core functionalities of the Admin and PIM modules, including user management, job titles, job categories, employment status, locations, and employee management. The framework supports parallel execution, logging, Allure reporting, screen recording, and screenshots for failed tests, with test data managed via Excel and configurations in a properties file.
Features 🛠️

Admin Module ⚙️:

User Management: Add, edit, delete users.
Job Titles: Manage job titles with descriptions and file uploads.
Job Categories: Add, edit, delete categories.
Employment Status: Manage employment statuses.
Locations: Add, edit, delete locations with details (name, city, country).


PIM Module 👥:

Employee Management: Add employees with login credentials and verify details.
Employee Login: Test valid/invalid login credentials.


Framework Features 📊:

Parallel execution with TestNG (5 threads) ⚡.
Data-driven testing using Excel 📈.
Logging with Log4j 📜.
Allure reports for test results 📊.
Screen recording with Monte Screen Recorder 🎥.
Screenshots for failed tests 📸.



Test Execution Flow 🏃‍♂️

Setup 🛠️:

Initialize WebDriver (Chrome/Firefox/Edge) via testng.xml.
Load configurations from PropertiesFile.properties.
Read test data from DataFile.xlsx 📈.


Execution ⚡:

Login: Authenticate with admin credentials (Admin, admin123) 🔐.
Admin Module ⚙️:
Navigate to User Management, Job Titles, Job Categories, Employment Status, or Locations.
Perform add, edit, delete operations and verify table data.


PIM Module 👥:
Add employees with login credentials.
Test login with valid/invalid credentials (username/password).
Search employees and verify table data (e.g., employee ID).


Tests run in parallel with 5 threads for efficiency.


Logging & Reporting 📊:

Log steps using Log4j (LogPropertiesFile.log) 📜.
Capture screenshots for failed tests (ExportData/Images) 📸.
Record videos of test execution (ExportData/Videos) 🎥.
Generate Allure reports for detailed results.


Teardown 🧹:

Quit WebDriver and clean up resources.



Project Structure 📂
orangehrm/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── DoPrepare/
│   │   │   │   ├── Common/               # Core utilities
│   │   │   │   │   ├── Listener.java     # TestNG listener 🔔
│   │   │   │   │   ├── Log.java          # Log4j logging 📜
│   │   │   │   │   ├── SetUp.java        # WebDriver setup 🌐
│   │   │   │   ├── Help/                 # Helper utilities
│   │   │   │   │   ├── CaptureHelp.java  # Screenshots 📸
│   │   │   │   │   ├── ExcelHelp.java    # Excel handling 📈
│   │   │   │   │   ├── PropertiesHelp.java # Properties file 🔧
│   │   │   │   │   ├── RecordHelp.java   # Screen recording 🎥
│   │   │   ├── DoProject/
│   │   │   │   ├── CommonFunctions/      # Selenium utilities
│   │   │   │   │   ├── Functions.java    # Common actions 🛠️
│   │   │   │   ├── Pages/                # Page objects
│   │   │   │   │   ├── AdminEmploymentStatus.java # Employment status 💼
│   │   │   │   │   ├── AdminJobCategories.java    # Job categories 🗂️
│   │   │   │   │   ├── AdminJobTitle.java         # Job titles 📋
│   │   │   │   │   ├── AdminLocation.java         # Locations 🌍
│   │   │   │   │   ├── AdminUserManagement.java   # User management 👤
│   │   │   │   │   ├── Login.java                 # Login page 🔐
│   │   │   │   │   ├── PIM.java                   # PIM module 👥
│   │   │   │   ├── TestCases/                    # Test cases
│   │   │   │   │   ├── AdminEmploymentStatusTest.java # Employment status tests 💼
│   │   │   │   │   ├── AdminJobCategoriesTest.java    # Job categories tests 🗂️
│   │   │   │   │   ├── AdminJobTitleTest.java         # Job titles tests 📋
│   │   │   │   │   ├── AdminLocationTest.java         # Locations tests 🌍
│   │   │   │   │   ├── AdminUserManagementTest.java   # User management tests 👤
│   │   │   │   │   ├── LoginTest.java                 # Login tests 🔐
│   │   │   │   │   ├── PIMTest.java                   # PIM module tests 👥
│   │   ├── resources/
│   │   │   ├── DataFile.xlsx             # Test data 📈
│   │   │   ├── PropertiesFile.properties # Configurations 🔧
│   │   │   ├── BuiMinhQuan.pdf           # Sample upload file 📄
├── Logs/                                     # Log output
│   ├── LogPropertiesFile.log             # Log file 📜
├── ExportData/                               # Test artifacts
│   ├── Images/                               # Screenshots 📸
│   ├── Videos/                               # Screen recordings 🎥
├── pom.xml                                   # Maven dependencies 🛠️
├── testng.xml                                # TestNG suite 🧪

Setup Instructions 🛠️

Prerequisites:

Java 24 ☕
Maven 🛠️
Chrome/Firefox/Edge browser 🌐


Clone Repository:
git clone <repository-url>
cd orangehrm


Install Dependencies:
mvn clean install


Run Tests:
mvn test


View Reports:

Allure: allure serve target/allure-results 📊.
Screenshots: ExportData/Images 📸.
Videos: ExportData/Videos 🎥.
Logs: Logs/LogPropertiesFile.log 📜.



Dependencies 📦



Dependency
Version
Purpose



Selenium WebDriver
4.34.0
Browser automation 🌐


TestNG
7.11.0
Test framework 🧪


WebDriverManager
6.1.0
WebDriver binary management 🚀


Apache POI
5.4.1
Excel file handling 📈


Log4j
2.25.1
Logging 📜


Monte Screen Recorder
0.7.7.0
Screen recording 🎥


Allure TestNG
2.29.1
Test reporting 📊


DataFaker
2.4.2
Test data generation 🎲


Contributors 👤

[Your Name] - Project Developer
