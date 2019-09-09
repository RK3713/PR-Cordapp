<p align="center">
  <img src="https://www.corda.net/wp-content/uploads/2016/11/fg005_corda_b.png" alt="Corda" width="500">
</p>

# PR - CorDapp - Java

Welcome to the PR - CorDapp. This CorDapp is one of the module for Permanent Relocation
process (PR - Process) 


# Pre-Requisites

 * Install Java 8
 * Install IntelliJ IDEA
 * Install Postman


# Running the PR - CorDapp

### First Way:

**Commands:**
 * `gradlew clean build`
 * `gradlew clean deployNodes`
 
 Navigate to `/build/nodes` folder 
   * `cd build/nodes`
   * `runnodes.bat` 
 
 **Running the Web-Servers:**
 
 There are 3 web-servers for 3 nodes. Open separate consoles for each web-server.
 
 * `gradlew runConsultantServer`
 * `gradlew runWesServer`
 * `gradlew runUniversityServer`
 
 
 
### Second Way:
 
 **Commands:**
  * `gradlew clean build`
  * `gradlew clean deployNodes`
  * `gradlew createServer`
  
Navigate to `/build/nodes` folder 
   * `cd build/nodes`
   * `runnodes.bat`
   * `runserver.bat`
   
    "runserver.bat" file takes care of running all webservers.

    
 
 ## Running API's
 
 
 Hit POST api from Consultant to Wes. Use prRequest.json in json folder as input.
 * http://localhost:50020/consultant/
 
 Hit GET api from Consultant to check all pr requests.
 * http://localhost:50020/consultant/
 
 
 
 
 
 
 
 
   
 



To switch to using the Gradle runner:

* Navigate to ``Build, Execution, Deployment -> Build Tools -> Gradle -> Runner`` (or search for `runner`)
  * Windows: this is in "Settings"
  * MacOS: this is in "Preferences"
* Set "Delegate IDE build/run actions to gradle" to true
* Set "Run test using:" to "Gradle Test Runner"

If you would prefer to use the built in IntelliJ JUnit test runner, you can run ``gradlew installQuasar`` which will
copy your quasar JAR file to the lib directory. You will then need to specify ``-javaagent:lib/quasar.jar``
and set the run directory to the project root directory for each test.





## Interacting with the nodes

### Shell

When started via the command line, each node will display an interactive shell:

    Welcome to the Corda interactive shell.
    Useful commands include 'help' to see what is available, and 'bye' to shut down the node.
    
    Tue Nov 06 11:58:13 GMT 2018>>>

You can use this shell to interact with your node. For example, enter `run networkMapSnapshot` to see a list of 
the other nodes on the network:

    Tue Nov 06 11:58:13 GMT 2018>>> run networkMapSnapshot
    [
      {
      "addresses" : [ "localhost:10002" ],
      "legalIdentitiesAndCerts" : [ "O=Notary, L=London, C=GB" ],
      "platformVersion" : 3,
      "serial" : 1541505484825
    },
      {
      "addresses" : [ "localhost:10005" ],
      "legalIdentitiesAndCerts" : [ "O=PartyA, L=London, C=GB" ],
      "platformVersion" : 3,
      "serial" : 1541505382560
    },
      {
      "addresses" : [ "localhost:10008" ],
      "legalIdentitiesAndCerts" : [ "O=PartyB, L=New York, C=US" ],
      "platformVersion" : 3,
      "serial" : 1541505384742
    }
    ]
    
    Tue Nov 06 12:30:11 GMT 2018>>> 

You can find out more about the node shell [here](https://docs.corda.net/shell.html).

### Client

`clients/src/main/java/com/template/Client.java` defines a simple command-line client that connects to a node via RPC 
and prints a list of the other nodes on the network.

#### Running the client

##### Via the command line

Run the `runTemplateClient` Gradle task. By default, it connects to the node with RPC address `localhost:10006` with 
the username `user1` and the password `test`.

##### Via IntelliJ

Run the `Run Template Client` run configuration. By default, it connects to the node with RPC address `localhost:10006` 
with the username `user1` and the password `test`.

### Webserver

`clients/src/main/java/com/template/webserver/` defines a simple Spring webserver that connects to a node via RPC and 
allows you to interact with the node over HTTP.

The API endpoints are defined here:

     clients/src/main/java/com/template/webserver/Controller.java

And a static webpage is defined here:

     clients/src/main/resources/static/

#### Running the webserver

##### Via the command line

Run the `runTemplateServer` Gradle task. By default, it connects to the node with RPC address `localhost:10006` with 
the username `user1` and the password `test`, and serves the webserver on port `localhost:10050`.

##### Via IntelliJ

Run the `Run Template Server` run configuration. By default, it connects to the node with RPC address `localhost:10006` 
with the username `user1` and the password `test`, and serves the webserver on port `localhost:10050`.

#### Interacting with the webserver

The static webpage is served on:

    http://localhost:10050

While the sole template endpoint is served on:

    http://localhost:10050/templateendpoint
    
# Extending the template

You should extend this template as follows:

* Add your own state and contract definitions under `contracts/src/main/java/`
* Add your own flow definitions under `workflows/src/main/java/`
* Extend or replace the client and webserver under `clients/src/main/java/`

### Developers:
* [Rishi Kundu](https://www.linkedin.com/in/rishi-kundu-1990/)
* [Ajinkya Pande](https://www.linkedin.com/in/ajinkya-pande-013ab5106/)

##### Please feel free to raise a pull request.
