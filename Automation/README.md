# University-Data-Management-System

## About the automation process ##
The automation process handles the database backup and system monitoring to automatically send alerts to the admin.

_As mentioned, there's 2 tasks that are automated:_
1. Database Backup
2. System Monitoring

In the following sections, those 2 tasks will be explained briefly.

---

## Database Backup ##

As I used __Oracle DBMS__ to impelement the database. So, `expdp` is the utility which is responsible of backing up the database with its metadata and data.

_The automation process of the database backup is responsible of some tasks:_
1. Creating a backup directory if there's not a one.
2. Backing up the database and store the `dmp` file in the specified directory. Also, there's a `log` file which stores the results of the backup.
3. Check whether the process run successfully or not and note the success or the failure of the backup in a log file which the admin has access on.


> Note that to run the database backup script, you should specifiy a backup directory in _Oracle_ first, and use this directory in the command `expdp`. 

_You can follow the steps below to create a backup directory in __Oracle__:_

```shell
# Connect to Oracle as SYS
CONN SYS/ORCL AS SYSDBA

# Then, create the backup directory
CREATE DIRECTORY backup_dir AS '<directory_path>';
```

[Click here to view the whole script of the database backup.](university-database-backup.sh)

---

## System Monitoring ##

System Monitoring is the task which is reponsible of monitoring the resources such as `CPU`, `Memory`, and `Disk Usage`, then sending alerts to the admin if there're issues.

This task has an extra feature which is the script be able to connect to our database on __Oracle__ and store the alerts in addition to storing them in a log file (created if there's not a one).

_Following is a sample of the code, it monitors the CPU usage:_

```shell
# The function which is reponsible of connecting to the database and insert the alert
function DB_CPU(){
    sqlplus -S ${DB_USER}/${DB_PASSWORD}@$DB_HOST:$DB_PORT << EOF
        INSERT INTO Notifications (DateTime, AnomalyType, Icon, Message)
            VALUES (TO_DATE('$(date +'%F %T')', 'YYYY-MM-DD HH24:MI:SS'), 'CPU Usage', 'resources/cpu.png', 'User CPU Usage exceeds the limits.');
        COMMIT;
        EXIT;
EOF

    if [[ $? == 0 ]]
    then
        echo "$(date) --> CPU data inserted in the database succeeded."
    else
        echo "Database Connection Failure: $(date) --> Failure in inserting cpu data to the database." >> log-file.log
    fi
}


# Check for the CPU usage
cpu_threshold=20
user_cpu_usage=$(mpstat | awk '{
                        if(NR==4)
                            print (($3*100))}')

if [[ ${user_cpu_usage} -gt ${cpu_threshold} ]]
then
    echo "CPU Usage: $(date) --> User CPU Usage exceeds the limits." >> log-file.log
    DB_CPU
fi

```

[Click here to view the whole script of the system monitoring.](system-monitoring.sh)

---

## Passing variables to the scripts ##

You may notice that the 2 scripts have some variables which are stored in another script `database-properties`. This script holds the credentials of my oracle database connection, and the backup directory that the database is backed up in.

This is for the aim of security to my __Oracle DBMS Service__. But, you can create your own bash file which holds your credentials.

_Follow the steps below to do so:_

```shell
#!/bin/bash

BACKUP_DIR=<your_backup_directory>
DB_BACKUP_DIR=<database_backup_directory>
DB_USER=<oracle_username>
DB_PASSWORD=<oracle_password>
DB_HOST=<host>
DB_PORT=<port_number>
```

> Note that each of the variables should be written withing single quotes except the port number.

---