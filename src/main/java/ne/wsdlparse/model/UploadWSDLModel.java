/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ne.wsdlparse.model;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nour
 */
public class UploadWSDLModel {
    private String name;
    private String mime;
    private String extension;
    private int size;
    private MultipartFile file;
    private boolean overwriteEnabled;
    private String page;
    private String success;
    private String error;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public boolean isOverwriteEnabled() {
        return overwriteEnabled;
    }

    public void setOverwriteEnabled(boolean overwriteEnabled) {
        this.overwriteEnabled = overwriteEnabled;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
