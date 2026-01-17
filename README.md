# Stockbit API Automation Test Framework

API Automation Test Framework built with **Java + Cucumber + JUnit 5 + REST Assured** for testing [FakerAPI](https://fakerapi.it/).

---

## Table of Contents

- [Project Overview](#-project-overview)
- [Prerequisites](#-prerequisites)
- [Project Setup](#-project-setup)
- [Project Structure](#-project-structure)
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
cd stockbit-qa-api-halid
```

### 2. Install Dependencies

```bash
mvn clean install -DskipTests
```

This will download all required dependencies:
- Cucumber Java (7.15.0)
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

For **VS Code**:
1. Install "Extension Pack for Java"
2. Install "Cucumber (Gherkin) Full Support"

---

## 📁 Project Structure

```
stockbit-qa-api-halid/
├── pom.xml                                    # Maven configuration
├── readme.md                                  # Project documentation
├── src/
│   └── test/
│       ├── java/
│       │   └── com/stockbit/qa/
│       │       ├── client/
│       │       │   └── ApiClient.java         # REST API client
│       │       ├── config/
│       │       │   └── ApiConfig.java         # Configuration constants
│       │       ├── context/
│       │       │   └── TestContext.java       # Shared test state
│       │       ├── models/
│       │       │   ├── Person.java            # Person POJO
│       │       │   ├── Company.java           # Company POJO
│       │       │   ├── Book.java              # Book POJO
│       │       │   ├── Product.java           # Product POJO
│       │       │   └── User.java              # User POJO
│       │       ├── runner/
│       │       │   └── TestRunner.java        # Cucumber test runner
│       │       └── stepdefinitions/
│       │           ├── CommonSteps.java       # Common step definitions
│       │           ├── PersonsSteps.java      # Persons API steps
│       │           ├── CompaniesSteps.java    # Companies API steps
│       │           ├── BooksSteps.java        # Books API steps
│       │           ├── ProductsSteps.java     # Products API steps
│       │           └── UsersSteps.java        # Users API steps
│       └── resources/
│           ├── features/
│           │   ├── persons.feature            # Persons test scenarios
│           │   ├── companies.feature          # Companies test scenarios
│           │   ├── books.feature              # Books test scenarios
│           │   ├── products.feature           # Products test scenarios
│           │   └── users.feature              # Users test scenarios
│           ├── cucumber.properties            # Cucumber config
│           ├── junit-platform.properties      # JUnit config
│           └── simplelogger.properties        # Logging config
└── target/
    └── cucumber-reports/                      # Generated test reports
```

---

## ▶️ Running Tests

### Run All Tests

```bash
mvn clean test
```

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

### 1. **Page Object Model (adapted for API)**
- `ApiClient.java` - Encapsulates API interaction logic
- `ApiConfig.java` - Centralized configuration

### 2. **Builder Pattern**
- `ApiClient` uses fluent interface for building requests

### 3. **Singleton Pattern**
- `TestContext` manages shared state between steps

### 4. **Factory Pattern**
- Model classes (POJOs) for response deserialization

### 5. **Dependency Injection**
- Cucumber PicoContainer for step definition DI

---

## 🔗 API Reference

Base URL: `https://fakerapi.it/api/v1`

### Common Query Parameters
| Parameter | Description | Example |
|-----------|-------------|---------|
| `_quantity` | Number of items to generate | `?_quantity=10` |
| `_locale` | Language/locale for data | `?_locale=en_US` |
---

## 📝 Author

**Halid** - QA Engineer

---

## 📄 License

This project is created for Stockbit QA Assessment purposes.