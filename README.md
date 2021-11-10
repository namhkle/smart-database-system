# Smart DBMS System
In a world where automated processes are becoming the norm, the Smart DBMS System may be leveraged by users for managing database computing resources in a distributed database management system more efficiently. Using machine learning algorithms allows the Smart DBMS software to dynamically turn on/off database nodes
to avoid wasting computer resources.  

## Resources Required:
- Java 11
- Python 3
- PostgreSQL
- Linux Unbuntu (version: 20.4)
- HAProxy (for load balancing)
 - More information: http://www.haproxy.org/#desc
- HAProxy Runtime API
 - More information: https://www.haproxy.com/blog/dynamic-configuration-haproxy-runtime-api/



## Limitations: 
- The software only makes predictions on whether to increase or decrease the number of nodes by 1 or leave them as is after making predictions. Working with minimal data and resources, it was thought best to keep the predictions simple. In order to make more accruate predictions you may want to modify the Model Predictor module. 
- Database connections and are hardcoded in the JDBC module and credentials are not hidden. In order to create a secure and dynamic implementation of the software, this module will need to be updated. 
- This software assumes that users are familiar distributed database systems. Before starting if you are unfamiliar with the concept, visit https://www.tutorialspoint.com/distributed_dbms/distributed_dbms_quick_guide.htm
- This software uses the *dvdrental database* and executes hardcoded queries for proof of concept. This downloadable database can be found at https://www.postgresqltutorial.com/postgresql-sample-database/
- This software uses HAProxy for it's load balancing. HAProxy load balancer connections cannot tell the difference between read and write queries so for queries to be distributed properly, two connections are established on the two ports specified in your HAProxy config file . These connections are hardcoded in the software and for personal use you may modify them. 
 - For those unfamiliar with HAProxy configuration, see https://www.haproxy.com/blog/the-four-essential-sections-of-an-haproxy-configuration/
- This software was developed on the same server hosting the HAProxy load balancer, therefore, in order for it run on a separate server, you must perform a few additional steps. 
 - Enable SSH and share the generated keys between the software hosting server and the load balancing server. 
 - The commands for retrieving CPU and Memory usage reside in the ModelPredictor. These must be modified to first SSH into the server hosting the load balancer in order to retrieve statistics correctly. 
- The methods written to manually modify database node states also make the assumption that the software is being run on the same server as the load balancer. SSH wilneed to be enabled between these two hosting devices and the commands for node modification and status viewing in the DatabaseNodeService module will need to be updated.  





## How To Use:
Upon running the system, the user will be met with 7 options:
1. Show server state
 - Shows the state of the server.
2. Turn off node
 - Shuts off a node (if there is more than 1 on).
3. Turn on node
 - Turns on a node (if there is one available).
4. Execute Read Queries
 - Runs queries that return information.
5. Execute Write Queries
 - Runs queries that change information on tables.
6. Execute Mix of Queries
 - Runs both read and write queries.
7. Exit
 - Stops the program


As shown above, users have the option to manually modify and view database node states as well as execute queries. This was implemented as a means of testing the program. When the application is started, it immediately begins the process of gathering statistics for the machine learning models predictions, as well as setting the interval at which the model makes its predictions using the gathered data. 
