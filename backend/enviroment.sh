#!/bin/bash

# Definir variables de entorno para Java 21
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
export PATH=$JAVA_HOME/bin:$PATH

# Verificar las variables de entorno
echo "JAVA_HOME se ha definido como: $JAVA_HOME"
echo "PATH se ha actualizado a: $PATH"

# Aplicar los cambios al perfil de usuario (sin duplicar si ya existen)
if ! grep -q "export JAVA_HOME=/usr/lib/jvm/java-21-openjdk" ~/.bash_profile; then
  echo "export JAVA_HOME=/usr/lib/jvm/java-21-openjdk" >> ~/.bash_profile
fi

if ! grep -q 'export PATH=\$JAVA_HOME/bin:\$PATH' ~/.bash_profile; then
  echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bash_profile
fi

echo "Las variables de entorno se han configurado correctamente en ~/.bash_profile."
