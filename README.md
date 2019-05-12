Steps:
1. mvn clean
2. mvn install
3. sudo docker-compose -f docker-compose.yml up

To use TestVMProvisioningService.postman_collection please copy JWT token generated in signin request to all other requests. Postman collections doesnt have dynamic token assignment yet
Additionally for PUT request please use appropriate id received from get all provisioned vms request

To Access UI 3000 port 
http://localhost:3000/login
