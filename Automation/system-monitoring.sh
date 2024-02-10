#!/bin/bash

# Import the file which I created to store my database credentials in, just for the purpose of security.
# This file has all the variables used in this script.
# It contains the database username, password, host, and port. It also contains the backup directory path.
source database-properties.sh

# Check if there's a log file
if [[ ! -f log-file.log ]];
then
	touch log-file.log
fi

# Inserting disk data
function DB_Disk(){
    sqlplus -S ${DB_USER}/${DB_PASSWORD}@$DB_HOST:$DB_PORT << EOF
        INSERT INTO Notifications (DateTime, AnomalyType, Icon, Message)
            VALUES (TO_DATE('$(date +'%F %T')', 'YYYY-MM-DD HH24:MI:SS'), 'Disk Usage', 'resources/disk.png', 'Disk is about to run out.');
        COMMIT;
        EXIT;
EOF

    if [[ $? == 0 ]]
    then
        echo "$(date) --> Disk data inserted in the database succeeded."
    else
        echo "Database Connection Failure: $(date) --> Failure in inserting disk data to the database." >> log-file.log
    fi
}

# Inserting cpu data
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

# Inserting memory data
function DB_Memory(){
    sqlplus -S ${DB_USER}/${DB_PASSWORD}@$DB_HOST:$DB_PORT << EOF
        INSERT INTO Notifications (DateTime, AnomalyType, Icon, Message)
            VALUES (TO_DATE('$(date +'%F %T')', 'YYYY-MM-DD HH24:MI:SS'), 'Memory Usage', 'resources/memory.png', 'Memory Usage exceeds the limits.');
        COMMIT;
        EXIT;
EOF

    if [[ $? == 0 ]]
    then
        echo "$(date) --> Memory data inserted in the database succeeded."
    else
        echo "Database Connection Failure: $(date) --> Failure in inserting memory data to the database." >> log-file.log
    fi
}

# Check for the disk space
disk_threshold=90
disk_usage=$(df -h | awk '{
                        if($6=="/")
                            print $5}' | awk -F% '{print $1}')

if [[ ${disk_usage} -gt ${disk_threshold} ]]
then
    echo "Disk Usage: $(date) --> Disk is about to runout." >> log-file.log
    DB_Disk
fi


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

# Check for the memory usage
memory_threshold=20
memory_usage=$(free | awk '{
                        if(NR==2)
                            print (((($3*100))/$2))}' | awk '{printf("%d\n",$1 + 0.5)}')

if [[ ${memory_usage} -gt ${memory_threshold} ]]
then
    echo "Memory Usage: $(date) --> Memory Usage exceeds the limits." >> log-file.log
    DB_Memory
fi

