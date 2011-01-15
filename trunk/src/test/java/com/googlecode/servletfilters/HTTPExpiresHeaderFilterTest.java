/*
 *  Copyright 2011 Sebastian Prehn <sebastian.prehn@planetswebdesign.de>.
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

import java.util.Calendar;
import org.apache.shale.test.mock.MockHttpServletResponse;
import java.io.IOException;
import org.junit.After;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.ServletException;
import org.apache.shale.test.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for HTTPExpiresHeaderFilter.
 * @author Sebastian Prehn <sebastian.prehn@planetswebdesign.de>
 */
public class HTTPExpiresHeaderFilterTest {
    private HTTPExpiresHeaderFilter instance;

    public HTTPExpiresHeaderFilterTest() {
    }

    @Before
    public void setUp() throws Exception {
        this.instance = new HTTPExpiresHeaderFilter();
    }

    @After
    public void tearDown() throws Exception {
        this.instance.destroy();
        this.instance = null;
    }

    @Test
    public void testTTLRequired() {
        Map<String, String> parameter = new HashMap<String, String>();

        try {
            this.instance.init(new FilterConfigMock(parameter, "HttpExpiresHeaderFilter"));
            fail("ttl is required");
        } catch (ServletException ex) {
            // expected
        }
    }

    @Test
    public void testTTLNotNull() {
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put("ttl", null);
        try {
            this.instance.init(new FilterConfigMock(parameter, "HttpExpiresHeaderFilter"));
            fail("ttl must not be null");
        } catch (ServletException ex) {
            // expected
        }
    }

    @Test
    public void testTTLInvalid() {
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put("ttl", "abc");
        try {
            this.instance.init(new FilterConfigMock(parameter, "HttpExpiresHeaderFilter"));
            fail("ttl must not be null");
        } catch (ServletException ex) {
            // expected
        }
    }

    @Test
    public void testTTLValid() throws ServletException, IOException {
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put("ttl", "10"); // 10 seconds
        this.instance.init(new FilterConfigMock(parameter, "HttpExpiresHeaderFilter"));
        FilterChainMock filterChain = new FilterChainMock();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        this.instance.doFilter(request, response ,filterChain);
        assertNotNull(response.getHeader("Expires"));
        assertTrue(filterChain.hasRun());
    }
    
    @Test
    public void testGetRFC1123Date() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c.set(1994, 10, 6, 8, 49, 37); // month is 0 based => nov -> 10
        assertEquals("Sun, 06 Nov 1994 08:49:37 GMT",HTTPExpiresHeaderFilter.getRFC1123Date(c.getTime()));
    }



}