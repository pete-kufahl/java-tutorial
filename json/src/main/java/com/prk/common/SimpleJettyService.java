package com.prk.common;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;

import java.io.IOException;

public class SimpleJettyService {

    public static void main(String[] args) throws Exception {
        // Create a Jetty server on port 8000
        Server server = new Server(8000);

        // Set up context handler
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Register servlet
        context.addServlet(HelloServlet.class, "/hello");

        // Start the server
        server.start();
        System.out.println("Jetty server started on http://localhost:8000/hello");
        server.join();
    }

    // Simple servlet that returns a plain text response
    public static class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("Hello from Jetty!");
        }
    }
}
