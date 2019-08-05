mvn clean jacoco:prepare-agent test jacoco:report surefire-report:report-only
open target/site/jacoco/index.html > /dev/null 2>&1