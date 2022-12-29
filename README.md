### Automatic Irrigation System:

## How To Run

1. run `docker-compose up`
2. add keycloak to host file
    - windows
        - path: `C:\Windows\System32\drivers\etc`
        - add `127.0.0.1 keycloak` to host file

3. Create Users 
   1. open [http://keycloak:8080/](http://keycloak:8080/)
   2. click `Administration Console` button
   3. username and password: `admin`
   4. ![1.png](/README_IMAGE/1.png)
   5. enter any username, such as `myadmin` or `myiot`
   6. and any password ![2.png](/README_IMAGE/2.png)
   7. choose user role `IOT` or `ADMIN` ot both
      - ![3.png](/README_IMAGE/3.png)
4. get client secret
   1. ![4.png](/README_IMAGE/4.png)
   2.t token via: [http://keycloak:8080/auth/realms/iot_auth/protocol/openid-connect/token](http://keycloak:8080/auth/realms/iot_auth/protocol/openid-connect/token)
   - client_id=automaticirrigationsystem
   - grant_type=password
   - ![6.png](/README_IMAGE/6.png)
5. use access_token to access endpoints
   - ![7.png](/README_IMAGE/7.png)


### How To use

- [automaticIrrigationSystem.postman_collection.json](automaticIrrigationSystem.postman_collection.json)