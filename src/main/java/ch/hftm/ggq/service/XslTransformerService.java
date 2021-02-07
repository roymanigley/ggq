package ch.hftm.ggq.service;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;

public interface XslTransformerService {

    void transform(Path xmlFile, Path xslFile, Path output) throws TransformerException, IOException;
    void transform(Path xmlFile, Path xslFile, Path output, Map<String, String> params) throws TransformerException, IOException;
    void transform(Reader xmlReader, Path xslFile, Path output, Map<String, String> params) throws TransformerException, IOException;
    void transform(Reader xmlReader, InputStream xslStream, Path output, Map<String, String> params) throws TransformerException, IOException;
}
