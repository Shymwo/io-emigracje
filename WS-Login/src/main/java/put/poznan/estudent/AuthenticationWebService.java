package put.poznan.estudent;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface AuthenticationWebService{
 
	@WebMethod AuthenticationResult authenticate(String name, String password,  String checksum);
 
}