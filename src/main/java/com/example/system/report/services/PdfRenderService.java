package com.example.system.report.services;

import com.example.system.report.exceptions.PdfGenerationException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfRenderService {
    private final TemplateEngine templateEngine;

    public byte[] render(String template, Map<String, Object> model) {
        Context ctx = new Context(Locale.forLanguageTag("th-TH"));
        ctx.setVariables(model);

        String html = templateEngine.process(template, ctx);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, "");
            builder.toStream(out);
            builder.run();

            return out.toByteArray();
        } catch (Exception e) {
            throw new PdfGenerationException("Failed to generate PDF", e);
        }
    }
}
