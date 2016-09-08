# MastersCapstone
Repo for Masters Capstone Project
Project Built using SpringBoot, Angular2, and Semantic UI

##Snapshot Build, Install, & Usage Instructions
1. *Prerequisites* - Ensure your system has Java/jdk8, nodejs, npm, and 
   Maven installed and properly configured   
2. Clone the repo
3. Navigate to the src/main/resources/static/ directory
    * Execute the following command: *npm install*
    * The command above will install all Angular2 dependencies in 
      the /src/main/resources/static/node_modules directory
    * Execute the following command:  *npm start*
        *  This will start and build the front end only
        *  Stop the running script (ctrl-c etc.) and continue
4. Using the command line or your IDE execute the following command
   at the project root: *mvn clean install*
5. Navigate to the /target directory
    * The following file should be present: *masterscapstone-0.0.1-SNAPSHOT.jar*
    * Execute the following command: *java -jar masterscapstone-0.0.1-SNAPSHOT.jar*
6. Using your favorite browser navigate to *http://localhost:8080/*