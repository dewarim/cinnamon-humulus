grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.repos.default = "myRepo"
grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
//        mavenRepo name:'myRepo'
        grailsCentral()
        mavenCentral()
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        runtime 'org.mindrot:jbcrypt:0.3m'
    }

    plugins {
        compile(':spring-security-core:2.0-RC5')
        compile ":rest-client-builder:2.1.1"
        build(":tomcat:8.0.15"){
            export=false
        }
        build(":release:3.1.1"){
            export=false
        }
        runtime(":hibernate:3.6.10.16")
    }
}
