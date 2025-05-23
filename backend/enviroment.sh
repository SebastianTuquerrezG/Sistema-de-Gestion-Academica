#!/bin/bash

# Definir variables de entorno para Java 21
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
export PATH=$JAVA_HOME/bin:$PATH

# Verificar las variables de entorno
echo "JAVA_HOME se ha definido como: $JAVA_HOME"
echo "PATH se ha actualizado a: $PATH"

# Aplicar los cambios al perfil de usuario (sin duplicar si ya existen)
PROFILE_FILE=~/.bash_profile

if ! grep -q '^export JAVA_HOME=/usr/lib/jvm/java-21-openjdk' "$PROFILE_FILE"; then
  echo 'export JAVA_HOME=/usr/lib/jvm/java-21-openjdk' >> "$PROFILE_FILE"
fi

if ! grep -q '^export PATH=\$JAVA_HOME/bin:\$PATH' "$PROFILE_FILE"; then
  echo 'export PATH=$JAVA_HOME/bin:$PATH' >> "$PROFILE_FILE"
fi

# Recargar configuración del perfil para esta sesión
source "$PROFILE_FILE"

echo "✅ Las variables de entorno se han configurado correctamente en $PROFILE_FILE."
