package humulus

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Environment {

    static Logger log = LoggerFactory.getLogger(Environment.class)
    
    static environments = []

    static {
        // def config = getGrailsApplication().getConfig()
        // cannot get this to work. This class is called before grailsApplication is available, it seems.
        // and there are problems with static access to grailsApplication.
        // tried http://stackoverflow.com/questions/6976983/getting-grails-2-0-0m1-config-info-in-domain-object-and-static-scope
        // and http://burtbeckwith.com/blog/?p=993 / http://burtbeckwith.com/blog/?p=1017, giving up now.
        def configFile = new File("${System.env.CINNAMON_HOME_DIR}/database-config.groovy")
        if(! configFile.exists()){
            throw new RuntimeException("Cannot find ${configFile.absolutePath}.")
        }
        log.debug("Loaded database-config file.")
        def script = configFile.text
        def config = new ConfigSlurper().parse(script)

        assert config != null: "could not find environments.properties!"
        config.dbconnections.each() {
            def env = config.dbconnections."${it.key}"
            environments << [
                    id: env.id,
                    prefix: env.prefix,
                    driverClassName: env.driverClassName,
                    cinnamonServerUrl: env.cinnamonServerUrl,
                    jdbcType: env.jdbcType,
                    host: env.host,
                    port: env.port,
                    username: env.username,
                    password: env.password,
                    encryptPasswords: env.encryptPasswords,
                    maxActive: 30,
                    initialSize: 10,
                    maxPoolSize: 30,
                    dbName: env.dbName,
                    dbType: env.dbType,
                    dbUser: env.dbUser,
                    dbPassword: env.dbPassword,
                    persistenceUnit: env.persistenceUnit ?: 'cinnamon',
                    dbConnectionUrl: createDatabaseConnectionURL(
                            env.dbName, env.dbType, env.jdbcType, env.host, env.dbUser, env.dbPassword
                    )
            ]
        }

        //unique id check 
        environments.each { env ->
            assert environments.findAll {it.id == env.id}.size() == 1
        }
    }

    static List<Map> list() {
        return environments
    }

    public static String createDatabaseConnectionURL(String repository, String dbType, String protocol, String host, String user, String password){
        String connString;
        if (dbType.equals("mysql")) {
            connString="jdbc:"+ protocol+"://" + host +
                    "/" + repository +
                    "?user=" + user +
                    "&password=" + password;
        } else if(dbType.equals("mssql")) {
            connString="jdbc:"+protocol+"://" + host +
                    ";databaseName=" + repository +
                    ";user=" + user +
                    ";password=" + password;
        } else if(dbType.equals("mssql2000")) {
            connString="jdbc:jtds:"+protocol+"://" + host +
                    "/" + repository +
                    ";user=" + user +
                    ";password=" + password +
                    ";TDS=8.0";
        }
        else if(dbType.equals("postgresql")){
            connString = "jdbc:"+protocol+"://" + host +
                    "/" + repository +
                    "?user=" + user +
                    "&password=" + password;
            //"&ssl=true";
        }
        else{
            throw new RuntimeException("unknown db_type in config found!" +
                    " Currently, Cinnamon only connects to MySQL, PostgreSQL or MS-SQL dbs");
        }
        return connString;
    }
}
