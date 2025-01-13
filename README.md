# Brokerage Utils (Dome project)

### Description
Development project for data structures and utils used by the Brokerage services

### How to share the Brockerage Utils libs
These libs will be used in the **Brokerage services** (e.g.,`billing-engine`, `billing-proxy`, `billing-scheduler`and `invoicing-service` projects).
To share them, you can use the `nexus` repository for artifacts provided by **Engineering** (https://production.eng.it/nexus).
To use it, you can use the `settings.xml` file (in the root of the project) and edit it using your **LDAP credentials** in the `<username>` and the `<password>` tags.

> [!TIP]  
> To upload the **artifacts** in the **nexus repo**, you can use this command: `mvn -s full_path_to_nexus_settings clean install deploy`.

> [!NOTE]  
> You can use the `mvn clean install deploy` command if you put the **settings.xml** in the maven directory (`${user.home}/.m2/settings.xml`).


