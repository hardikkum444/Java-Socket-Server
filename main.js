//used ACE editor
let inputEditor = ace.edit("inputEditor");
inputEditor.setTheme("ace/theme/gruvbox_dark_hard");
inputEditor.getSession().setMode("ace/mode/java");

let outputEditor = ace.edit("outputEditor");
outputEditor.setTheme("ace/theme/cobalt");
outputEditor.setReadOnly(true);


function sendRequest() {
    var request = inputEditor.getValue();
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "https://7929-2409-40d1-c-b28f-3a8f-18f4-ae8a-ac8.ngrok-free.app", true);
    xhr.setRequestHeader("Content-Type", "text/plain");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                outputEditor.setValue(xhr.responseText);
            } else {
                // Handle error here
                outputEditor.setValue('Error: ' + xhr.status + ' ' + xhr.statusText+' Cannot connect to server');
                // outputEditor.setValue('Cannot connect to server');

            }
        }
    };
    xhr.send(request);
}
