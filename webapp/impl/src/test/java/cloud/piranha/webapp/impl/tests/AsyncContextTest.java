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
package cloud.piranha.webapp.impl.tests;

import cloud.piranha.webapp.impl.DefaultWebApplication;
import cloud.piranha.webapp.impl.DefaultWebApplicationRequest;
import cloud.piranha.webapp.impl.DefaultWebApplicationResponse;
import jakarta.servlet.AsyncContext;
import java.io.ByteArrayOutputStream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * The JUnit tests for the AsyncContext API.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
class AsyncContextTest {

    /**
     * Test dispatch method.
     *
     * @throws Exception when a serious error occurs.
     */
    @Test
    void testDispatch() throws Exception {
        DefaultWebApplication webApp = new DefaultWebApplication();
        DefaultWebApplicationRequest request = new DefaultWebApplicationRequest();
        request.setAsyncSupported(true);
        request.setWebApplication(webApp);
        DefaultWebApplicationResponse response = new DefaultWebApplicationResponse();
        response.setResponseCloser(() -> {});
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        response.setUnderlyingOutputStream(byteOutput);
        response.setWebApplication(webApp);
        AsyncContext context = request.startAsync(request, response);
        context.dispatch();
        while(!response.isCommitted()) {
            Thread.sleep(10);
        }
        assertTrue(byteOutput.toString().contains("HTTP/1.1 404"));
    }

    /**
     * Test dispatch method.
     * 
     * @throws Exception when a serious error occurs.
     */
    @Test
    void testDispatch2() throws Exception {
        DefaultWebApplication webApp = new DefaultWebApplication();
        DefaultWebApplicationRequest request = new DefaultWebApplicationRequest();
        request.setAsyncSupported(true);
        request.setWebApplication(webApp);
        DefaultWebApplicationResponse response = new DefaultWebApplicationResponse();
        response.setResponseCloser(() -> {});
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        response.setUnderlyingOutputStream(byteOutput);
        response.setWebApplication(webApp);
        AsyncContext context = request.startAsync(request, response);
        context.dispatch("/mypath");
        while(!response.isCommitted()) {
            Thread.sleep(10);
        }
        assertTrue(byteOutput.toString().contains("HTTP/1.1 404"));
    }

    /**
     * Test dispatch method.
     */
    @Test
    void testDispatch3() {
        DefaultWebApplication webApp = new DefaultWebApplication();
        DefaultWebApplicationRequest request = new DefaultWebApplicationRequest();
        request.setWebApplication(webApp);
        DefaultWebApplicationResponse response = new DefaultWebApplicationResponse();
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        response.setUnderlyingOutputStream(byteOutput);
        response.setWebApplication(webApp);
        //DefaultAsyncContext context = new DefaultAsyncContext(request, response);
        /// context.dispatch(webApp, "/mypath");
    }
}
