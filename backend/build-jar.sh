#!/bin/bash

# Definir la lista de microservicios
services=("common_utilities-service" "eureka-server" "evaluation-service" "gateway" "ModuloRubricaCriterio_Hexagonal" "auth-service")

echo "🚀 Generando .jar para todos los servicios..."

for service in "${services[@]}"; do
  echo "📦 Compilando $service ..."
  cd "$service" || { echo "❌ No se pudo entrar a $service"; exit 1; }
  mvn clean package -DskipTests
  if [ $? -eq 0 ]; then
    echo "✅ $service compilado con éxito."
  else
    echo "❌ Error al compilar $service"
  fi
  cd ..
done

echo "🎉 Todos los servicios han sido compilados."
