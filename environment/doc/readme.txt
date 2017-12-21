1. Запустить ServiceMix
2. В папку deploy из дистрибутива скопировать файл it-distribution-0.0.1-SNAPSHOT\features\features.xml
   В этом файле прописаны системные runtime зависимости которые необходимы для работы приложения. Убедиться что зависимости встали без ошибок. Вся информация пишется лог \apache-servicemix-7.0.1\data\log\servicemix.log 
3. В папку etc из дистрибутива it-distribution-0.0.1-SNAPSHOT\etc\*  скопировать файлы настроек модулей
   Необходимо настроить параметры подключения к БД систем:
   3.1 it-distribution-0.0.1-SNAPSHOT\etc\it.claims.datasource.config.cfg 
   3.2 it-distribution-0.0.1-SNAPSHOT\etc\it.customs.datasource.config.cfg 
   3.3 it-distribution-0.0.1-SNAPSHOT\etc\it.postgres.datasource.config.cfg 
4. В папку deploy скопировать драйверы БД. Убедиться что драйверы установились успешно. В консоли ServiceMix набрать команду list. Драйверы должны быть в состоянии active
   4.1 it-distribution-0.0.1-SNAPSHOT\drivers\ojdbc14-10.2.0.5.jar 
   4.2 it-distribution-0.0.1-SNAPSHOT\drivers\postgresql-42.1.1.jre7.jar 
   4.3 it-distribution-0.0.1-SNAPSHOT\drivers\sqljdbc41.jar
5. В папку deploy скопировать модули поднимающие пулы соединений(DataSource) для каждой из систем, и фабрику соединений JMS на базе ActiveMq. В логе ServiceMix каждый модуль должен написать SUCCESS
   5.1 it-distribution-0.0.1-SNAPSHOT\app\smx\it-jdbc-claims-0.0.1-SNAPSHOT.jar 
   5.2 it-distribution-0.0.1-SNAPSHOT\app\smx\it-jdbc-context-0.0.1-SNAPSHOT.jar 
   5.3 it-distribution-0.0.1-SNAPSHOT\app\smx\it-jdbc-customs-0.0.1-SNAPSHOT.jar 
   5.4 it-distribution-0.0.1-SNAPSHOT\app\smx\it-jms-context-0.0.1-SNAPSHOT.jar 
6. Скопировать модули разработанных процессов в папку deploy:
   6.1 it-distribution-0.0.1-SNAPSHOT\app\j2ee\it-cbr-claims-0.0.1-SNAPSHOT.war
   6.2 it-distribution-0.0.1-SNAPSHOT\app\j2ee\it-cbr-customs-0.0.1-SNAPSHOT.war 
   6.3 it-distribution-0.0.1-SNAPSHOT\app\j2ee\it-cbr-daily-0.0.1-SNAPSHOT.war 
   6.4 it-distribution-0.0.1-SNAPSHOT\app\j2ee\it-cbr-fourpl-0.0.1-SNAPSHOT.war
   6.5 it-distribution-0.0.1-SNAPSHOT\app\j2ee\it-damage-fourpl-0.0.1-SNAPSHOT.war
   6.6 it-distribution-0.0.1-SNAPSHOT\app\j2ee\it-directory-customs-0.0.1-SNAPSHOT.war
   6.7 it-distribution-0.0.1-SNAPSHOT\app\j2ee\it-directory-fourpl-0.0.1-SNAPSHOT.war
   6.8 it-distribution-0.0.1-SNAPSHOT\app\j2ee\it-directory-rest-0.0.1-SNAPSHOT.war
   6.9 it-distribution-0.0.1-SNAPSHOT\app\j2ee\it-order-claims-0.0.1-SNAPSHOT.war
   6.10 it-distribution-0.0.1-SNAPSHOT\app\j2ee\it-rest-0.0.1-SNAPSHOT.war 

