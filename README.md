# OpenCart E-Commerce Automation Framework

## Overview
A hybrid automation framework for the OpenCart e-commerce application 
built from scratch using Selenium WebDriver, Java, TestNG and Maven. 
Covers end-to-end test scenarios across the full shopping journey 
including registration, login, product search, cart management and 
multiple checkout flows.

## Framework Architecture
- **Design Pattern:** Page Object Model (POM) with PageFactory
- **Test Framework:** TestNG
- **Build Tool:** Maven
- **Reporting:** Extent Reports with auto screenshot on failure
- **Logging:** Log4j2 rolling file appender
- **Grid:** Selenium Grid with Docker
- **CI/CD:** Jenkins

## Tech Stack
| Tool | Version |
|------|---------|
| Java | 21 |
| Selenium | 4.41.0 |
| TestNG | 7.12.0 |
| Maven Surefire | 3.5.5 |
| ExtentReports | 5.1.2 |
| Log4j2 | 2.23.1 |
| Apache POI | 5.5.1 |

## Project Structure
src/
├── test/
│   ├── java/
│   │   ├── pageObjects/     → Page classes (POM)
│   │   ├── testBase/        → BaseClass and BasePage
│   │   ├── testCases/       → Test classes
│   │   └── utilities/       → Reports, Excel, DataProviders
│   └── resources/
│       ├── config.properties
│       └── log4j2.xml
├── testData/
│   └── LoginData.xlsx
├── master.xml
├── grid-docker.xml
├── docker-compose.yml
├── run_local.bat
└── run_grid.bat

## Test Cases
| Test | Description | Groups |
|------|-------------|--------|
| TC001 | Account Registration | Smoke, Regression |
| TC002 | Login | Smoke, Sanity |
| TC003 | Login Data Driven | Datadriven |
| TC004 | Search Product | Sanity, Regression |
| TC005 | Product Display Page | Regression |
| TC006 | Cart Verification | Regression |
| TC007 | Guest Checkout | Regression |
| TC008 | Logged In User Checkout | Regression |
| TC009 | Logged In User New Address | Regression |
| TC010 | Register and Checkout | Regression |

## Prerequisites
- Java 21
- Maven
- Chrome/Firefox/Edge browser
- XAMPP (Apache + MySQL running)
- Docker Desktop (for Grid execution)

## Configuration
Update `src/test/resources/config.properties` before running:
```properties
execution_env=local   # change to remote for Docker Grid
appURL=http://localhost/opencart/
email=your@email.com
password=yourpassword
searchProdName=iPhone
```

## How to Run

### Run locally:
```bash
run_local.bat
```
or
```bash
mvn test
```

### Run on Docker Grid:
```bash
run_grid.bat
```
or manually:
```bash
docker-compose up -d
mvn test
docker-compose down
```

### Run specific groups:
```bash
mvn test -Dgroups=Smoke
mvn test -Dgroups=Sanity
mvn test -Dgroups=Regression
mvn test -Dgroups=Datadriven
```

### Run on Grid XML directly:
```bash
mvn test -Dsurefire.suiteXmlFiles=grid-docker.xml
```

## Reports
- Extent Reports auto-generated in `/reports` folder
- Report auto-opens in browser after execution
- Screenshots captured automatically on test failure
- Log4j2 logs saved in `/logs` folder

## CI/CD
- Jenkins pipeline configured for automated execution
- Scheduled builds via cron expression
- HTML report published as Jenkins artifact
- Supports both local and Docker Grid execution

## Data Driven Testing
Login data driven test reads from `testData/LoginData.xlsx`:

| email | password | res | scenario |
|-------|----------|-----|----------|
| valid@gmail.com | ValidPass | valid | Valid login |
| wrong@gmail.com | wrongpass | invalid | Wrong password |
| | ValidPass | invalid | Missing email |
| valid@gmail.com | | invalid | Missing password |