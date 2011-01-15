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
 */

package com.googlecode.servletfilters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import org.apache.shale.test.mock.MockHttpServletRequest;
import org.apache.shale.test.mock.MockHttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sebastian Prehn <sebastian.prehn@planetswebdesign.de>
 */
public class HTTPHeaderFilterTest {
    private HTTPHeaderFilter instance;

    public HTTPHeaderFilterTest() {
    }

   @Before
    public void setUp() throws Exception {
        this.instance = new HTTPHeaderFilter();
    }

    @After
    public void tearDown() throws Exception {
        this.instance.destroy();
        this.instance = null;
    }

    @Test
    public void testSomeMethod() {
    }


     @Test
    public void testDoFilter() throws ServletException, IOException {
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put("Cache-Control", "max-age=2419200, public");
        this.instance.init(new FilterConfigMock(parameter, "HttpHeaderFilter"));
        FilterChainMock filterChain = new FilterChainMock();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        this.instance.doFilter(request, response ,filterChain);

        assertEquals("max-age=2419200, public",response.getHeader("Cache-Control"));
        assertTrue(filterChain.hasRun());
    }
}