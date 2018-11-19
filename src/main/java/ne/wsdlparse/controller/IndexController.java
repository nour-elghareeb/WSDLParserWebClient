/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ne.wsdlparse.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import ne.utils.MiscUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
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
            model.addAttribute("exception", ex);
            return "error";
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
