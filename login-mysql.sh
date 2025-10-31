#!/usr/bin/env bash

DB_PASS=$(cat "./mysql-user-docker-secret")
DB_USER=$(cat "./mysql-user-docker-username")
NETWORK="glitch-loves-wrasslin_wrasslin-network"
#NETWORK="wrasslin_wrasslin-network"
 
docker run -it --network ${NETWORK} --rm mysql:8.0 mysql -hmysql -u${DB_USER} -p${DB_PASS} wrasslin-clips
