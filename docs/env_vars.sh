#!/bin/sh

# usage: source env_vars.sh
echo "> setting ENV vars"
export LS_DB_HOST="127.0.0.1"
export LS_DB_USER="dbuser"
export LS_DB_PASS="dbuser"
export LS_DB_NAME="phase0"

printenv | grep LS_DB


#LS_DB_HOST=127.0.0.1
#LS_DB_USER=dbuser
#LS_DB_PASS=dbuser
#LS_DB_NAME=phase0