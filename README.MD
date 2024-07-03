## 🤖 API Testing and Performance Project

This project showcases a comprehensive automation framework for API testing, focusing on REST APIs with additional support for SOAP and performance testing using Gatling.

### Key Features:
* **Robust API Testing**: Utilizes frameworks like _RestAssured_ for REST APIs and _SOAP UI_ for SOAP APIs.
* **Performance Testing**: Includes performance testing scenarios using _Gatling_ framework.
* **CI/CD Integration**: Designed for seamless integration into CI/CD pipelines for automated testing workflows.
* **Detailed Reporting**: Generates detailed test reports for API responses and performance metrics.

____
### Setup Checklist
To ensure smooth execution of this project, please complete the following steps:

#### 1. Install Essentials

* **Java Development Kit (JDK)**: Version 8 or higher.
* **Apache Maven**: Maven is used for building and running the project.

#### 2. Optional (But Recommended)

* **Jenkins**: Jenkins is recommended for CI/CD integration.
* **IDE**: Choose your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).

#### 3. Project Setup Notes
* **Dependencies**: Dependencies are managed in the project's pom.xml file for Maven. Ensure all necessary dependencies (RestAssured, SOAP UI, Gatling) are included.
____
### Execution
1. **Launch your IDE**: Open your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
2. **Import Project**: Import the project as a Maven project. Select the project's root directory.
3**Run Tests**:
   * **API Tests**:
      * **IDE**: Run tests directly from your IDE by right-clicking on the test class or method and selecting "Run" or choose NG Suite to run all tests.
      * **Command Line**: Navigate to the project's root directory and execute `mvn test` to run all API tests.
      * **Performance Tests (Gatling)**:Run tests from within the terminal in IDE with `cmd.exe /c mvnw.cmd gatling:test` command on Windows.
____
### Jenkins Integration
#### Pre-requisites
* **Jenkins**: Ensure Jenkins is installed and running.
* **Plugins**: Install necessary plugins (e.g., Git plugin, Maven plugin).
* **Tools Configuration**: Configure JDK and Maven within Jenkins.

#### Create a New Freestyle Job:
1. **New Job**: Navigate to Jenkins dashboard and click "New Item".
2. **Name and Type**: Provide a name for your job and select "Freestyle" as the job type.
3. **Freestyle Configuration**:
   * **Repository URL**: Set to your Git repository URL.
   * **Branches to build**: */master
   * **Build Steps**: Invoke top-level Maven targets
     ```
     mvn clean test  # Command for API tests
     gatling:test  # Command for Gatling performance tests
     ```

4. **Node Selection**: Ensure the Jenkins job runs on a Windows node.

#### Run Your Freestyle Job

Happy Testing! 😊
