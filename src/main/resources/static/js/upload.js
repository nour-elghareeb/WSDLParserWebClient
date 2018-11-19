/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
hasError = true;
errMsg = null;
$(document).ready(function () {
    $("#uploadForm").submit(function () {
        if (hasError) {
            if (errMsg === null) {
                $("#errordiv").text("Please choose file first");
                $("#errordiv").removeClass("d-none");
            }
            return false;
        }
    });
    $("form").on("change", "#wsdlFile", function () {

        hasError = true;
        errMsg = null;
        $("#errordiv").addClass("d-none");
        file = $(this)[0].files[0];
        $("#fileHelp").text(file.name);
        $("input[name=name]").val(file.name.split('.').slice(0, -1).join('.'));
        fileextinsion = file.name.substring(file.name.lastIndexOf('.') + 1);
        $("input[name=extension]").val(fileextinsion);
        filetype = file.type;
        $("input[name=size]").val(file.size);
        if ((filetype === null || filetype === "") && fileextinsion === "wsdl") {
            filetype = "application/xml";
        }
        if ((filetype === "application/zip" || filetype === "application/xml") && (fileextinsion === "zip" || fileextinsion === "wsdl")) {
            hasError = false;



            var reader = new FileReader();
            reader.onload = function () {

                var arrayBuffer = this.result,
                        array = new Uint8Array(arrayBuffer),
                        binaryString = String.fromCharCode.apply(null, array);

                
                $("input[name=contents]").val(btoa(binaryString));

            };
            reader.readAsArrayBuffer(file);


//            $("input[name=contents]").val(btoa(file   ));
//            var reader = new FileReader();
//            reader.readAsDataURL(file);
//            reader.onload = function () {
//                $("input[name=contents]").val();
//            };
//            reader.onerror = function (error) {
//                console.log('Error: ', error);
//            };

        } else {
            errMsg = "Invalid file type. Only .zip and .wsdl are allowed.";
            $("#errordiv").text(errMsg);
            $("#errordiv").removeClass("d-none");
        }
        $("input[name=mime]").val(filetype);

    });



});