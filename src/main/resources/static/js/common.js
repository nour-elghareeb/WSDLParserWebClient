/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function applyNewVals(inp, arr) {
    var a, b, i, val = inp;

    if (!val) {
        return false;
    }
    currentFocus = -1;
    /*create a DIV element that will contain the items (values):*/
    a = document.createElement("DIV");
    a.setAttribute("id", inp.id + "autocomplete-list");
    a.setAttribute("class", "autocomplete-items");
    /*append the DIV element as a child of the autocomplete container:*/
    inp.parentNode.appendChild(a);
    /*for each item in the array...*/
    for (i = 0; i < arr.length; i++) {
        /*check if the item starts with the same letters as the text field value:*/
        startpos = arr[i].toLowerCase() .search(val.value.toLowerCase())
        if (startpos === -1){
            return false;
        }
        /*create a DIV element for each matching element:*/
        b = document.createElement("DIV");
        /*make the matching letters bold:*/
        console.log("startpos: " +startpos);
        firstPart = "";
        if (startpos !== 0){           
            firstPart = arr[i].substr(0, startpos);
            b.innerHTML = firstPart
        }
        b.innerHTML += "<strong>" + arr[i].substr(startpos, val.value.length) + "</strong>";
        b.innerHTML += arr[i].substr(val.value.length+firstPart.length, arr[i].length);
        /*insert a input field that will hold 2the current array item's value:*/
        b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
        /*execute a function when someone clicks on the item value (DIV element):*/
        b.addEventListener("click", function (e) {
            /*insert the value for the autocomplete text field:*/
            inp.value = this.getElementsByTagName("input")[0].value;
            /*close the list of autocompleted values,
             (or any other open lists of autocompleted values:*/
            context = $("input:hidden#context").val();
            window.location.href = context + "View/" + inp.value;
        });
        a.appendChild(b);

    }
}
function autocomplete(inp) {
    /*the autocomplete function takes two arguments,
     the text field element and an array of possible autocompleted values:*/
    var currentFocus;
    /*execute a function when someone writes in the text field:*/
    inp.addEventListener("input", function (e) {
        closeAllLists();
        searchViaAjax(this)

    });
    /*execute a function presses a key on the keyboard:*/
    inp.addEventListener("keydown", function (e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x)
            x = x.getElementsByTagName("div");
        if (e.keyCode == 40) {
            /*If the arrow DOWN key is pressed,
             increase the currentFocus variable:*/
            currentFocus++;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode == 38) { //up
            /*If the arrow UP key is pressed,
             decrease the currentFocus variable:*/
            currentFocus--;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode == 13) {
            /*If the ENTER key is pressed, prevent the form from being submitted,*/
            e.preventDefault();
            if (currentFocus > -1) {
                /*and simulate a click on the "active" item:*/
                if (x)
                    x[currentFocus].click();
            }
        }
    });
    function addActive(x) {
        /*a function to classify an item as "active":*/
        if (!x)
            return false;
        /*start by removing the "active" class on all items:*/
        removeActive(x);
        if (currentFocus >= x.length)
            currentFocus = 0;
        if (currentFocus < 0)
            currentFocus = (x.length - 1);
        /*add class "autocomplete-active":*/
        x[currentFocus].classList.add("autocomplete-active");
    }
    function removeActive(x) {
        /*a function to remove the "active" class from all autocomplete items:*/
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }
    function closeAllLists(elmnt) {
        /*close all autocomplete lists in the document,
         except the one passed as an argument:*/
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }
    /*execute a function when someone clicks in the document:*/
    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}
var wsdls = [];
$(document).ready(function () {
    autocomplete(document.getElementById("filterWSDLs"), wsdls);
});


function searchViaAjax(input) {

    var data = {}
    data.filterValue = $("#filterWSDLs").val();
    filterForm = $("#filterForm");
    $("#filterSpinner").removeClass("hidden")
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: filterForm.attr("action"),
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 5000,
        
        success: function (data) {
            applyNewVals(input, data)
            wsdls = data;
        },
        error: function (e) {
            console.log("ERROR: ", e);

        },
        complete: function (e) {
            console.log("DONE");
            
            $("#filterSpinner").addClass("hidden");
        },
    });

}