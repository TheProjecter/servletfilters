common servlet filters for java web applications

# Introduction #

The servletfilters projects provides a collection of common servlet filters for java web applications based on the [Servlet 2.5 specification](http://jcp.org/aboutJava/communityprocess/mrel/jsr154/index.html).

# Features #
  * Lightweight and easy to use
  * No transitive dependencies
  * FREE

# Details #

The following filters are provided:
  * **[HTTPExpiresHeaderFilter](HTTPExpiresHeaderFilter.md)** to set the expires header on a response
  * **[HTTPHeaderFilter](HTTPHeaderFilter.md)** to set static http headers on a response

# Installation #

If you would like to use servletfilters in your maven build, include the following in your pom.xml:
```
<dependencies>
  <dependency>
      <groupId>com.googlecode.servletfilters</groupId>
      <artifactId>servletfilters</artifactId>
      <version>1.0.1</version>
  </dependency>
<dependencies>
```