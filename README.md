<p align="center">
  <img src="https://www.corda.net/wp-content/uploads/2016/11/fg005_corda_b.png" alt="Corda" width="500">
</p>

# PR - CorDapp - Java

Welcome to the PR - CorDapp. This CorDapp is one of the module for Permanent Relocation
process (PR - Process) and emphasizes on  There are 3 parties - Consultant, Wes and University.

* **Consultant**: It initiates the PR - Process.
* **Wes**: It approves the PR request and provides ECA report .
* **University**: It sends transcripts to Wes.


# Pre-Requisites

 * Install Java 8
 * Install IntelliJ IDEA
 * Install Postman


# Running the PR - CorDapp

### First Way:

**Commands:**
```
 * gradlew clean build
 * gradlew clean deployNodes
```
 
 Navigate to `/build/nodes` folder 
```
   * cd build/nodes
   * runnodes.bat 
```
  
 
 **Running the Web-Servers:**
 
 There are 3 web-servers for 3 nodes. Open separate consoles for each web-server.
 
 **Commands:**
 
 ```
   * gradlew runConsultantServer
   * gradlew runWesServer
   * gradlew runUniversityServer
 ```
 
 
 
 
 
### Second Way:
 
 **Commands:**
  ```
    * gradlew clean build
    * gradlew clean deployNodes
    * gradlew createServer
  ```
 
  
Navigate to `/build/nodes` folder 

```
    * cd build/nodes
    * runnodes.bat
    * runserver.bat
  ```   
    "runserver.bat" file takes care of running all the webservers.

    
 
 ## Running API's
 
 Please import api's from below link in postman.
 
  `https://www.getpostman.com/collections/5b924208b191625557d9`
 
  * Issue Cash to Consultant **(IssueCash)** .     One can change cash amount and currency in request parameters

    `http://localhost:8081/consultant/cash?amount=50000&currency=USD`
    
  * Check cash balance for a consultant node. **(GetNodeCashBalance)**    
    `http://localhost:8081/consultant/cash`
  
  * Send PR request from Consultant to Wes. One can update json body. **(SendPRRequest)**  
      `http://localhost:8081/consultant/`
  
  * Get the PR request created and copy `wesReferenceNumber (Id)` from response. One can run same api on Consultant and Wes.  **(GetPRRequest)**
    `http://localhost:8081/consultant/`
    
  * Respond to PR request created from Wes. **(SendResponseToPRRequest)**
    `http://localhost:8083/wes/{wesReferenceId}`
    
  * Raise request for student transcript and add `wesReferenceId` in json body. **(RaiseStudentAcademicTranscriptRequest)**
    `http://localhost:8081/consultant/transcript/CREATE`
    
  * Get students transcript request details and copy `requestId` from response. **(GetAllStudentsAcademicTranscriptRequestDetails)**
    `http://localhost:8081/consultant/transcript/1` 
    
  * Add transcript details by University for a particular requestId. **(AddTranscriptDetails)**
    `http://localhost:8082/university/transcript/{requestId}`
    
  * Confirm details of a transcript request. **(ConfirmTranscriptDetails)**
    `http://localhost:8081/consultant/transcript/{requestId}`
    
  * Send confirmed transcripts to Wes from University. **(ReadyForWES)**
    `http://localhost:8082/university/transcript/{requestId}` 
    
  * Get updated transcript details at Wes. **(GetAllStudentsAcademicTranscriptRequestDetails)**
    `http://localhost:8082/university/transcript/1`
    
  * Change PR Request's `prStatus` from `APPLICATION_ACKNOWLEDGEMENT` to `DOCUMENT_RECEIVED`. **(SendResponseToPRRequest)**
    `http://localhost:8083/wes/{wesReferenceId}`
    
  * Change PR Request's `prStatus` from `DOCUMENT_RECEIVED` to `DOCUMENT_REVIEWED`. **(SendResponseToPRRequest)**
      `http://localhost:8083/wes/{wesReferenceId}`
    
  * Send ECA details to PR request **(SendECAResponseToPRRequest)**
    `http://localhost:8083/wes/{wesReferenceId}`
    
  * ECA report is created and added to PR-Request. **(GetPRRequest)**
    `http://localhost:8081/consultant/`


## Interacting with the nodes

### Shell

When started via the command line, each node will display an interactive shell:

    Welcome to the Corda interactive shell.
    Useful commands include 'help' to see what is available, and 'bye' to shut down the node.
    
    Wed Sep 18 17:44:16 IST 2019>>>

You can use this shell to interact with your node. For example, enter `run networkMapSnapshot` to see a list of 
the other nodes on the network:

    Wed Sep 18 17:44:16 IST 2019>>> run networkMapSnapshot
    - addresses:
      - "localhost:10005"
      legalIdentitiesAndCerts:
      - "O=Consultants, L=London, C=GB"
      platformVersion: 4
      serial: 1568801538903
    - addresses:
      - "localhost:10008"
      legalIdentitiesAndCerts:
      - "O=University, L=New York, C=US"
      platformVersion: 4
      serial: 1568801533270
    - addresses:
      - "localhost:10011"
      legalIdentitiesAndCerts:
      - "O=Wes, L=London, C=GB"
      platformVersion: 4
      serial: 1568801539478
    - addresses:
      - "localhost:10002"
      legalIdentitiesAndCerts:
      - "O=Notary, L=London, C=GB"
      platformVersion: 4
      serial: 1568801542901 
      
      Wed Sep 18 18:41:56 IST 2019>>>

You can find out more about the node shell [here](https://docs.corda.net/shell.html).


### Developers:
* [Rishi Kundu](https://www.linkedin.com/in/rishi-kundu-1990/)
* [Ajinkya Pande](https://www.linkedin.com/in/ajinkya-pande-013ab5106/)

##### Please feel free to raise a pull request.
