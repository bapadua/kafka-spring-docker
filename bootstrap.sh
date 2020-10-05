#! /bin/bash
# Author Bruno A. Padua
# 2020

echo "Starting Script para o campaign test"

#verificar se o existe java na maquina
if type -p java
then
  echo "Java OK"
  if type -p docker-compose
  then
    echo "Docker Compose OK"
    command docker-compose down
    command docker network rm campaign-network
    command docker network create campaign-network --gateway 10.0.5.1 --subnet 10.0.5.0/24
    command docker-compose up -d
  else
    echo "compose not found"
  fi

else
  echo "java not installed"
fi
