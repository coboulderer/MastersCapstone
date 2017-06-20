# MastersCapstone
Repo for Masters Capstone Project.
Project Built using SpringBoot, Angular2, and Semantic UI

## Snapshot Build, Install, & Usage Instructions
1. *Prerequisites* - Ensure your system has Java/jdk8, nodejs, npm, and 
   Maven installed and properly configured   
2. Clone the repo
3. Navigate to frontend/
    * Execute the following command: *npm install*
    * The command above will install all Angular dependencies in 
      the frontend/node_modules directory
    * Execute the following command: *npm start*
    * The front end of the application should open up in a web browser.  If it doesn't it should 
    be available at *http://localhost:3000*
4. Using the command line or your IDE execute the following command
   in backend/ : *mvn clean install*
5. Navigate to backend/target/ 
    * The following file should be present: *masterscapstone-0.0.1-SNAPSHOT.jar*
    * Execute the following command: *java -jar masterscapstone-0.0.1-SNAPSHOT.jar*
6. With both the back and front ends running the application should function properly.
