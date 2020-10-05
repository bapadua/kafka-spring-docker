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

  else
    echo "compose not found"
  fi

else
  echo "java not installed"
fi
