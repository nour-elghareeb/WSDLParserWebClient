/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ne.wsdlparse.controller;

import com.sun.xml.internal.ws.util.StringUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import ne.utils.MiscUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import wsdlparse.ne.DeleteWSDLRequest;
import wsdlparse.ne.DeleteWSDLResponse;
import wsdlparse.ne.GenerateMessageESQLRequest;
import wsdlparse.ne.GenerateMessageESQLResponse;
import wsdlparse.ne.ListAvailableWSDLsRequest;
import wsdlparse.ne.ListAvailableWSDLsResponse;
import wsdlparse.ne.ListOperationMessagesRequest;
import wsdlparse.ne.ListOperationMessagesResponse;
import wsdlparse.ne.ListPortOperationsRequest;
import wsdlparse.ne.ListPortOperationsResponse;
import wsdlparse.ne.ListServicePortsRequest;
import wsdlparse.ne.ListServicePortsResponse;
import wsdlparse.ne.ListServicesRequest;
import wsdlparse.ne.ListServicesResponse;
import wsdlparse.ne.WSDLParserFault;
import wsdlparse.ne.WSDLParser_Service;

/**
 *
 * @author nour
 */
@Controller
public class IndexController {
    
    @RequestMapping("/")
    public String list(Model model) {
        model.addAttribute("page", "list");
        try {
            ListAvailableWSDLsResponse response = new WSDLParser_Service().getWSDLParserSOAP().listAvailableWSDLs(new ListAvailableWSDLsRequest());

            model.addAttribute("wsdlFiles", response.getWSDL());
            return "list";
        } catch (WSDLParserFault ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("exception", ex);
            model.addAttribute("errorMessage", ex.getFaultInfo().getErrorMessage());
            return "error";
        }

    }

    @RequestMapping(method = GET, value = "/View/{wsdlName}")
    public String listServices(@PathVariable String wsdlName, Model model) {
        model.addAttribute("page", "view");
        try {
            ListServicesRequest listServicesRequest = new ListServicesRequest();
            listServicesRequest.setWSDLName(wsdlName);
            ListServicesResponse response = new WSDLParser_Service().getWSDLParserSOAP().listServices(listServicesRequest);
            model.addAttribute("crumbs", new String[]{"WSDL#" + wsdlName});
            model.addAttribute("currentAction", "listServices");
            model.addAttribute("wsdlName", wsdlName);
            model.addAttribute("items", response.getService());
            model.addAttribute("currentPath", "/View/" + wsdlName);
            return "view";
        } catch (WSDLParserFault ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("exception", ex);
            model.addAttribute("errorMessage", ex.getFaultInfo().getErrorMessage());
            return "error";
        }
    }

    @RequestMapping(method = GET, value = "/View/{wsdlName}/{serviceName}")
    public String listPorts(@PathVariable String wsdlName, @PathVariable String serviceName, Model model) {
        model.addAttribute("page", "view");
        try {
            ListServicePortsRequest listServicePortsRequest = new ListServicePortsRequest();
            listServicePortsRequest.setWSDLName(wsdlName);
            listServicePortsRequest.setServiceName(serviceName);
            ListServicePortsResponse response = new WSDLParser_Service().getWSDLParserSOAP().listServicePorts(listServicePortsRequest);
            model.addAttribute("crumbs", new String[]{"WSDL#" + wsdlName, "Service#" + serviceName});

            model.addAttribute("wsdlName", wsdlName);
            model.addAttribute("serviceName", serviceName);
            model.addAttribute("currentAction", "listPorts");
            model.addAttribute("items", response.getPort());
            model.addAttribute("currentPath", "/View/" + wsdlName + "/" + serviceName);

            return "view";
        } catch (WSDLParserFault ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("exception", ex);
            model.addAttribute("errorMessage", ex.getFaultInfo().getErrorMessage());
            return "error";
        }
    }

    @RequestMapping(method = GET, value = "/View/{wsdlName}/{serviceName}/{portName}")
    public String listPorts(@PathVariable String wsdlName, @PathVariable String serviceName, @PathVariable String portName, Model model) {
        model.addAttribute("page", "view");
        try {
            String crumb = "";

            ListPortOperationsRequest request = new ListPortOperationsRequest();
            request.setWSDLName(wsdlName);
            request.setServiceName(serviceName);
            request.setPortName(portName);
            ListPortOperationsResponse response = new WSDLParser_Service().getWSDLParserSOAP().listPortOperations(request);
            model.addAttribute("crumbs", new String[]{"WSDL#" + wsdlName, "Service#" + serviceName, "Port#" + portName});

            model.addAttribute("wsdlName", wsdlName);
            model.addAttribute("serviceName", serviceName);
            model.addAttribute("portName", portName);
            model.addAttribute("currentAction", "listOperations");
            model.addAttribute("items", response.getOperation());
            model.addAttribute("currentPath", "/View/" + wsdlName + "/" + serviceName + "/" + portName);

            return "view";
        } catch (WSDLParserFault ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("errorMessage", ex.getFaultInfo().getErrorMessage());
            model.addAttribute("exception", ex);
            return "error";
        }
    }

    @RequestMapping(method = GET, value = "/View/{wsdlName}/{serviceName}/{portName}/{operationName}")
    public String listMessages(@PathVariable String wsdlName, @PathVariable String serviceName, @PathVariable String portName, @PathVariable String operationName, Model model) {
        model.addAttribute("page", "view");
        try {
            String crumb = "";

            ListOperationMessagesRequest request = new ListOperationMessagesRequest();
            request.setWSDLName(wsdlName);
            request.setServiceName(serviceName);
            request.setPortName(portName);
            request.setOperationName(operationName);
            ListOperationMessagesResponse response = new WSDLParser_Service().getWSDLParserSOAP().listOperationMessages(request);
            model.addAttribute("crumbs", new String[]{"WSDL#" + wsdlName, "Service#" + serviceName, "Port#" + portName, "Operation#" + operationName});

            model.addAttribute("wsdlName", wsdlName);
            model.addAttribute("serviceName", serviceName);
            model.addAttribute("portName", portName);
            model.addAttribute("currentAction", "listMessages");
            model.addAttribute("items", response.getMessage());
            model.addAttribute("currentPath", "/View/" + wsdlName + "/" + serviceName + "/" + portName + "/" + operationName);

            return "view";
        } catch (WSDLParserFault ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("errorMessage", ex.getFaultInfo().getErrorMessage());
            model.addAttribute("error", ex.getFaultInfo().getErrorMessage());
            model.addAttribute("exception", ex);
            return "error";
        }
    }
    @RequestMapping(method = GET, value = "/View/{wsdlName}/{serviceName}/{portName}/{operationName}/{messageType}/{messageName}")
    public String generateESQL(@PathVariable String wsdlName, @PathVariable String serviceName, @PathVariable String portName, @PathVariable String operationName, @PathVariable(required = false) String messageType, @PathVariable(required = false) String messageName, Model model) {
        model.addAttribute("page", "view");
        try {
            String crumb = "";
            GenerateMessageESQLRequest request = new GenerateMessageESQLRequest();
            request.setWSDLName(wsdlName);
            request.setServiceName(serviceName);
            request.setPortName(portName);
            request.setOperationName(operationName);
            request.setMessageName(messageName == null ? messageType : messageName);
            request.setESQLSource("output");
            request.getESQLVerboisty().add("VALUE_HELP");
            request.setUseReferenceAsVariables(true);
            model.addAttribute("crumbs", new String[]{"WSDL#" + wsdlName, "Service#" + serviceName, "Port#" + portName, "Operation#" + operationName, messageType+"#"+messageName});
            model.addAttribute("wsdlName", wsdlName);
            model.addAttribute("serviceName", serviceName);
            model.addAttribute("portName", portName);
            model.addAttribute("messageType", StringUtils.capitalize(messageType));
            model.addAttribute("messageName", messageName);
            model.addAttribute("currentAction", "listMessages");
            
            model.addAttribute("currentPath", "/View/" + wsdlName + "/" + serviceName + "/" + portName + "/" + operationName);
            GenerateMessageESQLResponse response = new WSDLParser_Service().getWSDLParserSOAP().generateMessageESQL(request);
            model.addAttribute("items", response.getESQLLine());            

        } catch (WSDLParserFault ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("errorMessage", ex.getFaultInfo().getErrorMessage());
            model.addAttribute("error", ex.getFaultInfo().getErrorMessage());
            model.addAttribute("exception", ex);
            
        }
        return "generate";
    }
    
    @PostMapping("/delete")
    public String delete(@RequestParam(value="wsdlName", required = true) String wsdlName, Model model){
        try {
            DeleteWSDLRequest request = new DeleteWSDLRequest();
            request.setWSDLName(wsdlName);
            DeleteWSDLResponse response = new WSDLParser_Service().getWSDLParserSOAP().deleteWSDL(request);
            model.addAttribute("success", "WSDL deleted successfully!");
            return list(model);
        } catch (WSDLParserFault ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("error", ex.getFaultInfo().getErrorMessage());
            return list(model);
        }
    }

    @RequestMapping(method = GET, value = "/test")
    public String test(Model model) {
        String url = "/View/GetBESCustomer/Ftth_WS/Ftth_WSSoap";
        model.addAttribute("urlBefore", url);
        model.addAttribute("urlAfter", MiscUtils.getBeforeNthMatch(url, 2, "/"));
        return "test";
    }

}
