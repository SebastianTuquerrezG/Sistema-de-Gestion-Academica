#!/bin/bash

# Definir la lista de microservicios
services=("common-utilities-service" "eureka-server" "evaluation-service" "gateway" "ModuloRubricaCriterio_Hexagonal" "auth-service")
failed_services=()

echo "🚀 Generando .jar para todos los servicios..."

for service in "${services[@]}"; do
  echo "📦 Compilando $service ..."
  cd "$service" || { echo "❌ No se pudo entrar a $service"; failed_services+=("$service"); cd ..; continue; }

  mvn clean package -DskipTests
  if [ $? -eq 0 ]; then
    echo "✅ $service compilado con éxito."
  else
    echo "❌ Error al compilar $service"
    failed_services+=("$service")
  fi

  cd ..
done

if [ ${#failed_services[@]} -eq 0 ]; then
  echo "🎉 Todos los servicios han sido compilados exitosamente."
else
  echo "❌ Falló la compilación de los siguientes servicios:"
  for failed in "${failed_services[@]}"; do
    echo "   - $failed"
  done
  exit 1
fi