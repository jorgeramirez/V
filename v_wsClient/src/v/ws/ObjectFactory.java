
package v.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the v.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RegistrarPagosResponse_QNAME = new QName("http://ws.v/", "registrarPagosResponse");
    private final static QName _RegistrarPagos_QNAME = new QName("http://ws.v/", "registrarPagos");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: v.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PagoWs }
     * 
     */
    public PagoWs createPagoWs() {
        return new PagoWs();
    }

    /**
     * Create an instance of {@link RegistrarPagosResponse }
     * 
     */
    public RegistrarPagosResponse createRegistrarPagosResponse() {
        return new RegistrarPagosResponse();
    }

    /**
     * Create an instance of {@link RegistrarPagos }
     * 
     */
    public RegistrarPagos createRegistrarPagos() {
        return new RegistrarPagos();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarPagosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.v/", name = "registrarPagosResponse")
    public JAXBElement<RegistrarPagosResponse> createRegistrarPagosResponse(RegistrarPagosResponse value) {
        return new JAXBElement<RegistrarPagosResponse>(_RegistrarPagosResponse_QNAME, RegistrarPagosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarPagos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.v/", name = "registrarPagos")
    public JAXBElement<RegistrarPagos> createRegistrarPagos(RegistrarPagos value) {
        return new JAXBElement<RegistrarPagos>(_RegistrarPagos_QNAME, RegistrarPagos.class, null, value);
    }

}
