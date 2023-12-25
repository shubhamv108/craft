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


# Replace
### craft
### craft
### Craft

# Github Secrets
DOCKER_USER
DOCKER_PASSWORD


# APIs
## DriverOnboarding
1. /drivers/onboard/v1/updateStatus
   <details>
      <summary>Request</summary>
      ```
         {
            ""
         }
      ```
   </details>
2. /drivers/onboard/v1/updateStatus