@echo off
docker-compose down
docker network rm campaign-network
docker network create campaign-network --gateway 10.0.5.1 --subnet 10.0.5.0/24
docker-compose up -d
pause