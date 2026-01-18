# Stockbit API Automation Test Framework

API Automation Test Framework built with **Java + Spring Boot + Cucumber + JUnit 5 + REST Assured** for testing [FakerAPI](https://fakerapi.it/).

---

## Table of Contents

- [Project Overview](#-project-overview)
- [Architecture](#-architecture)
- [Prerequisites](#-prerequisites)
- [Project Setup](#-project-setup)
- [Project Structure](#-project-structure)
- [Configuration](#-configuration)
- [Running Tests](#-running-tests)
- [Test Reports](#-test-reports)
- [Test Scenarios](#-test-scenarios)
- [Design Patterns Used](#-design-patterns-used)

---

## 🎯 Project Overview

This project automates API testing for [FakerAPI](https://fakerapi.it/), a free data mocking generator API.

### Tested Endpoints
- `/api/v1/persons` - Generate fake person data
- `/api/v1/companies` - Generate fake company data
- `/api/v1/books` - Generate fake book data
- `/api/v1/products` - Generate fake product data
- `/api/v1/users` - Generate fake user data

---

## 🏛️ Architecture
```
┌─────────────────────────────────────────────────────────────┐
│                     Step Definitions                         │
│  (Set request params → Call controllers → Assert responses) │
└─────────────────────────┬───────────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────────┐
│                     Controllers                              │
│         (API service layer - makes REST calls)               │
└─────────────────────────┬───────────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────────┐
│                     ServiceApi (Base)                        │
│              (Common REST operations + Interceptors)         │
└─────────────────────────┬───────────────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────────────┐
│                     Interceptors                             │
│        (Add headers, params, logging to requests)            │
└─────────────────────────────────────────────────────────────┘
```

### Key Components

| Component            | Description                                                                              |
|----------------------|------------------------------------------------------------------------------------------|
| **Interceptors**     | Add common headers, query parameters, and logging to all API requests                    |
| **ServiceApi**       | Base class for controllers that provides the `service()` method with interceptor support |
| **Controllers**      | Service layer classes that handle specific API endpoints                                 |
| **Data Classes**     | Store request parameters and response data for each API                                  |
| **Properties**       | Spring configuration classes mapped to `application.properties`                          |
| **Step Definitions** | Cucumber steps that orchestrate data setup, API calls, and assertions                    |

---

## 🔧 Prerequisites

Ensure you have the following installed on your system:

| Tool             | Version       | Download Link                                     |
|------------------|---------------|---------------------------------------------------|
| **Java JDK**     | 21 or higher  | [Download](https://adoptium.net/)                 |
| **Apache Maven** | 3.6 or higher | [Download](https://maven.apache.org/download.cgi) |
| **Git**          | Latest        | [Download](https://git-scm.com/downloads)         |

### Verify Installation

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check Git version
git --version
```

---

## 🚀 Project Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd stockbit-qa-api-automation
```

### 2. Install Dependencies

```bash
mvn clean install -DskipTests
```

This will download all required dependencies:
- Spring Boot (3.2.1)
- Cucumber Java (7.15.0)
- Cucumber Spring (7.15.0)
- JUnit 5 Platform (1.10.1)
- REST Assured (5.4.0)
- Jackson Databind (2.16.1)
- AssertJ (3.25.1)
- Lombok (1.18.30)

### 3. IDE Setup (Optional)

For **IntelliJ IDEA**:
1. Open the project folder
2. Import as Maven project
3. Enable annotation processing for Lombok: `Settings > Build > Compiler > Annotation Processors > Enable annotation processing`
4. Install Cucumber plugin: `Settings > Plugins > Search "Cucumber for Java"`
5. Install Spring Boot plugin if not already installed

For **VS Code**:
1. Install "Extension Pack for Java"
2. Install "Cucumber (Gherkin) Full Support"
3. Install "Spring Boot Extension Pack"

---

## 📁 Project Structure

```
stockbit-qa-api-automation/
├── pom.xml                                          # Maven configuration
├── README.md                                        # Project documentation
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/stockbit/qa/
│   │           ├── Application.java                 # Spring Boot application
│   │           ├── api/                             # API-specific packages
│   │           │   ├── persons/
│   │           │   │   ├── data/
│   │           │   │   │   └── PersonsData.java     # Persons request/response data
│   │           │   │   └── services/
│   │           │   │       └── PersonsController.java # Persons API controller
│   │           │   ├── companies/
│   │           │   │   ├── data/
│   │           │   │   │   └── CompaniesData.java
│   │           │   │   └── services/
│   │           │   │       └── CompaniesController.java
│   │           │   ├── books/
│   │           │   │   ├── data/
│   │           │   │   │   └── BooksData.java
│   │           │   │   └── services/
│   │           │   │       └── BooksController.java
│   │           │   ├── products/
│   │           │   │   ├── data/
│   │           │   │   │   └── ProductsData.java
│   │           │   │   └── services/
│   │           │   │       └── ProductsController.java
│   │           │   └── users/
│   │           │       ├── data/
│   │           │       │   └── UsersData.java
│   │           │       └── services/
│   │           │           └── UsersController.java
│   │           ├── core/                            # Core framework components
│   │           │   ├── data/
│   │           │   │   └── InterceptorData.java     # Shared interceptor data
│   │           │   ├── interceptors/
│   │           │   │   ├── ServiceInterceptor.java  # Interceptor interface
│   │           │   │   └── FakerApiInterceptor.java # FakerAPI interceptor impl
│   │           │   ├── json/
│   │           │   │   └── JsonApi.java             # JSON utilities
│   │           │   ├── models/
│   │           │   │   └── ApiResponse.java         # Generic API response
│   │           │   ├── properties/
│   │           │   │   ├── ApiProperties.java       # API configuration
│   │           │   │   ├── EndpointProperties.java  # Endpoints configuration
│   │           │   │   └── RequestProperties.java   # Request configuration
│   │           │   └── restassured/
│   │           │       ├── ResponseApi.java         # Response wrapper
│   │           │       └── ServiceApi.java          # Base service class
│   │           └── models/                          # Domain models
│   │               ├── Person.java
│   │               ├── Company.java
│   │               ├── Book.java
│   │               ├── Product.java
│   │               └── User.java
│   └── test/
│       ├── java/
│       │   └── com/stockbit/qa/
│       │       ├── config/
│       │       │   └── CucumberSpringConfiguration.java # Cucumber-Spring config
│       │       ├── hooks/
│       │       │   └── CucumberHooks.java           # Test lifecycle hooks
│       │       ├── runner/
│       │       │   ├── TestRunner.java              # JUnit 5 Platform Suite runner
│       │       │   └── CucumberRunner.java          # Alternative Cucumber runner
│       │       └── stepdefinitions/
│       │           ├── CommonSteps.java             # Common step definitions
│       │           ├── PersonsSteps.java            # Persons API steps
│       │           ├── CompaniesSteps.java          # Companies API steps
│       │           ├── BooksSteps.java              # Books API steps
│       │           ├── ProductsSteps.java           # Products API steps
│       │           └── UsersSteps.java              # Users API steps
│       └── resources/
│           ├── application.properties               # Spring configuration
│           ├── features/
│           │   ├── persons.feature
│           │   ├── companies.feature
│           │   ├── books.feature
│           │   ├── products.feature
│           │   └── users.feature
│           ├── cucumber.properties
│           ├── junit-platform.properties
│           └── simplelogger.properties
└── target/
    └── cucumber-reports/                            # Generated test reports
```

---

## ⚙️ Configuration

### Application Properties

All configuration is centralized in `src/test/resources/application.properties`:

```properties
# API Configuration
api.fakerapi.base-url=https://fakerapi.it/api/v1
api.fakerapi.default-locale=en_US
api.fakerapi.default-quantity=5

# Endpoints
api.endpoints.persons=/persons
api.endpoints.companies=/companies
api.endpoints.books=/books
api.endpoints.products=/products
api.endpoints.users=/users

# Request Configuration
api.request.connection-timeout=10000
api.request.read-timeout=10000

# Interceptor Configuration
api.interceptor.log-requests=true
api.interceptor.log-responses=true
```

### Environment-Specific Configuration

Create additional property files for different environments:

- `application-staging.properties` - Staging environment
- `application-production.properties` - Production environment

Activate a profile using:
```bash
mvn test -Dspring.profiles.active=staging
```

---

## ▶️ Running Tests

### Available Test Runners

This project provides two test runners:

| Runner           | Description                                    | Usage                            |
|------------------|------------------------------------------------|----------------------------------|
| `TestRunner`     | JUnit 5 Platform Suite runner (default)        | `mvn test -Dtest=TestRunner`     |
| `CucumberRunner` | Alternative Cucumber runner with rerun support | `mvn test -Dtest=CucumberRunner` |

### Run All Tests

```bash
# Using default configuration (runs both runners)
mvn clean test

# Using specific runner
mvn test -Dtest=TestRunner
mvn test -Dtest=CucumberRunner
```

### Run from IDE

You can run tests directly from your IDE:
1. Navigate to `src/test/java/com/stockbit/qa/runner/`
2. Right-click on `TestRunner.java` or `CucumberRunner.java`
3. Edit this tag @ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@yourtaghere")
4. Select "Run" or "Debug"

### Run Tests by Tag

```bash
# Run only smoke tests
mvn test -Dcucumber.filter.tags="@smoke"

# Run specific feature (e.g., persons only)
mvn test -Dcucumber.filter.tags="@persons"

# Run multiple tags (AND condition)
mvn test -Dcucumber.filter.tags="@smoke and @positive"

# Run multiple tags (OR condition)
mvn test -Dcucumber.filter.tags="@persons or @companies"

# Run with CucumberRunner and specific tag
mvn test -Dtest=CucumberRunner -Dcucumber.filter.tags="@smoke"
```

### Run with Specific Profile

```bash
# Run with staging configuration
mvn test -Dspring.profiles.active=staging
```

### Rerun Failed Tests

The `CucumberRunner` generates a `rerun.txt` file for failed scenarios:

```bash
# After running tests, failed scenarios are saved to:
# target/cucumber-reports/rerun.txt

# You can use this file to rerun only failed tests
```

---

## 📊 Test Reports

After test execution, reports are generated in:

### HTML Report
```
target/cucumber-reports/cucumber.html
```
Open this file in a browser to view the visual test report.

### JSON Report
```
target/cucumber-reports/cucumber.json
```
This can be used for CI/CD integration or third-party reporting tools.

---

## 🧪 Test Scenarios

### Persons API (`@persons`)
| Scenario                                   | Description                                | Tags                    |
|--------------------------------------------|--------------------------------------------|-------------------------|
| Retrieve persons with default settings     | Verify basic GET request returns 5 persons | `@smoke @positive`      |
| Retrieve persons with different quantities | Parameterized test for various quantities  | `@positive`             |
| Validate person data structure             | Check required fields exist                | `@positive @validation` |
| Verify email format                        | Validate email regex pattern               | `@positive @email`      |
| Verify gender values                       | Check gender is male/female                | `@positive @gender`     |
| Request with zero quantity                 | Boundary test                              | `@negative @boundary`   |

### Companies API (`@companies`)
| Scenario                                     | Description              | Tags                    |
|----------------------------------------------|--------------------------|-------------------------|
| Retrieve companies with default settings     | Verify basic GET request | `@smoke @positive`      |
| Retrieve companies with different quantities | Parameterized test       | `@positive`             |
| Validate company data structure              | Check required fields    | `@positive @validation` |
| Verify contact information                   | Check contact exists     | `@positive @contact`    |

### Books API (`@books`)
| Scenario                                 | Description              | Tags                    |
|------------------------------------------|--------------------------|-------------------------|
| Retrieve books with default settings     | Verify basic GET request | `@smoke @positive`      |
| Retrieve books with different quantities | Parameterized test       | `@positive`             |
| Validate book data structure             | Check required fields    | `@positive @validation` |
| Verify ISBN format                       | Validate ISBN exists     | `@positive @isbn`       |

### Products API (`@products`)
| Scenario                                    | Description                 | Tags                    |
|---------------------------------------------|-----------------------------|-------------------------|
| Retrieve products with default settings     | Verify basic GET request    | `@smoke @positive`      |
| Retrieve products with different quantities | Parameterized test          | `@positive`             |
| Validate product data structure             | Check required fields       | `@positive @validation` |
| Verify positive prices                      | Check price > 0             | `@positive @price`      |
| Verify price consistency with taxes         | Validate price >= net_price | `@positive @taxes`      |

### Users API (`@users`)
| Scenario                                 | Description              | Tags                    |
|------------------------------------------|--------------------------|-------------------------|
| Retrieve users with default settings     | Verify basic GET request | `@smoke @positive`      |
| Retrieve users with different quantities | Parameterized test       | `@positive`             |
| Validate user data structure             | Check required fields    | `@positive @validation` |
| Verify UUID format                       | Validate UUID regex      | `@positive @uuid`       |
| Verify non-empty passwords               | Check password exists    | `@positive @password`   |

---

## 🏗️ Design Patterns Used

### 1. **Service Layer Pattern**
- `Controllers` - Encapsulate API interaction logic for each endpoint
- `ServiceApi` - Base class providing common REST operations

### 2. **Interceptor Pattern**
- `ServiceInterceptor` - Interface for request interceptors
- `FakerApiInterceptor` - Adds common headers and parameters to FakerAPI requests

### 3. **Data Transfer Object (DTO) Pattern**
- `Data` classes - Store request parameters and response data
- `Model` classes - POJO representations of API responses

### 4. **Dependency Injection**
- Spring's `@Autowired` for injecting components
- Cucumber-Spring integration for step definition DI

### 5. **Configuration Properties Pattern**
- `@ConfigurationProperties` for type-safe configuration
- Externalized configuration in `application.properties`

### 6. **Builder Pattern**
- REST Assured's fluent interface for building requests
- `ResponseApi` wrapper for typed responses

---

## 🔄 Interceptor Flow

The interceptor pattern allows adding common functionality to all API calls:

```
Step Definition
    │
    ▼
Controller.method()
    │
    ▼
ServiceApi.service("fakerapi")
    │
    ▼
FakerApiInterceptor.isSupport("fakerapi") → true
    │
    ▼
FakerApiInterceptor.prepare(requestSpec)
    ├── Add Content-Type header
    ├── Add Accept header
    ├── Add _quantity parameter
    ├── Add _locale parameter
    └── Enable request logging
    │
    ▼
Execute REST Request
    │
    ▼
Return Response
```

---

## 🔗 API Reference

Base URL: `https://fakerapi.it/api/v1`

### Common Query Parameters
| Parameter   | Description                 | Example          |
|-------------|-----------------------------|------------------|
| `_quantity` | Number of items to generate | `?_quantity=10`  |
| `_locale`   | Language/locale for data    | `?_locale=en_US` |

---

## 📝 Author

**Halid** - QA Engineer

---

## 📄 License

This project is created for Stockbit QA Assessment purposes.