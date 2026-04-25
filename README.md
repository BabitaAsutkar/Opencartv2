# Opencartv2 — Automated UI Test Suite

## Table of Contents
- Quick Start
- Prerequisites
- Configuration
- How to run
- Reporting
- CI/CD integration
- Troubleshooting
- Project structure

## Quick Start (one-liner)
From the project root (Windows CMD), run a quick smoke suite using Chrome (visible):

```bat
mvn clean test -Dsurefire.suiteXmlFiles=Master.xml -Dheadless=false -Denv=local
```

Or the default headless run:

```bat
mvn clean test
```

## 1) Project title and brief description

Opencartv2 Automated UI Test Framework

Brief summary
- A Java + TestNG + Selenium framework that automates functional UI tests for an OpenCart v2 application. The framework supports local and remote (Selenium Grid) execution, data-driven tests (Excel), page-object design, parallel cross-browser execution, logging, screenshots and HTML reporting.

## 2) Test stack (tools & versions)

This project uses the following tools and library versions (as configured in `pom.xml` and the codebase):

- Java: JDK 11+ recommended (set JAVA_HOME accordingly)
- Maven: 3.6+ (mvn on PATH)
- Selenium Java: 4.27.0
- TestNG: 7.11.0
- Apache POI: 5.5.1 (poi, poi-ooxml) — for Excel data-driven tests
- Log4j: 2.23.1 (log4j-api, log4j-core)
- Apache Commons Lang: 3.20.0
- Commons IO: 2.21.0
- ExtentReports: 5.1.2 (optional advanced HTML reporting)
- Maven Surefire Plugin: 3.5.5 (configured in pom)
- maven-compiler-plugin: 3.15.0

Browser & drivers
- Chrome, Microsoft Edge, Firefox (local execution)
- Ensure matching driver binaries (chromedriver, msedgedriver, geckodriver) are available and compatible with the installed browser versions.

Optional infrastructure
- Selenium Grid Hub (Docker or standalone) at `http://localhost:4444/wd/hub` when using remote execution.

## 3) Key features (what makes this framework smart)

- Page Object Model (POM): clean separation between test logic and UI locators/actions, which improves maintainability.
- Data-driven testing with Excel (Apache POI): `DataProviders` reads Excel sheets and feeds TestNG tests.
- Cross-browser & remote execution: supports local browser runs and remote execution on Selenium Grid via desired capabilities.
- Parallel execution support: TestNG suites allow parallel runs and cross-browser matrices (see `parallelCrossBrowser.xml`).
- Built-in logging & diagnostics: Log4j2 integration plus structured logs in `logs/automation.log`.
- Screenshots on demand: `captureScreen` helper in `BaseClass` stores screenshots to `reports/` for fast debugging.
- ExtentReports integration: optional enhanced HTML reporting for richer test result visualization.
- Reusable utilities: random test data helpers and common utilities to keep test cases focused on assertions.

## 4) Project structure (folder tree)

Top-level important files and folders (trimmed view):

- Opencartv2/
  - pom.xml
  - run.bat
  - Master.xml
  - parallelCrossBrowser.xml
  - grid_docker.xml
  - grouping.xml
  - README.md
  - logs/
    - automation.log
  - reports/
    - Test-Report-*.html
  - screenshots/
  - src/
    - main/
    - test/
      - java/
        - pageObjects/
        - testBase/
          - BaseClass.java
        - testCases/
          - Tc001_AccountRegistrationTest.java
          - Tc002_LoginTest.java
          - Tc003_loginDDT.java
          - Tc004_ProductSearch.java
          - Tc005_ProductSearchDDT.java
        - utilities/
          - DataProviders.java
          - ExcelUtility.java
      - resources/
        - config.properties
  - testData/
    - Opencart_LoginData.xlsx
    - Search_TestData.xlsx

(Use your IDE or `dir` to view the full tree for any missing files.)

## 5) Prerequisites and installation (step-by-step)

1. Install Java JDK 11+ and set `JAVA_HOME`.
   - Windows example (cmd):

```bat
setx JAVA_HOME "C:\Program Files\Java\jdk-11.0.18"
setx PATH "%PATH%;%JAVA_HOME%\\bin"
```

2. Install Apache Maven and add it to PATH (or use a packaged IDE that provides Maven).

3. Clone or copy the repository into your workspace.

4. Ensure browser(s) installed locally: Chrome, Edge or Firefox.

5. Download matching browser driver executables for your browsers (chromedriver, msedgedriver, geckodriver) and add them to PATH or set system properties during runtime.
   - Example: put executables in `C:\tools\drivers` and add to PATH.

6. Verify Maven dependencies: from repository root run:

```bat
mvn -v
mvn dependency:resolve
```

7. Verify test data and config files:
   - Open `src/test/resources/config.properties` and set `appURL`, `execution_env` (local/remote), and other properties.
   - Confirm Excel files exist in `testData/` and have expected sheet names/columns.

## 6) How to run the tests

Option A — run the bundled Windows script

Open cmd.exe at the project root and run:

```bat
run.bat
```

Option B — run via Maven (default test suite)

```bat
mvn clean test
```

Option C — run a specific TestNG suite file

```bat
mvn clean test -Dsurefire.suiteXmlFiles=Master.xml
```

Pass TestNG parameters via suite XML or system properties if your `BaseClass` expects them (for example `os` and `browser`). Example snippet for TestNG suite:

```xml
<suite name="Master">
  <parameter name="os" value="windows"/>
  <parameter name="browser" value="chrome"/>
  <test name="AllTests"> ... </test>
</suite>
```

Remote execution (Selenium Grid)
- Start the Grid and set `execution_env=remote` in `src/test/resources/config.properties`.
- Ensure nodes are registered and offer the desired browser capabilities.

## 7) Reporting (where results appear)

After test execution you can inspect:

- `reports/Test-Report-<timestamp>.html` — main human-readable test report (if the project generates it via the test run)
- `test-output/` — TestNG default HTML and XML artifacts
- `target/surefire-reports/` — surefire plugin results (depending on how tests were run)
- `logs/automation.log` — detailed runtime log
- `screenshots/` or `reports/` (screenshots) — captured screenshots for failed steps
- ExtentReports output (if configured) — check `reports/` for Extent report files

How to interpret items
- Open the HTML reports in a browser to see passed/failed tests and stack traces.
- Use screenshots to reproduce UI state when a failure happened.
- Check `automation.log` for step-by-step messages generated by Log4j2.

## 8) CI/CD integration

Suggested CI strategy (GitHub Actions / Jenkins / GitLab CI):

- Add a lightweight smoke test suite (small set of stable tests) to run on each PR to catch regressions early.
- Use a matrix job to validate multiple JDK / browser combinations if needed.
- Use Dockerized Selenium Grid on the CI runner or use cloud providers (BrowserStack / Sauce Labs) to avoid driver management.
- Persist artifacts from CI runs (HTML reports, screenshots, logs) as job artifacts for post-run analysis.

Example GitHub Actions (high-level guidance):
- Workflow triggers: on push and pull_request
- Steps:
  - Checkout repository
  - Set up JDK (actions/setup-java)
  - Install Maven
  - Start a dockerized Selenium Grid (or use a managed service)
  - Add browser drivers to PATH on the runner (or use Grid nodes)
  - Run tests with `mvn clean test -Dsurefire.suiteXmlFiles=Master.xml`
  - Upload `reports/`, `test-output/`, and `screenshots/` as workflow artifacts

Security & reliability recommendations for CI
- Do not store secrets in plaintext; use CI secret stores for service credentials.
- Pin Docker images and dependency versions to reduce flakiness.
- Run unstable or visually flaky tests nightly instead of on every PR.

