package ch.hftm.ggq.service.impl;

import ch.hftm.ggq.service.XslTransformerService;

import javax.enterprise.context.Dependent;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;

@Dependent
public class XslTransformerServiceImpl implements XslTransformerService {

    private static final TransformerFactory TRANSFORMER_FACTORY;

    static {
//        System.setProperty("javax.xml.transform.TransformerFactory",
//                "org.apache.xalan.processor.TransformerFactoryImpl");
        TRANSFORMER_FACTORY = TransformerFactory.newInstance();
        TRANSFORMER_FACTORY.setURIResolver((href, base) -> {
            final InputStream s = XslTransformerServiceImpl.class.getClassLoader().getResourceAsStream("xsl/" + href);
            return new StreamSource(s);
        });
    }

    @Override
    public void transform(Path xmlFile, Path xslFile, Path output) throws TransformerException, IOException {
        transform(xmlFile, xslFile, output, Collections.EMPTY_MAP);
    }

    @Override
    public void transform(Path xmlFile, Path xslFile, Path output, Map<String, String> params) throws TransformerException, IOException {
        try (final InputStream xmlStream = new FileInputStream(xmlFile.toFile());
             final InputStreamReader xmlReader = new InputStreamReader(xmlStream);) {
            transform(xmlReader, xslFile, output, params);
        } catch (TransformerException | IOException e) {
            throw e;
        }
    }

    @Override
    public void transform(Reader xmlReader, Path xslFile, Path output, Map<String, String> params) throws TransformerException, IOException {
        try (final InputStream xslStream = new FileInputStream(xslFile.toFile());) {
            transform(xmlReader, xslStream, output, params);
        } catch (TransformerException | IOException e) {
            throw e;
        }
    }

    @Override
    public void transform(Reader xmlReader, InputStream xslStream, Path output, Map<String, String> params) throws TransformerException, IOException {
        System.out.println(output);
        try (final InputStreamReader xslReader = new InputStreamReader(xslStream);
             final FileOutputStream outputStream = new FileOutputStream(output.toFile());) {
            Source xmlSrc = new StreamSource(xmlReader);
            Source xslSrc = new StreamSource(xslReader);
            Result result = new StreamResult(outputStream);
            Transformer transformer = TRANSFORMER_FACTORY.newTransformer(xslSrc);
            params.forEach(transformer::setParameter);
            transformer.transform(xmlSrc, result);
        } catch (TransformerException | IOException e) {
            throw e;
        }
    }
}
