# Introduction #

Filter that sets the HTTP **Expires** header to a date in the future.
The expiration date is calulated as: current time plus init-parameter ttl.

# Configuration #
Init Parameters:

  * **ttl** - time to live, must be given in seconds (required)

# Example #
Setting an Expires header
```
<filter>
      <description>filter to explicitly set the expires header of static resources</description>
      <filter-name>HTTPExpiresHeaderFilter</filter-name>
      <filter-class>com.googlecode.servletfilters.HTTPExpiresHeaderFilter</filter-class>
      <init-param>
          <!-- one month in seconds -->
          <param-name>ttl</param-name>
          <param-value>2419200</param-value>
      </init-param>
</filter>
```