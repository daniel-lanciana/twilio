# About

Coding test for Mast Mobile. Took approximately 12 hours.

# Rules

1. Use any language
2. Public repository on GitHub
3. Attach MIT license
4. Project should take 1 day

# Problem

At Mast Mobile we’re operating a mobile phone network for businesses and their employees.  
As part of running this service, we must ensure that phone calls work as well as – or better than – any of our 
competitors. This is important because part of our offering is software and services that modify call processing and as 
we improve those services, we must ensure that calls continue to function reliably.

**Basics**

* Use the Twilio API
* Application that repeatedly calls a phone using the API
* Analysis on data returned from API (reliability, quality, display data, test calling)
* Log and display results

# Overview

* TDD using JUnit, Hamcrest and Mockito. Unit and integration tests.
* Tools: IDEA 14 (static code analysis) and Git
* Play! Framework
* Deployed to Heroku and Amazon RDS
* System components:
 * /builders: Builder pattern for constructing Twilio outbound call parameter map
 * /controllers: web view and associated function mapped to URL routes
 * /factories: CallService factory for returning a concrete implementation
 * /jobs: Job for repeatedly calling numbers. Randomly calls from a predefined list.
 * /models: PlacedOutgoingCall extends OutgoingCall and provides additional non-null properties
 * /servies: Concrete Twilio implementation of an interface 
 * /translators: Converts from a Twilio call object to local OutgoingCall object
* Features:
 1. Dial a number via Twilio API
 2. Retrieve all calls from Twilio API and persist locally
 3. Start/stop background job that repeatedly call from a list of numbers

# Design and Assumptions

* Web application using Play! framework for rapid web application development
* Use of factory, builder and translator patterns
* For simplicity, API auth token and RDS information committed to Git (private repository)
* 'Clean Code' principles (e.g. naming, only necessary code comments, DRY, SRP, small functions, avoid magic numbers...)
* Use of CallService interface to decouple Twilio implementation and allow future flexibility and extensibility (i.e. swap out Twilio)
* Integration test found at TwilioIntegrationTest
* Persists data to a local H2 file system database

# Issues

* Attempted to use an AWS RDS instance as the database, but Heroku throws an exception starting up (permission debugging)
* Web application previously running on Heroku, but change to model table name throws an exception (table not found). 
Difficult to debug, runs locally, can't inspect database, can't use RDS. Extremely frustrating with limited time.
* Attempted WAR deployment to Elastic Beanstalk (RDS permission issues)
* Persisting JodaTime to database caused adapter issues

# Enhancements

* Deploy to Heroku
* Extract sensitive information (Twilio API token, RDS credentials) to local environment variables (or outside of Git)
* Improved presentation (i.e. HTML5 framwwork, HAML, SASS)
* Display the current job status (started, stopped) on the home page
* 

# Analysis

* Outbound call response
* Get all outbound calls
* Call transcript and recordings
* Fallback URL? (post call)
* API failure
* statusCallback


### Reliability

Reliability can be measured by successful calls placed.

### Quality

Quality can be measured by the time to connect a call and the line quality during. 

statusCallbacks for latency at various stages, time between, success rates
Run samples through batch process.

### Testing

Adjust timeout, recording call true/false, call numbers without permission (international)

# How to Run

1. Install Play! 1.3.0 (http://downloads.typesafe.com/play/1.3.0/play-1.3.0.zip)
2. Clone the GitHub repository
3. In Terminal, navigate to the project directory and enter the command 'play run'
4. Open the browser and navigate to 'http://localhost:9000'