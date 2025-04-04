#!/bin/bash

# Definir variables de entorno para Java 17
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
export PATH=$JAVA_HOME/bin:$PATH

# Verificar las variables de entorno
echo "JAVA_HOME se ha definido como: $JAVA_HOME"
echo "PATH se ha actualizado a: $PATH"

# Aplicar los cambios al perfil de usuario
echo "export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64" >> ~/.profile
echo "export PATH=\$PATH:\$JAVA_HOME/bin" >> ~/.profile

echo "Las variables de entorno se han configurado correctamente."
