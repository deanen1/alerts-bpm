### About the alerts-bpm proof-of-concept
---

This application is a proof-of-concept whose aim is to explore some of the basic concepts
involved in orchestrating a business process using the Camunda BPM Platform. The domain and 
business process used is an overly simplified version of the domain and business process for
handling critical lab alerts. These alerts are triggered when a lab result 
meets the criteria defined by our institution's policy on crisis observations. These crisis 
observations represent a critical finding that if not dealt with could lead to serious patient 
harm including death. The goal of the process is to obtain acknowledgement from a licensed 
provider that they have received the alert and take responsibility for any changes in care 
that should be made in response to the alert.

In this simplified workflow when a critical alert occurs:
1. The alert could be acknowledged by a provider using our mobile application or in the lab results
view of our Electronic Health Record System
1. We should attempt to notify the patient's careteam using our Clinical Messaging Gateway 
(this is done asynchronously and the outcome is communicated back to the initiator via http callbacks) 
1. If we are able to notify the patient's care team: 
   1. The system should wait for careteam acknowledgement
   1. If after 7 minutes the careteam has not acknowledged the alert a task should be assigned to
   operator services to begin calling the care team
1. If we are unable to notify the patient's care team 
(because a care team was not assigned or due to technical issues) then we should immediately assign
a task to the lab call center to begin their procedures for contacting the responsible provider

---
### Goals of the proof-of-concept

1. Demonstrate the interaction between the process engine and the external services 
   1. Demonstrate how the process engine is informed about events happening externally - 
   i.e. provider acknowledgment performed in our mobile application
   1. Demonstrate how the process engine would orchestrate calls to external services
   1. Demonstrate how user tasks can be assigned to users or groups
1. Explore the design of the "glue code" application
1. Explore some of the techniques that we might use in modeling the business process 
1. Explore how to test a business process definition 

---
### What is not in scope for this proof-of-concept

1. Optimization of the process or glue code
1. Fully implementing our workflow for handling critical lab alerts or the domain model
1. Exhaustively testing the business process
1. Making actual calls out to external services - these can be stubbed out for now
1. Logging or observability 

--- 
### Future scope to be added

1. Making calls out to actual services from glue code
1. Creating a basic task list application that allows operators and lab call center employees to
record acknowledgement of an alert assigned to them
1. Further explore process testing using ZeebeSpringTest

--
### Implementation notes

The application is...

1. A Spring Boot (v 2.7.18) application
1. Capable of receiving events (new alerts, alert acknowledgements, clinical message callbacks) 
via JMS or an HTTP endpoint
1. Using an embedded Artemis Message Broker to reduce the external dependencies required to run it
1. Using a self-managed instance of the Camunda Platform that is spun up using 
Docker Compose according to the instructions provided by Camunda
1. A JMeter plan has been included in the project to provide a simple way for sending JMS messages 
to test the application - (note: you need the artemis dependencies in Jmeter's lib/ext directory)
1. Testing can also be performed using cUrl or Postman to hit the HTTP endpoints
1. While testing use the Operate Application to review process execution status and results