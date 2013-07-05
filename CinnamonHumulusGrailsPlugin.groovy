class CinnamonHumulusGrailsPlugin {

    def version = "0.2.6.2"
    def grailsVersion = "2.1 > *"
    def dependsOn = [:]
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]
    def packaging = "binary"
    
    def title = "Cinnamon Humulus Plugin" 
    def author = "Ingo Wiarda"
    def authorEmail = "ingo_wiarda@dewarim.de"
    def description = '''\
SwitchableDataSource and Security related classes required by the Cinnamon CMS plugin and applications based thereon. 
'''

    def documentation = "http://cinnamon-cms.com"
    def license = "LGPL-2.1"

    def developers = [ 
        [name:'Ingo Wiarda', email: "ingo_wiarda@dewarim.de"]
    ]

    def issueManagement = [ system: "Github", url: "https://github.com/dewarim/cinnamon-humulus" ]
    def scm = [ url: "https://github.com/dewarim/cinnamon-humulus/" ]

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
