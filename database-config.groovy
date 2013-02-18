// The Humulus plugin searches for this file in:
// $CINNAMON_HOME_DIR/database-config.groovy
// so you have to set the CINNAMON_HOME_DIR environment variable before starting Cinnamon 3
dbconnections {

    demo {
        id = 1
        // the prefix name is displayed on the login page
        // at http://localhost:8080/cinnamon/login/auth
        prefix = 'demo'
        driverClassName = 'org.postgresql.Driver'
        jdbcType = 'postgresql'
        host = 'localhost'
        cinnamonServerUrl = 'http://localhost:8080/cinnamon/cinnamon'
        port = 5432
        dbName = 'demo'
        dbname = 'demo' // legacy setting
        username = 'cinnamon'
        password = 'cinnamon'
        dbUser = 'cinnamon'
        dbPassword = 'cinnamon'
        dbType = 'postgresql'
        encryptPasswords = 'true'
    }

}

