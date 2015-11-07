# Changelog for Cinnamon-Humulus

Note: the last number is the build. Not all builds have attributable changes. 
For example, if a build is found broken before release, it will be fixed and
 the changes moved to the next build.

## 0.x

Cinnamon-Humulus has become obsolete. It's code was moved into cinnamon-db (or removed completely over time)

## 0.3

* Removed EnvironmentHolder, Environment, SwitchableDataSource, RepositoryLoginFilter
    We are currently moving to a single-database-per-installation mode and do not need them
    any longer.

## 0.1.2

* Made urlFilterRegex configurable. You can now specify a regex in your application-config to
  prevent certain files from being subjected to the login filter. This is useful for static images,
  which should be accessible without the user being asked for his login credentials.
  
    humulus{
        urlFilterRegex = '*-icon.gif$'    
    }