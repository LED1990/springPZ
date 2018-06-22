
package pz.klasywsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the pz.klasywsdl package. 
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

    private final static QName _WSSigningException_QNAME = new QName("http://exception.ws.comarch.gov", "WSSigningException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pz.klasywsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WSSigningException }
     * 
     */
    public WSSigningException createWSSigningException() {
        return new WSSigningException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSSigningException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://exception.ws.comarch.gov", name = "WSSigningException")
    public JAXBElement<WSSigningException> createWSSigningException(WSSigningException value) {
        return new JAXBElement<WSSigningException>(_WSSigningException_QNAME, WSSigningException.class, null, value);
    }

}
