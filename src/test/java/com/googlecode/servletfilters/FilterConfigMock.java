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

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;



/**
 *
 * @author Sebastian Prehn <sebastian.prehn@planetswebdesign.de>
 */
public class FilterConfigMock implements FilterConfig{

    private final Map<String, String> parameter;
    private final String filterName;

    public FilterConfigMock(Map<String, String> parameter, String filterName) {
        this.parameter = parameter;
        this.filterName = filterName;
    }


    public String getFilterName() {
        return this.filterName;
    }

    public ServletContext getServletContext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getInitParameter(String name) {
        return this.parameter.get(name);
    }

    public Enumeration getInitParameterNames() {
        final Iterator<String> iterator = this.parameter.keySet().iterator();
        return new Enumeration() {

            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            public Object nextElement() {
                return iterator.next();
            }
        };
    }

}
