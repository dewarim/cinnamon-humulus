package humulus

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import org.codehaus.groovy.grails.commons.ApplicationAttributes
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Change database connection depending on selected repository. Otherwise,
 * the user object will not be found.
 */
class RepositoryLoginFilter extends UsernamePasswordAuthenticationFilter implements InitializingBean {
    
    def grailsApplication
    
    RepositoryLoginFilter() {
        this.postOnly = false
    }

    RepositoryLoginFilter(String defaultFilterProcessesUrl) {
        this.filterProcessesUrl = defaultFilterProcessesUrl
        this.postOnly = false
    }

    Logger log = LoggerFactory.getLogger(this.class)
//    static final String DEFAULT_FILTER_REGEX = '(?:plugins/[-_.a-zA-Z0-9]+/|assets/)?(?:images|css|js)?/.*(?:css|js|png|jpe?g|gif)'
    static final String DEFAULT_FILTER_REGEX = 'assets/.*'
    
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return null
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        def params = request.parameterMap
        def httpServletRequest = (HttpServletRequest) request
        def uri = httpServletRequest.getRequestURI()
        def contextPath = httpServletRequest.getContextPath()
        
        def filterRegex = grailsApplication.config.humulus.urlFilterRegex ?: DEFAULT_FILTER_REGEX
        log.debug("filterRegex: $contextPath/$filterRegex")
        if (uri =~ /${contextPath}\/${filterRegex}/) {
            log.debug("do not filter $uri")
            chain.doFilter(request, response)
            return
        }
        else {
            log.debug("continue with: $uri")
        }

        def session = httpServletRequest.session
        log.warn("EnvironmentHolder: " + EnvironmentHolder.getEnvironment()?.id)

        if (params.environment) {
//            log.debug("params.environment: ${params.environment}")
            def id = Integer.parseInt(params.environment[0])
            def env = Environment.list().find {it.id == id}
            if (!env) {
                log.debug("Could not find environment '${params.environment}'")
                throw new RuntimeException("Could not find environment '${params.environment}'")
            }

            //test connection
            EnvironmentHolder.setEnvironment(env)
            def ds = getDataSourceForEnv(null)
            establishConnection(ds, session, env)                        
        }
        else {
            def currentEnv
            if ( httpServletRequest.getHeader('ticket')){
                def repository = httpServletRequest.getHeader('ticket')?.split('@')[1]
                currentEnv = Environment.list().find{it.dbName == repository}
                log.debug("Found ticket in request header for repository $repository")
            }
            else{
                currentEnv = session.getAttribute('environment')
            }
            if (currentEnv) {
                /*
                 * we got a session, so we initialize the ThreadLocal EnvironmentHolder with
                 * our per-session-environment.
                 */

                /*
                 currently, we do not close any connections, since
                  ds.getConnection will return *new* connections or pooled connections,
                  which are not very useful to close.
                  */

                EnvironmentHolder.setEnvironment(currentEnv)
                def ds = getDataSourceForEnv(null)
                def con = ds.getConnection() // necessary to make sure we _can_ connect.
                log.debug("connected to: ${con.dump()}")                

            }
            else {
                /*
                 * We have not got a session, which means that our EnvironmentHolder is most
                 * likely invalid.
                 * We will try to establish a database connection anyway, since the login logo
                 * (and perhaps other resources) may be loaded without a valid user account / session.
                 */
                log.warn("EnvironmentHolder without session / param: " + EnvironmentHolder.getEnvironment()?.id)
                def ds = getDataSourceForEnv(null)
                log.debug("will use first repository in list.")
                // TODO: try to determine environment from request.host attribute, if possible.
                // for example: demo.cinnamon4u.de would look for repository demo.
                def env = Environment.list()[0]
                establishConnection(ds, session, env)
            }
        }
        chain.doFilter(request, response)
    }

    private void establishConnection(ds, session, env) {
        log.debug("datasource: ${ds.dump()}")

        try {
            def con = ds.getConnection()
            log.debug("connected to $con")
            session.setAttribute('environment', env)
            session.setAttribute('repositoryName', env.dbName)            
            log.debug('Environment change complete.')
        } catch (e) {
            log.debug("error: ", e)
            throw new RuntimeException("Unable to connect to database", e)
        }
    }

    private def getDataSourceForEnv(env) {
        def servletContext = ServletContextHolder.servletContext
        def ctx = servletContext
                .getAttribute(ApplicationAttributes.APPLICATION_CONTEXT)
        return ctx.dataSource
    }


}
