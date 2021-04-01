/*
 * Copyright (c) 2002-2021 Manorrock.com. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   1. Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *   2. Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *   3. Neither the name of the copyright holder nor the names of its
 *      contributors may be used to endorse or promote products derived from
 *      this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package cloud.piranha.http.webapp.tests;

import cloud.piranha.http.api.HttpServer;
import cloud.piranha.http.api.HttpServerProcessor;
import cloud.piranha.http.webapp.HttpWebApplicationServer;
import cloud.piranha.webapp.impl.DefaultWebApplication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * The JUnit tests for the HttpWebApplicationRequest class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public abstract class HttpWebApplicationRequestTest {
    
    /**
     * Stores our random.
     */
    private Random random = new Random();
    
    /**
     * Get random port.
     * 
     * @return a random port.
     */
    public int getRandomPort() {
        return 4000 + random.nextInt(1000);
    }
    
    /**
     * Create the server.
     * 
     * @param port the port.
     * @param processor the processor.
     * @return the HTTP server.
     */
    public abstract HttpServer createServer(int port, HttpServerProcessor processor);
    
    /**
     * Test getCharacterEncoding method.
     */
    @Test
    public void testGetCharacterEncoding() {
        HttpWebApplicationServer server = new HttpWebApplicationServer();
        int port = getRandomPort();
        HttpServer httpServer = createServer(port, server);
        DefaultWebApplication application = new DefaultWebApplication();
        application.setContextPath("/test");
        application.addServlet("test", new TestGetCharacterEncodingServlet());
        application.addServletMapping("test", "/TestServlet");
        server.addWebApplication(application);
        server.initialize();
        server.start();
        httpServer.start();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder(new URI("http://localhost:" + port + "/test/TestServlet"))
                    .header("Content-Type", "text/html;charset=UTF-8")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("Character-Encoding: UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        httpServer.stop();
        server.stop();
    }
    
    /**
     * Test getCharacterEncoding method.
     */
    @Test
    public void testGetCharacterEncoding2() {
        HttpWebApplicationServer server = new HttpWebApplicationServer();
        int port = getRandomPort();
        HttpServer httpServer = createServer(port, server);
        DefaultWebApplication application = new DefaultWebApplication();
        application.setContextPath("/test");
        application.addServlet("test", new TestGetCharacterEncodingServlet());
        application.addServletMapping("test", "/TestServlet");
        server.addWebApplication(application);
        server.initialize();
        server.start();
        httpServer.start();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder(new URI("http://localhost:" + port + "/test/TestServlet"))
                    .header("Content-Type", "text/html;charset=ISO-8859-1")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("Character-Encoding: ISO-8859-1"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        httpServer.stop();
        server.stop();
    }
    
    /**
     * Test getContentLength method.
     */
    @Test
    public void testGetContentLength() {
        HttpWebApplicationServer server = new HttpWebApplicationServer();
        int port = getRandomPort();
        HttpServer httpServer = createServer(port, server);
        DefaultWebApplication application = new DefaultWebApplication();
        application.setContextPath("/test");
        application.addServlet("test", new TestGetContentLengthServlet());
        application.addServletMapping("test", "/TestServlet");
        server.addWebApplication(application);
        server.initialize();
        server.start();
        httpServer.start();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder(new URI("http://localhost:" + port + "/test/TestServlet"))
                    .header("Content-Type", "text/html")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("Content-Length: 0"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        httpServer.stop();
        server.stop();
    }
    
    /**
     * Test getContentType method.
     */
    @Test
    public void testGetContentType() {
        HttpWebApplicationServer server = new HttpWebApplicationServer();
        int port = getRandomPort();
        HttpServer httpServer = createServer(port, server);
        DefaultWebApplication application = new DefaultWebApplication();
        application.setContextPath("/test");
        application.addServlet("test", new TestGetContentTypeServlet());
        application.addServletMapping("test", "/TestServlet");
        server.addWebApplication(application);
        server.initialize();
        server.start();
        httpServer.start();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest
                    .newBuilder(new URI("http://localhost:" + port + "/test/TestServlet"))
                    .header("Content-Type", "text/html")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("Content-Type: text/html"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        httpServer.stop();
        server.stop();
    }
    
    /**
     * Test getContextPath method.
     */
    @Test
    public void testGetContextPath() {
        HttpWebApplicationServer server = new HttpWebApplicationServer();
        int port = getRandomPort();
        HttpServer httpServer = createServer(port, server);
        DefaultWebApplication application = new DefaultWebApplication();
        application.setContextPath("/test");
        application.addServlet("test", new TestGetContextPathServlet());
        application.addServletMapping("test", "/TestServlet");
        server.addWebApplication(application);
        server.initialize();
        server.start();
        httpServer.start();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:" + port + "/test/TestServlet")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("Context Path: /test"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        httpServer.stop();
        server.stop();
    }
    
    /**
     * Test getParameter method.
     */
    @Test
    public void testGetParameter() {
        HttpWebApplicationServer server = new HttpWebApplicationServer();
        int port = getRandomPort();
        HttpServer httpServer = createServer(port, server);
        DefaultWebApplication application = new DefaultWebApplication();
        application.setContextPath("/test");
        application.addServlet("test", new TestGetParameterServlet());
        application.addServletMapping("test", "/TestServlet");
        server.addWebApplication(application);
        server.initialize();
        server.start();
        httpServer.start();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:" + port + "/test/TestServlet?test=mytest")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("Parameter name: test"));
            assertTrue(response.body().contains("Parameter value: mytest"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        httpServer.stop();
        server.stop();
    }
    
    /**
     * Test getQueryString method.
     */
    @Test
    public void testGetQueryString() {
        HttpWebApplicationServer server = new HttpWebApplicationServer();
        int port = getRandomPort();
        HttpServer httpServer = createServer(port, server);
        DefaultWebApplication application = new DefaultWebApplication();
        application.setContextPath("/test");
        application.addServlet("test", new TestGetQueryStringServlet());
        application.addServletMapping("test", "/TestServlet");
        server.addWebApplication(application);
        server.initialize();
        server.start();
        httpServer.start();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:" + port + "/test/TestServlet?test=getQueryString")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("Query String: test=getQueryString"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        httpServer.stop();
        server.stop();
    }
    
    /**
     * Test getQueryString method.
     */
    @Test
    public void testGetQueryString2() {
        HttpWebApplicationServer server = new HttpWebApplicationServer();
        int port = getRandomPort();
        HttpServer httpServer = createServer(port, server);
        DefaultWebApplication application = new DefaultWebApplication();
        application.setContextPath("/test");
        application.addServlet("test", new TestGetQueryStringServlet());
        application.addServletMapping("test", "/TestServlet");
        server.addWebApplication(application);
        server.initialize();
        server.start();
        httpServer.start();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:" + port + "/test/TestServlet")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("Query String: null"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        httpServer.stop();
        server.stop();
    }

    /**
     * Test getRequestURI method.
     */
    @Test
    public void testGetRequestURI() {
        HttpWebApplicationServer server = new HttpWebApplicationServer();
        int port = getRandomPort();
        HttpServer httpServer = createServer(port, server);
        DefaultWebApplication application = new DefaultWebApplication();
        application.setContextPath("/test");
        application.addServlet("test", new TestGetRequestURIServlet());
        application.addServletMapping("test", "/TestServlet");
        server.addWebApplication(application);
        server.initialize();
        server.start();
        httpServer.start();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:" + port + "/test/TestServlet")).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("Request URI: /test/TestServlet"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        httpServer.stop();
        server.stop();
    }
}