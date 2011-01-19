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

import java.io.*;
import java.util.*;
import javax.servlet.http.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * {@link javax.servlet.Filter} that sets name value pairs in the HTTP response header.
 * <p>Example: Setting a Cache-Control header</p>
 * <pre>
 * &lt;filter&gt;
 *      &lt;description>filter to explicitly enable caching of static resources&lt;/description&gt;
 *      &lt;filter-name>HttpHeaderFilter&lt;/filter-name&gt;
 *      &lt;filter-class>com.googlecode.servletfilters.HTTPHeaderFilter&lt;/filter-class&gt;
 *      &lt;init-param&gt;
 *          &lt;!-- set max-age to one month --&gt;
 *          &lt;param-name>Cache-Control&lt;/param-name&gt;
 *          &lt;param-value>max-age=2419200, public&lt;/param-value&gt;
 *      &lt;/init-param&gt;
 * &lt;/filter&gt;
 * </pre>
 *
 * 
 * @author  Sebastian Prehn <sebastian.prehn@planetswebdesign.de>
 */
public class HTTPHeaderFilter implements Filter {

    /**
     * Filter configuration object.
     * If <code>null</code> the instance has not yet been configured.
     */
    private FilterConfig filterConfig = null;

    public HTTPHeaderFilter() {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        @SuppressWarnings("unchecked")
        final Enumeration<String> initParameterNames = this.filterConfig.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            final String name = initParameterNames.nextElement();
            final String value = this.filterConfig.getInitParameter(name);
            httpResponse.setHeader(name, value);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }

    @Override
    public void init(final FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
}
