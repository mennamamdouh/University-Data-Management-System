#!/bin/bash

# Import the file which I created to store my database credentials in, just for the purpose of security.
# This file has all the variables used in this script.
# It contains the database username, password, host, and port. It also contains the backup directory path.
source database-properties.sh

# Check if the backups directory exists
if [[ ! -d $BACKUP_DIR ]];
then
    echo 'Backup directory doesn't exist. I'll create it for you.'
	mkdir $BACKUP_DIR
else
    echo 'Backup directory is found.'
fi

# Define the backup file name and the error file name if error occured while executing the bash
BACKUP_FILE="university_backup_$(date +%Y%m%d).dmp"
ERROR_FILE="${BACKUP_DIR}university_backup_error_logs.error"

# Check if a backup is already created with the same name
if [[ ! -f $BACKUP_FILE ]]
then
    rm "${BACKUP_DIR}${BACKUP_FILE}"
    echo 'Database backup file is already created. Iam about to remove and recreate it again.'
fi
# Execute the command which is responsible of making a backup on Oracle DBMS
expdp ${DB_USER}/${DB_PASSWORD}@$DB_HOST:$DB_PORT DIRECTORY=${DB_BACKUP_DIR} DUMPFILE=${BACKUP_FILE}

# Check for any errors
if [[ $? -eq 0 ]]
then
	echo "$(date) --> Database is backed up successfully."
else
	echo "$(date) --> Database cannot be backuped."
	echo "$(cat ERROR_FILE)"
fi