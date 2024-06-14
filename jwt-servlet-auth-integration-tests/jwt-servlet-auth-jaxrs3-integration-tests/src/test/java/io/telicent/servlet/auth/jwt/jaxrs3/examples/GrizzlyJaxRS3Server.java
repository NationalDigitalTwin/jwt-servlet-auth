/**
 * Copyright (C) Telicent Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.telicent.servlet.auth.jwt.jaxrs3.examples;

import io.telicent.servlet.auth.jwt.configuration.RuntimeConfigurationAdaptor;
import io.telicent.servlet.auth.jwt.testing.AbstractServer;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.WebappContext;

public class GrizzlyJaxRS3Server extends AbstractServer {
    private final HttpServer server;
    private final WebappContext webApp;
    private final int port;

    public GrizzlyJaxRS3Server(HttpServer server, WebappContext webApp, int port) {
        this.server = server;
        this.webApp = webApp;
        this.port = port;
    }

    @Override
    public void start() {
        try {
            this.server.start();
            this.webApp.deploy(this.server);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        try {
            this.server.shutdownNow();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBaseUrl() {
        return String.format("http://localhost:%d", this.port);
    }

    @Override
    public RuntimeConfigurationAdaptor getRuntimeConfiguration() {
        return new RuntimeConfigurationAdaptor() {
            @Override
            public String getParameter(String param) {
                return null;
            }

            @Override
            public void setAttribute(String attribute, Object value) {
                webApp.setAttribute(attribute, value);
            }

            @Override
            public Object getAttribute(String attribute) {
                return webApp.getAttribute(attribute);
            }
        };
    }
}
