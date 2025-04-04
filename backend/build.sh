#!/bin/bash

# Iniciar erueka-server con perfil prod
tmux new-session -d -s eureka-server
tmux send-keys -t eureka-server:0 './environment.sh' C-m 'cd eureka-server' C-m 'mvn spring-boot:run' C-m

# Iniciar gateway con perfil prod
tmux new-session -d -s gateway
tmux send-keys -t gateway:0 './environment.sh' C-m 'cd gateway' C-m 'mvn spring-boot:run' C-m

# Iniciar auth-service con perfil prod
tmux new-session -d -s evaluation-service
tmux send-keys -t evaluation-service:0 './environment.sh' C-m 'cd evaluation-service' C-m 'mvn spring-boot:run' C-m

# Iniciar audit-service con perfil prod
tmux new-session -d -s ModuloRubricaCriterio_Hexagonal-service
tmux send-keys -t ModuloRubricaCriterio_Hexagonal-serv:0 './environment.sh' C-m 'cd ModuloRubricaCriterio_Hexagonal' C-m 'mvn spring-boot:run' C-m

