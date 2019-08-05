# Keenan's Point of Sale

### Summary
The Keenan's Point of Sale is a sample application using stdin and stdout.

### Running the Application

To run the application use the following command:

`sh run-keenan-pos.sh`

This will run the application using SpringBoot. 

Please note, the `run-keenan.pos.sh` script uses maven to execute the application.
It's recommended to first build the application to download all of the dependencies.

`mvn clean compile`

This application was tested running Java8 on MacOSX.

### Unit Testing

To run units with code coverage, use the following maven command:

`mvn clean jacoco:prepare-agent test jacoco:report surefire-report:report-only`

Review Reports when the units tests have completed successfully.

`open target/site/jacoco/index.html`

Alternatively, you can run the helper test script `run-unit-tests.sh` to execute all tests 
and open the results of the tests with code coverage.

`sh run-unit-tests.sh`

Only tests for the TaxService and ProductSearchHelper are implemented.

### Improvements

* Update the ViewHandler to manage the views so that some code doesn't need to be repeated
* Implement a templating system that will do a better job of rendering static content
* Implement unit tests for all classes and code coverage