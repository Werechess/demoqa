# Test automation project for [DemoQA](https://demoqa.com)

<p align="center">
<img src="media/logotypes/Toolsqa.jpg" alt="Toolsqa">
</p>

> DemoQA is a demo site for QA engineers, learning selenium.\
> Made by Tools QA.\
> Consists of website with training forms and example of bookshop with open API.

# <a name="TableOfContents">Table of contents</a>

+ [Description](#Description)
+ [Tools and technologies](#Technology)
+ [How to run](#Jenkins)
    + [Gradle command](#GradleCommand)
    + [Property files](#PropertyFiles)
    + [Run in Jenkins](#RunInJenkins)
+ [Telegram Notifications](#TelegramNotifications)
+ [Test results report in Allure Report](#AllureReport)
+ [Allure TestOps integration](#AllureTestOps)
    + [Project in Allure TestOps](#AllureTestOpsProject)
    + [Start a run of custom set of tests](#AllureTestOpsStartTests)
    + [Dashboard](#Dashboard)
    + [Defects](#Defects)
+ [Video of running tests](#Video)

# <a name="Description">Description</a>

The test project consists of Web and API tests.\
A brief list of interesting facts about the project:

- [x] `Page Object` with steps using `Chain of Invocations`
- [x] Fake data generating with `Faker` library
- [x] Parametrized tests
- [x] Parametrized build
- [x] Different configuration files for test running depending on build parameters
- [x] Config with `Owner` library
- [x] Use `Lombok` for models in API tests
- [x] Objects serialization/deserialization for API requests/responses using `Jackson`
- [x] Using request/response specifications for API tests
- [x] Custom Allure listener for beautiful API requests/responses logging
- [x] `Allure TestOps` integration
- [x] Autotests as test documentation
- [x] Parallel execution
- [x] Failing tests retries

# <a name="Technology">Tools and a technologies</a>

<p align="center">
  <code><img width="5%" title="IntelliJ IDEA" src="./media/logotypes/IDEA-logo.svg"></code>
  <code><img width="5%" title="Java" src="./media/logotypes/java-logo.svg"></code>
  <code><img width="5%" title="Selenide" src="./media/logotypes/selenide-logo.svg"></code>
  <code><img width="5%" title="REST-Assured" src="./media/logotypes/rest-assured-logo.svg"></code>
  <code><img width="5%" title="Selenoid" src="./media/logotypes/selenoid-logo.svg"></code>
  <code><img width="5%" title="Gradle" src="./media/logotypes/gradle-logo.svg"></code>
  <code><img width="5%" title="JUnit5" src="./media/logotypes/junit5-logo.svg"></code>
  <code><img width="5%" title="Allure Report" src="./media/logotypes/allure-Report-logo.svg"></code>
  <code><img width="5%" title="Allure TestOps" src="./media/logotypes/allure-ee-logo.svg"></code>
  <code><img width="5%" title="Github" src="./media/logotypes/git-logo.svg"></code>
  <code><img width="5%" title="Jenkins" src="./media/logotypes/jenkins-logo.svg"></code>
  <code><img width="5%" title="Telegram" src="./media/logotypes/Telegram.svg"></code>
</p>

The autotests in this project are written in `Java` using `Selenide` framework.\
`Gradle` - is used as a build automation tool.  \
`JUnit5` - to execute tests.\
`REST Assured` - for easy API testing of REST services.\
`Jenkins` - CI/CD for running tests remotely.\
`Selenoid` - to remote launching browsers in `Docker` containers.\
`Allure Report` - for test results visualisation.\
`Telegram Bot` - for test results notifications.\
`Allure TestOps` - as Test Management System.

[Back to the table of contents ⬆](#TableOfContents)

# <a name="HowToRun">How to run</a>

## <a name="GradleCommand">Gradle command</a>

To run locally the following command can be is used:

```bash
gradle clean test
```

Additional parameters:
> `-Dthreads=<number_of_threads>` can be added for parallel tests execution\
> `-Denv=remote` can be added for remote tests execution when remote url is set in remote.properties

`-Dtag=<tag>` - tests with this tag will be executed:
>- *api*
>- *ui*

Additional properties are retrieved from the corresponding properties files:

```bash
./resources/config/${value}.properties
```

[Back to the table of contents ⬆](#TableOfContents)

## <a name="PropertyFiles">Property files</a>

Possible properties in a `${env}.properties` file, local or remote:

```properties
browserName=
browserVersion=
browserSize=
baseURL=
isRemote=
remoteURL=
```

>- *browserName* - browser for Web tests, chrome and firefox supported
>- *browserVersion* - version of browser for Web tests
>- *browserSize* - size of browser for Web tests
>- *baseUrl* - base URL for Web tests
>- *isRemote* - defines local or remote environments
>- *remoteURL* - URL for remote WebDriver

Possible properties in a `user.properties` file:

```properties
username=
password=
```

>- *username* - for authorization by old user that has a Git Pocket Guide book added in profile
>- *password* - used for all users in tests

[Back to the table of contents ⬆](#TableOfContents)

## <a name="RunInJenkins">Run in [Jenkins](https://jenkins.autotests.cloud/job/015-Kenzalani-demoqa)</a>

Main page of the build:
<p align="center">
<img src="media/screenshots/JenkinsBuildMainPage.png" alt="JenkinsBuildMainPage" width="950">
</p>

A parametrized Jenkins job can be launched with needed ***parameters***:
<p align="center">
<img src="media/screenshots/JenkinsBuildParameters.png" alt="JenkinsBuildParameters" width="950">
</p>

Sensitive config files are created in build workspace on build start.\
Relatively safe information transferred to the build by gradle arguments (see [Gradle command](#GradleCommand)
section, 'Additional parameters').

After the build is done the test results are available in:
>- <code><strong>*Allure Report*</strong></code>
>- <code><strong>*Allure TestOps*</strong></code> - results are uploaded there and the automated test-cases can be
   automatically updated accordingly to the recent changes in the code.
<p align="center">
<img src="media/screenshots/JenkinsFinishedBuild.png" alt="JenkinsFinishedBuild" width="950">
</p>

[Back to the table of contents ⬆](#TableOfContents)

# <a name="TelegramNotifications">Telegram Notifications</a>

Telegram bot sends a brief report to a specified telegram chat by results of each build.
<p align="center">
<img src="media/screenshots/TelegramNotification.png" alt="TelegramNotification" width="550">
</p>

[Back to the table of contents ⬆](#TableOfContents)

# <a name="AllureReport">Test results report in [Allure Report](https://jenkins.autotests.cloud/job/015-Kenzalani-demoqa/18/allure/)</a>

## Main page

Main page of Allure report contains the following blocks:

>- <code><strong>*ALLURE REPORT*</strong></code> - displays date and time of the test, overall number of launched
    tests, and a diagram with percent and number of passed, failed or broken tests
>- <code><strong>*TREND*</strong></code> - displays trend of running tests for all runs
>- <code><strong>*SUITES*</strong></code> - displays distribution of tests by suites
>- <code><strong>*CATEGORIES*</strong></code> - displays distribution of unsuccessful tests by defect types
<p align="center">
  <img src="media/screenshots/AllureReportMain.png" alt="AllureReportMain" width="950">
</p>

## List of tests with steps and test artefacts

On the page the list of the tests grouped by suites with status shown for each test.\
Full info about each test can be shown: tags, severity, duration, detailed steps.

Also additional test artifacts are available:
>- Screenshot
>- Page Source
>- Browser console log
>- Video

<p align="center">
  <img src="media/screenshots/AllureReportSuites.png" alt="AllureReportSuites" width="1150">
</p>

<p align="left">
  <img src="media/screenshots/AllureReportBehaviors.png" alt="AllureReportBehaviors" width="950">
</p>

[Back to the table of contents ⬆](#TableOfContents)

# <a name="AllureTestOps">[Allure TestOps](https://allure.autotests.cloud/project/1810/dashboards) integration</a>

## <a name="AllureTestOpsProject">Project in Allure TestOps</a>

Test-cases in the project are imported and constantly updated from the code,
so there is no need in complex process of synchronization manual test-cases and autotests.\
It is enough to create and update an autotest in the code and the test-case in TMS always will be in actual state.\
Manual test-cases also can be added in TMS in case of need (via web interface or via code).
<p align="center">
  <img src="media/screenshots/AllureTestOpsTests.png" alt="AllureTestOpsTests" width="1050">
</p>

## <a name="AllureTestOpsStartTests">Ability to start a run of custom set of tests from Allure TestOps</a>

Any person not related to autotest creation can select a set of tests, environment parameter(RunIn) and start a run.\
Allure TestOps run will be created, Jenkins job triggered with correct parameters. And results of the job will be
seamlessly integrated into Allure TestOps.
<p align="center">
  <img src="media/screenshots/AllureTestOpsSelectionOfTests.png" alt="AllureTestOpsSelectionOfTests" width="1050">
</p>

As soon as the Jenkins job is done, corresponding tests get their statuses. A tester can finish manual tests (if any)
and click "Close launch".

<p align="center">
  <img src="media/screenshots/AllureTestOpsLaunches.png" alt="AllureTestOpsLaunches" width="1250">
</p>

> After that all these test-cases(names, steps, tags etc.) will be updated according to the recent code changes.
[Back to the table of contents ⬆](#TableOfContents)

## <a name="Dashboard">Dashboard</a>

Automation trends charts, distribution tests by some different parameters etc.:
<p align="center">
  <img src="media/screenshots/AllureTestOpsDashboard.png" alt="AllureTestOpsDashboard" width="1050">
</p>

[Back to the table of contents ⬆](#TableOfContents)

## <a name="Defects">Defects</a>

Knows defects are automatically recognized by defined patterns for test fails in further launches.
<p align="center">
  <img src="media/screenshots/AllureTestOpsDefects.png" alt="AllureTestOpsDefects" width="1050">
</p>

[Back to the table of contents ⬆](#TableOfContents)

# <a name="Video">Video of running tests</a>

<p align="center">
  <img src="media/screenshots/VideoForm.gif" alt="VideoForm" width="1050">
</p>

<p align="center">
  <img src="media/screenshots/VideoSearch.gif" alt="VideoSearch" width="1050">
</p>

[Back to the table of contents ⬆](#TableOfContents)
