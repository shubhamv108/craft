# craft
    You are part of a team, building a ride management application like Uber. You are responsible
    for the driver onboarding module. As part of this module you are required to build an
    interface that will allow a driver to sign-up as a driver partner. The driver onboarding module
    should:

        a) Allow a driver to sign-up and enter their profile information
        b) Trigger onboarding processes like document collection, background verification, shipping
        of tracking device, etc.,
        c) Allow a driver to mark when they are ready to take a ride

Please create a list of the API interfaces you will expose to support these requirements and
also come up with choices of data stores, tech stacks, etc.
Instructions to the Candidate:
1. When designing your solution, think through resilience, scalability, load balancing,
   sharding etc and be prepared to discuss your approach in detail. Please use some design
   diagrams tools to come up with clear design.
2. We are interested in your approach to the problem. Please build and implement atleast
   2 API’s with Unit test cases.
3. Do take time to clearly define the data models and the API interfaces and please ensure
   all the required components for the Craft Demo should be listed or specified clearly in
   your PPT.
4. You can use pen/paper to answer if there is any questions asked during the interview
   process.

## Github Secrets
      DOCKER_USER
      DOCKER_PASSWORD
      AWS_ACCES_KEY_ID
      AWS_SECRET_ACCESS_KEY
      AWS_DEFAULT_REGION

# Setup
### Local
      make local-setup
      make local-app


# [API Documentation](http://localhost:8080/craft/swagger-ui/index.html#/)


# Tech Stack
##### Code Repository
   - Github

#### Configuration
   - AWS Param Store  
##### Secure Config
   - AWS KMS - Encryption keys
   - AWS Secret manager: Database keys 

(Alternative centralized Spring Config Server (git branch support), Vault for secured keys]

#### Deployment
   - Github Action (format, build, sonarqube static code analysis, test, test coverage, create image, push image to registry)
   - Jfrog artifact code repository (Dependency repository with vulnerability check) (exploring)
   - AWS ECR - Image Repository
   - Prisma Cloud - Image Scan (exploring)
   - AWS Cloudformation (IaC)
   - AWS Codepipeline (Env - DEV, QA, Pre-Prod, Prod (full auto on approval in pre prod )) (Stages - Source (AutoTrigger on commit), setup infra, test, deploy, approve for next env (Manual))


#### DNS
   - Route 53

#### CDN
   - AWS Cloudfront

#### DFS
   - AWS S3


#### API Gateway
   - AWS API Gateway (Routing, Rate Limiting)


##### Auth
   - OAuth2 Google with Role in IAM service for eahc email.  (RBAC Keycloak when allowing self registration)
   - AWS Lambda Authorizer


#### VPC 
   - VPC with private Subnet
   - VPC Link (connect AWS Api Gateway to ELB (on VPC with private subnet))


#### Load Balancing
   - AWS ELB (ALB) - Round Robin

#### Service Host - Stateless
   - AWS ECS (Auto Scaling on CPU (AWS Cloudwatch Alarm on CPU Metric))
(Alternative Kubernetes with Horizontal Pod Autoscaling (not clear on metrics collection in kubernetes))

#### Programming Language
   - Java

#### Framework
   - Spring Boot

#### Circuit Breaker
   - Resilience4j

#### Event Sourcing
   - Eventuate (exploring)

#### Transactional outbox with AWS DynamoDB backed Services
   - DynamoDB Stream
   - Kinesis (Dynamo event stream persisted here for more than 24 hours)
   - AWS Lambda (Consumer)
   - SNS
   - DynamoDB (for offset)

#### Test
   - JaCoCo - Code Coverage
   - Mockito
   - Junit
   - Docker Compose

#### Static code analysis
   - Sonarqube

#### Firewall
   - AWS Security group

#### Cache
- AWS Elasticache (Redis)


#### Databases
   - Postgres (AWS RDS (Multi AZ with Read Replica), RDS Proxy(ConnectionPool)) - Driver, Driver Onboard, Document, Order, OrderProduct, IAM, UserProfile
   - AWS DynamoDB - Blob, Background Verification, Shipment

#### Queue
   - AWS SQS

#### PubSub
   - AWS SNS

#### Logging
   - FlunetBit (Sidecar/Daemon Set in Kubernetes)
   - AWS Cloudwatch Logs
   - AWS Opensearch

#### Metrics & Alarms
##### Push
   - FlunetBit (Sidecar/Daemon Set in Kubernetes)
   - AWS Cloudwatch Logs
   - AWS Cloudwatch Metrics - Dashboard, Alarms
##### Pull
   - Prometheus, InfluxDB (when needed)

#### Exception tracking & Error De-Duplication
   - Sentry

#### Jump Host
   - AWS Session Manager

#### Cron
   - AWS Eventbridge

#### Analytic Report
   - eventbridge
   - sns
   - sqs
   - Schema - AWS Glue 
   - job within service or aws emr
   - S3

#### ServiceMesh
Istio (With kubernetes, exploring)

# High Level Design
![HIghLevelDesign](https://github.com/shubhamv108/craft/assets/16763337/2c653f9a-59b1-4643-9ac8-62e117ab4441)


# Images
#### Generate Google OAuth2 Bearer Token
![Screenshot from 2023-12-31 13-02-34](https://github.com/shubhamv108/craft/assets/16763337/a6f293dc-40d0-4712-bdf7-63839ab0bb75)

