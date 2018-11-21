/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $("body").on("click", ".deleteWSDLButton", function () {
        $("#modalTitle").text("Delete " + $(this).parents("form.deleteWSDLForm").find("input[name=wsdlName]").val());
        $("#deleteModal").modal('show');
        button = $(this);
        
        $("body").on("click", "#confirmDelete", function () {
            button.parents("form.deleteWSDLForm").submit();
        });
    });

});