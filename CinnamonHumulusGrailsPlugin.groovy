class CinnamonHumulusGrailsPlugin {

    def groupId = 'cinnamon'
    
    def version = "0.2.1"
    def grailsVersion = "2.1 > *"
    def dependsOn = [:]
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]
    def packaging = "binary"
    
    def title = "Cinnamon Humulus Plugin" // Headline display name of the plugin
    def author = "Ingo Wiarda"
    def authorEmail = "ingo.wiarda@horner-project.eu"
    def description = '''\
SwitchableDataSource and Security related classes required by the Cinnamon CMS plugin and applications based thereon. 
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/humulus"

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "LGPL-2.1"
    
    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.grails-plugins.codehaus.org/browse/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }

    def onShutdown = { event ->
    }
}
