Camel Router Project for Spring-DM (OSGi)
=========================================

To build this project use

    mvn install

You can run this project using he following Maven goal:

    mvn camel:run

To deploy the project in OSGi. For example using Apache ServiceMix
or Apache Karaf. You will run the following command from its shell:

    osgi:install -s mvn:org.prof-itgroup/it-jdbc-context/0.0.1-SNAPSHOT

For more help see the Apache Camel documentation

    http://camel.apache.org/

