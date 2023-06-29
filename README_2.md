# Yorkshire Arboretum Developer Docs

## Initiating the database and website

There are two property files the repository requires in order to to create a new database or run. These two 
properties files are to be located in the directory "src/main/resources/". These files must be included in the .
gitignore file as they contain sensitive information.

### 1) Create "admin.properties" file 
Contains the initial user for the database username and password. The fields "Username" and "password" should be
set to the admins initial username and password, respectively. The files example contents are below:

```properties
admin.initialUsername=Username

admin.initialPassword=password
```

### 2)Create the "application.properties" file 
This contains the base URL of the hosted database that the website accesses as well as the admin username and
password for the database. The content to include is below: 

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/<DataBase_Path>?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true  
spring.jpa.hibernate.ddl-auto=update  
spring.datasource.username=<mysql-username>  
spring.datasource.password=<mysql-password>  

rsa.rsa-private-key=classpath:certs/private.pem  
rsa.rsa-public-key=classpath:certs/public.pem  
```

#### 2.1) Set database path
Replace "localhost:3306/<DataBase_Path>" with your ***base thingy***.

#### 2.2) Set database username
This is for the database, so must be different to your initial user for the website. Replace <mysql-username> in 
above properties template.

#### 2.3) Set database password
Replace <mysql-password>

#### 2.4 Create the public and private keys
The contents of "make-keys.sh" should be copied and run in the terminal, this file is located in folder 
"src/main/resources/certs". Once run the files "public.perm" and "private.perm" should now be present in this same 
directory. 

The references for these two keys are listed in the last line of "application.properties" and no change is required.

### Function of initialisation

For the website to function, we need to create an initial admin so that other admins can be created. When the
server is initialised a command line runner in the main file runs a method in the UserService. This method checks if
the initial user is there, if it is there it does nothing, but if it is not and initial username and password is
initialised.

Four main files carry out this function. **application.properties** and **admin.properties** have been discussed
above. The file **src/main/resources/AdminProperties.java** retrieves the initial admin username and password from
**admin.properties**.

**src/main/com.example.security/YorkshireArboretum** contains the command line runner that sets the initial the
admin username and password, if it is not already present.

**src/main/com.example.security/service/UserService.java** contains the method that is called in YorkshireArboretum
to create the initial username and password.

### Testing of initialisation

- Testing of this section is in Mockito. It consists of checking if the user does or does not already exist when 
  initialising the database.  
- There is also testing for the correct response when a user logs in for both if they exist and do not exist.  



