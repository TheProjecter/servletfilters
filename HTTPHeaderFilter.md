# Introduction #

Filter that sets name value pairs in the HTTP response header.

# Configuration #

All name value pairs that shall be set in the response header have to be provided as init parameters of the filter configuration.

# Example #

Setting a Cache-Control header:
```
  <filter>
      <description>filter to explicitly enable caching of static resources</description>
       <filter-name>HTTPHeaderFilter</filter-name>
       <filter-class>com.googlecode.servletfilters.HTTPHeaderFilter</filter-class>
       <init-param>
           <!-- set max-age to one month -->
           <param-name>Cache-Control</param-name>
           <param-value>max-age=2419200, public</param-value>
       </init-param>
  </filter>
```