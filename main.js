//used ACE editor
let inputEditor = ace.edit("inputEditor");
inputEditor.setTheme("ace/theme/cobalt");
inputEditor.getSession().setMode("ace/mode/java");

let outputEditor = ace.edit("outputEditor");
outputEditor.setTheme("ace/theme/cobalt");
outputEditor.setReadOnly(true);


function sendRequest() {
    var request = inputEditor.getValue();
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:9000", true);
    xhr.setRequestHeader("Content-Type", "text/plain");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            outputEditor.setValue(xhr.responseText);
        }
    };
    xhr.send(request);
}
