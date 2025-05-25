package com.prk.streamingConsumer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.prk.common.SimpleJettyService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ApplicationStreamingServlet extends HttpServlet {
    private final JsonFactory factory = new JsonFactory();

    @Override
    protected void doPost(
        HttpServletRequest req,
        HttpServletResponse resp
    ) throws IOException {
        JsonParser parser = factory.createParser(req.getInputStream());

        double totalIncome = 0.0;
        double amount = 0.0;

        JsonToken token;
        while((token = parser.nextToken()) != null) {
            String currentName = parser.currentName();
            if (currentName != null && token.isNumeric()) {
                switch (currentName) {
                    case "annualIncome":
                        totalIncome += parser.getDoubleValue();
                        break;
                    case "amount":
                        amount = parser.getDoubleValue();
                        break;
                }
            }
        }

        int status;
        String message;
        if (amount <= 3 * totalIncome) {
            status = HttpServletResponse.SC_OK;
            message = "Approved";
        } else {
            status = HttpServletResponse.SC_FORBIDDEN;
            message = "Denied";
        }
        resp.setStatus(status);
        resp.getWriter().println(message);
    }

    public static void main(String[] args) {
        SimpleJettyService.run(ApplicationStreamingServlet.class);
    }
}
