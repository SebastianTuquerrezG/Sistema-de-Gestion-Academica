[33mcommit dd7df5357fc153ca45aae3c15ffa0014dcda2cc9[m[33m ([m[1;31morigin/dev-WTF[m[33m)[m
Author: Elkin2814 <elkingmorillo@gmail.com>
Date:   Mon May 19 20:34:10 2025 -0500

    Change File Controller

[33mcommit 6c888e3d7bc1e6ee22c15070a6cde6d9ad54f3eb[m
Author: Elkin2814 <elkingmorillo@gmail.com>
Date:   Mon May 19 20:30:56 2025 -0500

    News Endpoints
    
    Funciona? I don't know

[33mcommit aee6ac04be4d001e0db96c7966e9e688993ccff5[m
Author: Jua-n10 <sebastianpisso00272@gmail.com>
Date:   Mon May 19 10:59:18 2025 -0500

    Changes to gateway properties and student query

[33mcommit caa0e24893e563a0b1666dcd648283c6e940167d[m
Author: Everhech <edwin415@outlook.com>
Date:   Fri May 16 17:50:40 2025 -0500

    Fix: Rabbit connection between microservices

[33mcommit 985dc56f424d182571ed06cd3614599cf20f9934[m
Author: Everhech <edwin415@outlook.com>
Date:   Fri May 16 16:30:06 2025 -0500

    Fix: fixed error with database
    
    Added:
    - PHPMyAdmin to show all the database of the microservices with docker.

[33mcommit 1aa0a8c08a0c063f22dbc28ecbe0dc6a39ac7d20[m
Author: Elkin2814 <elkingmorillo@gmail.com>
Date:   Thu May 15 19:50:03 2025 -0500

    More changes

[33mcommit c68420119bef8680de2411ee32a551e1bdad2758[m
Author: Elkin2814 <elkingmorillo@gmail.com>
Date:   Thu May 15 19:47:57 2025 -0500

    Primary Stats

[33mcommit 44f8280b16d3b9cca9a965a30cfc59151bffffe4[m
Author: Edwin Orlando <edwin415@outlook.com>
Date:   Thu May 15 13:32:12 2025 -0500

    change: rabbit exchange trusted
    
    added a new configuration for rabbit to trust about the classess

[33mcommit 0f573468b85d1bc7904fca03712649bf8a646b78[m
Author: Everhech <edwin415@outlook.com>
Date:   Wed May 14 20:39:27 2025 -0500

    Change: new components and refactory
    
    Helper_service now its name is Common_utilities_service

[33mcommit 5e66371386ce62668b767234171b93662660d2c7[m
Author: Edwin Orlando <edwin415@outlook.com>
Date:   Tue May 13 20:40:33 2025 -0500

    feature: New implementations of rabbit

[33mcommit 075fe14dc37689cbd7a691c4069f675d1a56811f[m
Author: Edwin Orlando <edwin415@outlook.com>
Date:   Tue May 13 08:32:14 2025 -0500

    feature: new implementation of rabbit

[33mcommit 3eba9ab1208042fb3284afa14ea4f39850e3bf53[m[33m ([m[1;32mdev-WTF[m[33m)[m
Author: Joan Sebastian Tuquerrez Gomez <jtuquerrez@unicauca.edu.co>
Date:   Mon May 12 22:45:21 2025 -0500

    add keycloak to back

[33mcommit 190af663896f69f955f5c210e5795c93ee7f5734[m
Author: Everhech <edwin415@outlook.com>
Date:   Sun May 11 19:48:49 2025 -0500

    Fix: fixed connection between microservices
    
    Fixed rabbit connection.

[33mcommit ac64f3adac25bbb9333de0a5c9c9a270169d14b6[m
Merge: 27aa134 f3658dc
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri May 9 10:15:49 2025 -0500

    Merge pull request #36 from Viperexz/revert-35-lm-modulo-estadisticas
    
    Revert "Estructura base de componente de estadisticas"

[33mcommit f3658dc540332340d592c0682f68b0cfd3dc0b16[m[33m ([m[1;31morigin/revert-35-lm-modulo-estadisticas[m[33m)[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri May 9 10:15:39 2025 -0500

    Revert "Estructura base de componente de estadisticas"

[33mcommit 27aa1349ea413be6948910a85a36873ab27a163c[m
Merge: 24caf43 9a79fa7
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri May 9 09:37:06 2025 -0500

    Merge pull request #35 from Viperexz/lm-modulo-estadisticas
    
    Estructura base de componente de estadisticas

[33mcommit 9a79fa705fa4fcbdae942febe7b6612dddf156b7[m
Author: Jonas2k03 <99449599+Jonas2k03@users.noreply.github.com>
Date:   Thu May 8 23:07:13 2025 -0500

    Estructura base de componente de estadisticas

[33mcommit 24caf438b307cf4fe40ddbaee9dad1b7f06df040[m
Author: Everhech <edwin415@outlook.com>
Date:   Thu May 8 22:10:44 2025 -0500

    Add: Adding some methods to connect
    
    Connection between microservices:
    - It use RabbitMQ.
    
    There are some problems to send a message with a entity, so we need to watch it.

[33mcommit 6b948e6190f0444787fed5050c2d36c378cdff7e[m
Author: Everhech <edwin415@outlook.com>
Date:   Wed May 7 19:39:35 2025 -0500

    Feature: New component for rabbitmq
    
    Now, exists:
    - Service for RabbitMQ
    - Producer and Consumer of RabbitMQ
    
    Fixed:
    - Dependencies with the other microservice.
    
    Result:
    - Now you can send a message from a microservice to another microservice. For the moment is activated  helper_service and evaluation_service

[33mcommit b1746f3c2f9db482f74619e737730d8189f56254[m
Author: Everhech <edwin415@outlook.com>
Date:   Wed May 7 17:23:53 2025 -0500

    Fix: Fixed connection with mariadb and docker compose configuration

[33mcommit ffa371cb4e94756d1640e57fbfb7624decd6b2ef[m
Merge: 9aafad8 1b9400e
Author: Everhech <edwin415@outlook.com>
Date:   Wed May 7 15:35:10 2025 -0500

    Merge branch 'dev-WTF' of https://github.com/Viperexz/Proyecto1 into dev-WTF

[33mcommit 9aafad851626c6f20257cbed84196c6c09c42209[m
Author: Everhech <edwin415@outlook.com>
Date:   Wed May 7 15:29:32 2025 -0500

    Errors: never mind

[33mcommit 1b9400e03e1c4210e292b16dc3d7b4d95856f5fc[m
Author: Edwin Orlando <edwin415@outlook.com>
Date:   Wed May 7 14:52:29 2025 -0500

    Fix: fixed errors with evaluation and helper class
    
    There are some problemns with docker. It can't start the services:
    - Helper
    - Evaluation
    - ModuleRubricas

[33mcommit e4857534b920e0bb5a6868f4e56e0e13b0751577[m
Author: Everhech <edwin415@outlook.com>
Date:   Sun May 4 20:54:52 2025 -0500

    feature: new component of rabbit

[33mcommit b323b380076b487ebbfcaf67c539aad98342c219[m
Merge: e57d6bc 105e34c
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Tue Apr 29 08:01:52 2025 -0500

    Merge pull request #34 from Viperexz/Correcciones-lm-h3
    
    Correcciones lm h3 - FINAL Sprint 1

[33mcommit 105e34c9ff78442609cc530886af7034652b1684[m
Merge: 95a26f2 e57d6bc
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Tue Apr 29 08:01:20 2025 -0500

    Merge remote-tracking branch 'origin/dev' into Correcciones-lm-h3

[33mcommit e57d6bc07fd554990386e92558b1077c172046f6[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Tue Apr 8 08:03:19 2025 -0500

    Correcciones

[33mcommit 95a26f29d6ae31e36dae96644b834cb1f0f58a09[m
Author: Felipe <fpinos@unicauca.edu.co>
Date:   Fri Apr 25 16:46:40 2025 -0500

    única url en servicios

[33mcommit d997a09a5e754ec75f2d4daafd79ebaf6a54d38e[m
Author: Felipe <fpinos@unicauca.edu.co>
Date:   Fri Apr 25 15:58:28 2025 -0500

    responsive interface

[33mcommit 624f6e80dc418fb98b2131cf1f6522920164e1e5[m
Author: Jonas2k03 <99449599+Jonas2k03@users.noreply.github.com>
Date:   Thu Apr 10 18:25:14 2025 -0500

    Cambios en evaluaciones pendientes por corregir

[33mcommit b8361569115ce6a7c41c7fa3dc497c379a64cf35[m
Author: Jonas2k03 <99449599+Jonas2k03@users.noreply.github.com>
Date:   Tue Apr 8 02:49:40 2025 -0500

    version final para despliegue

[33mcommit bb9df872e6fecc90d761c3de08f8b73ec3cf0ed2[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Mon Apr 7 23:57:30 2025 -0500

    ...

[33mcommit f0ef6a84a2f76b8c58e832d458de6c38daf413cb[m
Merge: 4ce040a 597d4ce
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Mon Apr 7 22:07:20 2025 -0500

    Merge pull request #32 from Viperexz/correccion-dev-WTF
    
    Coupling with backend ATN

[33mcommit 597d4ce1ee0ee0d38ff5dd5089db2c0019e24fa3[m
Merge: 8d48bca 4ce040a
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Mon Apr 7 22:07:13 2025 -0500

    Merge branch 'dev' into correccion-dev-WTF

[33mcommit 8d48bcadcad3c2b8ba43fb2d83351c8348d9ef04[m
Merge: a70b5ef a947a80
Author: Joan Sebastian Tuquerrez Gomez <jtuquerrez@unicauca.edu.co>
Date:   Mon Apr 7 21:50:43 2025 -0500

    Merge remote-tracking branch 'origin/correcion-dev-WTF-H6' into correccion-dev-WTF

[33mcommit a70b5ef7044243caf5b4460a6523f511513fa7c5[m
Author: Joan Sebastian Tuquerrez Gomez <jtuquerrez@unicauca.edu.co>
Date:   Mon Apr 7 21:45:16 2025 -0500

    Modify entities for coupling

[33mcommit 4ce040ab80bd57c3b7cd1624babf4c87f005b1f4[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Mon Apr 7 21:24:10 2025 -0500

    Ajustes pro despliegue:
    
    Se crean los dockerfile y docker-compose.yml.
    
    SE DEBE RECORDAR NO TOCARLOS

[33mcommit e72613fd29f5d9d09034dbad83933847ff0d3df9[m
Author: Jonas2k03 <99449599+Jonas2k03@users.noreply.github.com>
Date:   Mon Apr 7 18:04:06 2025 -0500

    Modificaciones de consumo total del microservicio de evaluación. Pendiente de verificar el funcionamiento completo con el back terminado

[33mcommit 0d84091ec3e28f55023eae9471e6f83572aff4ad[m
Merge: 6a4adce 768afde
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Mon Apr 7 10:30:52 2025 -0500

    Merge pull request #31 from Viperexz/correccion-dev-WTF
    
    Modify properties of gateway adding evaluation microservice

[33mcommit 768afdea774f5df8b40cedab636bc5f71cc136f3[m
Merge: e3775a8 6a4adce
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Mon Apr 7 10:30:40 2025 -0500

    Merge branch 'dev' into correccion-dev-WTF

[33mcommit a947a80ab513985434a99f7318e8db40485e2247[m
Author: danieltucanes21 <danieltucanes@unicauca.edu.co>
Date:   Sun Apr 6 23:34:41 2025 -0500

    cambios en consultas HE-6

[33mcommit 191eb6fe905c2b4d6a5b8d0c8d92e2d7dbd9efac[m
Author: Jonas2k03 <99449599+Jonas2k03@users.noreply.github.com>
Date:   Sun Apr 6 19:45:21 2025 -0500

    Cambios deshechos previos a la comunicacion entre micro servicios. A la espera de esa comunicacion para hacer la integracion total

[33mcommit e3775a8ad3ed0cd82a1af79461f5b41fe7e04806[m
Author: Everhech <edwin415@outlook.com>
Date:   Sun Apr 6 15:26:23 2025 -0500

    Changes: Rubric Entity with new features

[33mcommit 4e605e0e5dafecb9113016a7c9890d8039833f11[m
Author: Joan Sebastian Tuquerrez Gomez <jtuquerrez@unicauca.edu.co>
Date:   Fri Apr 4 13:42:20 2025 -0500

    Modify properties of gateway adding evaluation microservice

[33mcommit 6a4adcef063b287e226f5bf9f636771d50f0336e[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 10:44:18 2025 -0500

    Eliminando el login

[33mcommit 25150e4467d83b530dd5ea4899636040d4e3aebe[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 09:57:25 2025 -0500

    Ajustes en dependencias

[33mcommit 723dea70f540b44087081ba25eec4cc9458def08[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 09:50:16 2025 -0500

    Correcciones Front

[33mcommit f3f9d01cb44b4f5a8502e64dc6dbe95998f1b65d[m
Merge: 498c866 8b8b984
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 09:16:10 2025 -0500

    Merge pull request #30 from Viperexz/Correcciones-GrupoATN
    
    Se añade el patch y mejora de save-update

[33mcommit 8b8b98485ddc30e3b0f919b23a143d67a3e695f7[m
Author: Jeison Andrés Vallejo Gómez <jeisonvallejo@unicauca.edu.co>
Date:   Fri Apr 4 01:52:55 2025 -0500

    Se añade el patch y mejora de save-update
    
    Los métodos de save y update permiten crear criterios y rúbricas también

[33mcommit 498c866b5bbbed6aed591f7870c42a87278473e0[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 01:23:42 2025 -0500

    Update enviroment.sh

[33mcommit 4748c9950a7eef057554049160d63a24f3b52ad5[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 01:19:57 2025 -0500

    Update enviroment.sh

[33mcommit 48e6a271a0a9c23973454eda078ab82f3f7faaca[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 01:17:09 2025 -0500

    .

[33mcommit 9609ce71a8a680dfc36f7162960e7008706ea95b[m
Author: Jonas2k03 <99449599+Jonas2k03@users.noreply.github.com>
Date:   Fri Apr 4 01:11:03 2025 -0500

    Integracion temporal con backend (no funcional)

[33mcommit 1dbc09431224ee3734a0645c3ebb634f17a62bed[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 01:06:27 2025 -0500

    .

[33mcommit d24f9a3d943d35e466804bcae3d8657d7f7997ad[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 00:59:40 2025 -0500

    .x2

[33mcommit f40b9e89fb1e0d7ec4113bf79c25e2e4cc253bed[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 00:58:08 2025 -0500

    .

[33mcommit f07fe4e538407160d72767c70acebc3c582e0c5b[m
Merge: b720988 3d65746
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 00:45:59 2025 -0500

    Merge branch 'dev' of https://github.com/Viperexz/Proyecto1 into dev

[33mcommit b720988ab6c43df57ea37b28b783f1b8d4a4a7a7[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 00:45:57 2025 -0500

    .

[33mcommit 5fe0923cefab9b6ef582ce4a5bdabfcfe0975f25[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Fri Apr 4 00:33:02 2025 -0500

    Ajuste finales frontend

[33mcommit 3d65746e67a9f4259b272a28b0baf47757b5c55a[m
Merge: 8972bf6 8c7f8ed
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Thu Apr 3 23:17:13 2025 -0500

    Merge pull request #29 from Viperexz/correccion-dev-WTF
    
    Feature: New endpoint of rubrics and Solved Cors

[33mcommit 8c7f8ede50978ad6ebdc128f9c481c158f82f729[m
Author: Edwin Orlando <edwin415@outlook.com>
Date:   Thu Apr 3 22:58:17 2025 -0500

    Feature: New endpoint of rubrics

[33mcommit 8972bf6dea831c69b63d3b6c164125392dd30f20[m
Merge: 808fcf8 ad0b45e
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Thu Apr 3 22:41:25 2025 -0500

    Merge branch 'dev' of https://github.com/Viperexz/Proyecto1 into dev

[33mcommit 808fcf88c752d105aeba3503149fa75f21471990[m
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Thu Apr 3 22:41:09 2025 -0500

    Algunos cambios

[33mcommit ad0b45eb53d1627479f31a5b48bf27d134f44e38[m
Merge: f8cad3a a76483b
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Thu Apr 3 20:45:09 2025 -0500

    Merge pull request #28 from Viperexz/correccion-dev-WTF

[33mcommit 2fdc25765150bc5ad3964587c326758fc0d9d5ac[m
Author: Jonas2k03 <99449599+Jonas2k03@users.noreply.github.com>
Date:   Thu Apr 3 19:56:38 2025 -0500

    Integracion de buscadores progresivos y dinamicos en evaluacion, primeros pasos para la conexión con el back

[33mcommit a76483b63a8b47a5ec2149d5b5d949de5379778a[m
Author: Edwin Orlando <edwin415@outlook.com>
Date:   Thu Apr 3 19:17:22 2025 -0500

    Deleted: deleted some features of properties

[33mcommit afdb485aca8fa071dccb26df5bdb043296c6a52d[m
Author: Joan Sebastian Tuquerrez Gomez <jtuquerrez@unicauca.edu.co>
Date:   Thu Apr 3 17:44:59 2025 -0500

    Modify Cors Config, test, and add controller for student

[33mcommit a46539ef7919a4bc39e0890b603bed2d5fb2c636[m
Author: Joan Sebastian Tuquerrez Gomez <jtuquerrez@unicauca.edu.co>
Date:   Thu Apr 3 16:40:02 2025 -0500

    Add cors config

[33mcommit b59083cd63c604ac3b9a1eeea1257319f11a6fbb[m
Author: Edwin Orlando <edwin415@outlook.com>
Date:   Thu Apr 3 16:15:28 2025 -0500

    Feature: List of endpoints

[33mcommit f8cad3a308f5a48f1ac54cc9bfebf23c2701ceac[m
Merge: 00a15d9 c9c28d3
Author: Oscar Fernando Hoyos Carvajal <57969765+Viperexz@users.noreply.github.com>
Date:   Thu Apr 3 15:40:41 2025 -0500

    Merge pull request #27 from Viperexz/Correcciones-GrupoATNi
    
    Eliminación de clave compuesta
