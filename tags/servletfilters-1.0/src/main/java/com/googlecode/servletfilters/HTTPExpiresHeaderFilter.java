/*
 *  Copyright 2009 Sebastian Prehn <sebastian.prehn@planetswebdesign.de>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 * 
 *  Versioning:
 *  $LastChangedDate$
 *  $LastChangedRevision$
 */

package com.googlecode.servletfilters;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link javax.servlet.Filter} that sets the HTTP <b>Expires</b> header to a date in the future.
 * The expiration date is calulated as: current time plus init-parameter ttl.
 *
 * <p>Example: Setting an Expires header</p>
 * <pre>
 * &lt;filter&gt;
 *      &lt;description>filter to explicitly set the expires header of static resources&lt;/description&gt;
 *      &lt;filter-name>HttpExpiresHeaderFilter&lt;/filter-name&gt;
 *      &lt;filter-class>com.googlecode.servletfilters.HttpExpiresHeaderFilter&lt;/filter-class&gt;
 *      &lt;init-param&gt;
 *          &lt;!-- one month in seconds --&gt;
 *          &lt;param-name>ttl&lt;/param-name&gt;
 *          &lt;param-value>2419200&lt;/param-value&gt;
 *      &lt;/init-param&gt;
 * &lt;/filter&gt;
 * </pre>
 * <p>Init Parameters:</p>
 * <ul>
 * <li><b>ttl</b> - time to live, must be given in seconds (required)</li>
 * </ul>
 * @author  Sebastian Prehn <sebastian.prehn@planetswebdesign.de>
 */
public class HTTPExpiresHeaderFilter implements Filter {

    /**
     * US locale - all HTTP dates are in english.
     */
    private static final Locale LOCALE_US = Locale.US;
    /**
     * GMT timezone - all HTTP dates are on GMT.
     */
    private static final TimeZone GMT_ZONE = TimeZone.getTimeZone("GMT");
    /**
     * Format for RFC 1123 date string.
     * Example: "Sun, 06 Nov 1994 08:49:37 GMT"
     */
    private static final String RFC1123_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    /**
     * RFC 1123 date formatter.
     */
    private static final SimpleDateFormat rfc1123Format = new SimpleDateFormat(RFC1123_PATTERN, LOCALE_US);

    static {
        rfc1123Format.setTimeZone(GMT_ZONE);
    }
    /**
     * Time to live in milliseconds.
     */
    private long ttl = 0;

    /**
     * Returns the rfc1123 formated date as string.
     * Synchronized since SimpleDateFormat is not thread-safe!
     * @param date the date to format
     * @return the formated string
     */
    static synchronized String getRFC1123Date(final Date date) {
        return rfc1123Format.format(date);
    }

    /**
     * Init method for this filter.
     *
     * @param filterConfig the filter configuration.
     */
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        String ttlSeconds = filterConfig.getInitParameter("ttl");

        if(ttlSeconds == null) {
            throw new ServletException("parameter ttl is required");
        }

        try {
            this.ttl = Long.parseLong(ttlSeconds) * 1000;
        } catch (NumberFormatException ex) {
            throw new ServletException("ttl parameter given in invalid format", ex);
        }
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Date expireDate = new Date((new Date().getTime()) + ttl);
        httpResponse.setHeader("Expires", getRFC1123Date(expireDate));
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // nothing to do
    }
}
