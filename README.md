Setting up the database
=======================

1. Project uses MySQL as the database. You can either install MySQL on your local machine or use a Docker container.
    1. If you want to use a Docker container, you can use the following command to run a MySQL container:
        ```bash
        docker run --name {{mysql_container_name}} -e MYSQL_ROOT_PASSWORD={{mysql_root_password}} -d -p 3306:3306 mysql/mysql-server
        ```
        Replace `{{mysql_container_name}}` with the name you want to give to the container and `{{mysql_root_password}}` with the password you want to set for the root user.
        In our case, we use "admin-panel-mysql-container" as the container name and "password" as the password for the root user.
    2. If you want to install MySQL on your local machine, you can follow the instructions [here](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/).
2. If you pass the root password when starting the container, the 3rd and 5th steps won't be necessary.
3. When running MySQL container, the following command will give you the initially generated password for the root user:
    ```bash
    docker logs {{mysql_container_name}} 2>&1 | grep GENERATED
    ```
    You can also find the password in the `{{mysql_container_name}}` container's `/var/log/mysqld.log` file.
3. Get into the MySQL container using the following command:
    ```bash
    docker exec -it {{mysql_container_name}} mysql -uroot -p
    ```
    You will be prompted to enter the password for the root user. Enter the password you got in the previous step.
4. If you are getting the following error: "ERROR 1820 (HY000): You must reset your password using ALTER USER statement before executing this statement", alter the password with the following command:
    ```sql
    ALTER USER 'root'@'localhost' IDENTIFIED BY '{{mysql_root_password}}';
    ```
    Replace `{{mysql_root_password}}` with the password you set for the root user.
5. Create a new user for your application:
    ```sql
    CREATE USER '{{admin_panel}}'@'%' IDENTIFIED BY '{{admin_panel_password}}';
    ```
   '%' is important here as it will allow the user to connect from any host, otherwise you will get the following error: "Access denied for user '{{admin_panel}}'@'localhost'".
   We use "newuser" as the username and "password" as the password for the user.
6. Grant all privileges to the user:
    ```sql
    GRANT ALL PRIVILEGES ON *.* TO 'newuser'@'%';
    ```
7. Flush privileges:
    ```sql
    FLUSH PRIVILEGES;
    ```
8. Create a database called `admin_panel`"
    ```sql
    CREATE DATABASE admin_panel;
    ```