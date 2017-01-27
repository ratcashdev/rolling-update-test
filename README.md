# rolling-update-test
Simple project to facilitate the testing of Payara's rolling update feature

##Usage

1. start PAYARA `asadmin start-domain payaradomain`
2. git checkout 73ec4234
3. build using `mvn clean package`
4. deploy to Payara using: `asadmin deploy --enabled false --name=app:1 rollingUpdate.war`
5. git checkout d5410a28
6. build using `mvn clean package`
7. deploy to Payara using: `asadmin deploy --enabled false --name=app:2 rollingUpdate.war`
8. enable version 1: `asadmin enable app:1`
9. list apps: `asadmin list-applications --long=true`
10. load the src/test/resources/RollingUpdateReport.jmx into JMeter, and start the test
11. while the test runs, issue `asadmin enable app:2`

Expected result: 

1. all requests are GREEN in JMETER. No 404, or 503 responses
2. all responses should read: `The chemistry teacher's laborant had a successful experiment with the following config: {payara-sampleConfig} and temperature: ` with an arbitrary temperature above 0.
3. None of the responses should have a response with "temperature:0" (except the very first request)
4. From the moment app:2 is enabled, gradually all responses shoudl read `The physics teacher's laborant had a failed experiment with the following config: {payara-productionConfig} and temperature: ` above 5000 (and not 0)

If all is well, disable SyncRequest in JMETER and instead, enable AsyncRequest in JMETER and try again.
