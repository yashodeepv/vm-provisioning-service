# Basic VM Provisioning Service with JWT based security and ReactJS UI integrated using DOCKER

## Technologies Used:
1. Spring Boot
2. Spring Security
3. Java 8
4. Junit and Mockito Based test cases
5. React JS
6. Docker

## How to run:
1. mvn clean
2. mvn install
3. sudo docker-compose -f docker-compose.yml up
4. To Access UI 3000 port http://localhost:3000/login

## Demo Postman Collection
To use TestVMProvisioningService.postman_collection please copy JWT token generated in signin request to all other requests. Postman collections doesnt have dynamic token assignment yet
Additionally for PUT request please use appropriate id received from get all provisioned vms request
