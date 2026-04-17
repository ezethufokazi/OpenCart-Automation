# OpenCart Automation Framework

## Overview
Automated testing framework for OpenCart e-commerce application
built with Selenium WebDriver, Java, TestNG and Maven.

## Framework Architecture
- **Design Pattern:** Page Object Model with PageFactory
- **Test Framework:** TestNG
- **Build Tool:** Maven
- **Reporting:** Extent Reports
- **Logging:** Log4j2
- **Grid:** Selenium Grid with Docker

## Tech Stack
| Tool | Version |
|------|---------|
| Java | 21 |
| Selenium | 4.18.1 |
| TestNG | 7.8.0 |
| Maven | 3.x |
| ExtentReports | 5.1.1 |
| Log4j2 | 2.23.1 |

## Project Structure
src/test/java/
├── pageObjects/     → Page classes (POM)
├── testBase/        → BaseClass and BasePage
├── testCases/       → Test classes
└── utilities/       → Reports, Excel reader, DataProviders

## Test Cases
| Test | Description | Group |
|------|-------------|-------|
| TC001 | Account Registration | Smoke, Regression |
| TC002 | Login | Smoke, Sanity |
| TC003 | Login Data Driven | Datadriven |
| TC004 | Product Display Page | Regression |
| TC005 | Search | Regression | Sanity, Regression
| TC006 | Cart Verification | Regression |
| TC007 | Guest Checkout | Regression |
| TC008 | Register and Checkout | Regression |
| TC009 | Logged In User Existing Address | Regression |
| TC010 | Logged In User New Address | Regression |


## Prerequisites
- Java 21
- Maven
- Chrome/Firefox/Edge browser
- XAMPP (Apache + MySQL)
- Docker Desktop (for Grid execution)

## How to Run

### Run locally:
```bash
double click run_local.bat
```
or
```bash
mvn test
```

### Run on Docker Grid:
```bash
double click run_grid.bat
```
or
```bash
docker-compose up -d
mvn test
docker-compose down
```

### Run specific groups:
```bash
mvn test -Dgroups=Smoke
mvn test -Dgroups=Smoke
mvn test -Dgroups=Regression
mvn test -Dgroups=Datadriven
```

## Configuration
Update `src/test/resources/config.properties`:
```properties
execution_env=local   # change to remote for Grid
browser=chrome
os=windows
appURL=http://localhost/opencart/
```

## Reports
- Extent Reports generated in `/reports` folder
- Auto opens in browser after test execution
- Screenshots automatically captured on failure

## CI/CD
- Jenkins pipeline configured for automated execution
- Docker Compose for Selenium Grid setup
- Supports parallel test execution