// main.js

// Wait for the DOM content to be fully loaded
document.addEventListener("DOMContentLoaded", function() {
    // Get the DOM element for the editor
    let editor = document.querySelector("#editor");

    // Initialize the Ace Editor
    let aceEditor = ace.edit(editor);

    // Set the theme
    aceEditor.setTheme("ace/theme/cobalt");
});
