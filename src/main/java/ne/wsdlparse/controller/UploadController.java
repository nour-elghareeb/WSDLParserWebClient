/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ne.wsdlparse.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import ne.wsdlparse.model.UploadWSDLModel;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import wsdlparse.ne.UploadWSDLRequest;
import wsdlparse.ne.UploadWSDLResponse;
import wsdlparse.ne.WSDLParserFault;
import wsdlparse.ne.WSDLParser_Service;

/**
 *
 * @author nour
 */
@Controller
public class UploadController {

    @GetMapping(value = "/upload")
    public String upload(Model model) {
        model.addAttribute("page", "upload");
        model.addAttribute("model", new UploadWSDLModel());
        return "upload";
    }

    @PostMapping(value = "/upload")
    public String uploadProcessing(final @RequestPart(value = "wsdlFile") MultipartFile file, @RequestParam(value="overwrite", required = false, defaultValue = "") String overwrite, Model model) {

        model.addAttribute("page", "upload");
        UploadWSDLResponse response;
        if (file.getContentType().equals("application/zip") || (file.getContentType().equals("application/octet-stream") && FilenameUtils.getExtension(file.getOriginalFilename()).equals("wsdl"))) {
            try {
                UploadWSDLRequest request = new UploadWSDLRequest();
                request.setFileName(FilenameUtils.getBaseName(file.getOriginalFilename()));
                request.setFileExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
                request.setFileSize(file.getSize());
                request.setMimeType(file.getContentType().equals("application/octet-stream") ? "application/xml" : file.getContentType());
                request.setOverwrite(overwrite.equals("on"));
                request.setFileContents(file.getBytes());
                response = new WSDLParser_Service().getWSDLParserSOAP().uploadWSDL(request);
                model.addAttribute("success", "WSDL "+file.getName() + " uploaded successfully");
            } catch (WSDLParserFault ex) {
                Logger.getLogger(UploadController.class.getName()).log(Level.SEVERE, null, ex);
                model.addAttribute("error", ex.getFaultInfo().getErrorMessage());
            }catch (Exception ex){
                Logger.getLogger(UploadController.class.getName()).log(Level.SEVERE, null, ex);
                model.addAttribute("error", ex.getMessage());
            }            
        }else{
            model.addAttribute("error", "Unsupported file format!");
        }
        return "upload";
    }
}
