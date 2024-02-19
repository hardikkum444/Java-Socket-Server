import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.lang.*;

public class changes {

    public static void main(String[] args) {

        try (ServerSocket myServer = new ServerSocket(9000)) {

            System.out.println("Server started. Listening on port 9000...");

            while (true) {
                Socket client = myServer.accept();
                System.out.println("Client connected: " + client.getInetAddress());

                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                
                String person = "";
                String output = "";
                String fileName = "";
                String input = "";
                boolean isFin = false;

                StringBuilder requestBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.isEmpty()) {
                        isFin = true;
                        break;
                    }
                    requestBuilder.append(line).append("\n");
                }


                //-------------------------------------------------------------------------------------------------------
                //checking and implementing the payload



                if (isFin) {
                    // Check if the request has a payload
                    int contentLength = 0;
                    String contentLengthHeader = "Content-Length: ";
                    for (String header : requestBuilder.toString().split("\n")) {
                        if (header.startsWith(contentLengthHeader)) {
                            contentLength = Integer.parseInt(header.substring(contentLengthHeader.length()).trim());
                            break;
                        }
                    }

                    // Read the payload
                    StringBuilder payloadBuilder = new StringBuilder();
                    int bytesRead = 0;
                    while (bytesRead < contentLength) {
                        int c = reader.read();
                        
                        if (c == -1) {
                            break; // End of stream reached unexpectedly
                        }

                        char character = (char) c;
                        payloadBuilder.append(character);

                        String payload1 = payloadBuilder.toString();

                        if(payload1.contains("public class")&& payload1.contains(" {")){
                            // int index1 = payload1.indexOf("public class");
                            int index1 = payload1.indexOf("public class") + "public class".length();
                            int index2 = payload1.indexOf(" {",index1);
                            fileName = payload1.substring(index1,index2).trim();
                            
                            // if (fileName.endsWith("}")){
                            //     fileName = fileName.substring(0,fileName.length()-2);
                            // }


                            // fileName = payload1.substring(index1+1,index2);
                            // fileName = payload1.substring(index1 + "public class".length(), index2).trim();

                        }

                        // if(payload1.contains("//*")){

                        //     int index3 = payload1.indexOf("//*") + "//*".length();
                        //     if(payload1.substring(index3).trim().contains("//*")){
                                
                        //         payload1 = payload1.replace("//*", "");

                        //     }
                        //     input = payload1.substring(index3).trim();

                        // }


                        if (payload1.contains("//*")) {
                            int index3 = payload1.indexOf("//*") + "//*".length();
                            String trimmedSubstring = payload1.substring(index3).trim();
                            if (trimmedSubstring.contains("//*")) {
                                trimmedSubstring = trimmedSubstring.replace("//*", "");
                            }
                            input = trimmedSubstring;
                        }

                        bytesRead++;
                    }

                    String payload = payloadBuilder.toString().trim();
                    System.out.println("Payload from client: \n" + payload);

                    // int index1 = payload.indexOf("public class ");
                    // int index2 = payload

                    File code = new File(fileName+".java");
                    try(PrintWriter codeWriter = new PrintWriter(code)){
                        codeWriter.println(payload);
                    }

                    String command = "javac -d . "+fileName+".java";
                    Process compile = Runtime.getRuntime().exec(command);




                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(compile.getErrorStream()));
                    StringBuilder compileError = new StringBuilder();
                    String error = "";

                    while((error = errorReader.readLine()) != null){

                        compileError.append(error).append("\n");

                    }

                    String finalError = compileError.toString();
                    if(!finalError.isEmpty()){

                        output = finalError;
                    }else{


                    // try {
                    //     compile.waitFor();
                    // } catch (InterruptedException e) {
                    //     e.printStackTrace();
                    // }

                    // String command_3 = "";
                    String command_2 = "java " + fileName; // Default command without input
                    // if (!input.isEmpty()) {
                    //     command_2 += "\n" + input; 
                    // }

                    Process execute = Runtime.getRuntime().exec(command_2);


                    if (!input.isEmpty()) {
                        OutputStream outputStream = execute.getOutputStream();
                        PrintWriter writer1 = new PrintWriter(new OutputStreamWriter(outputStream));
                        writer1.println(input); // Write input to the processd
                        writer1.flush();
                    }

                    // if (!input.isEmpty()) {
                    //     BufferedWriter writer1 = new BufferedWriter(new OutputStreamWriter(execute.getOutputStream()));
                    //     writer1.write(input); // Write input to the process
                    //     writer1.newLine(); // Add a new line if needed
                    //     writer1.flush();
                    // }

                    BufferedReader execReader = new BufferedReader(new InputStreamReader(execute.getInputStream()));
                    StringBuilder outputBuilder = new StringBuilder();
                    String execLine = "";
                    while((execLine = execReader.readLine()) != null){
                        
                        outputBuilder.append(execLine).append("\n");
                    }

                    output = outputBuilder.toString();

                    // String command_3 = "rm main.class";
                    // Process delete = Runtime.getRuntime().exec(command_3);

                    }


                }

                //-------------------------------------------------------------------------------------------------------


                String request = requestBuilder.toString();
                System.out.println("Received request:\n" + request);

                // Add CORS headers to allow requests from any origin
                writer.print("HTTP/1.1 200 OK\r\n");
                writer.print("Content-Type: text/plain\r\n");
                writer.print("Access-Control-Allow-Origin: *\r\n"); // Allow requests from any origin
                writer.print("\r\n");

                // Send a simple response
                // writer.print("Hello, client! Your request was received.\r\n");
                writer.print(output);
                // writer.print(input);


                
                writer.flush();

                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }
}
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.OutputStreamWriter;
// import java.io.PrintWriter;
// import java.net.ServerSocket;
// import java.net.Socket;

// public class serverPrac {

//     public static void main(String[] args) {

//         try (ServerSocket myServer = new ServerSocket(9000)) {

//             System.out.println("Server started. Listening on port 9000...");

//             while (true) {
//                 Socket client = myServer.accept();
//                 System.out.println("Client connected: " + client.getInetAddress());

//                 BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                
//                 String person = "";
//                 boolean isFin = false;

//                 StringBuilder requestBuilder = new StringBuilder();
//                 String line;
//                 while ((line = reader.readLine()) != null) {
//                     if (line.isEmpty()) {
//                      isFin = true;
//                         break;
//                     }
//                     // if(line.contains("GET")){
//                     //   int startIndex = line.indexOf("/");
//                     //   int endIndex = line.indexOf(" HTTP");
//                     //   person = line.substring(startIndex+1,endIndex);

//                     // }
//                     requestBuilder.append(line).append("\n");
//                 }

//                 if(isFin){

//                  StringBuilder payloadBuilder = new StringBuilder();

//                  while ((line = reader.readLine()) != null){

//                      payloadBuilder.append(line).append("\n");
//                  }

//                  String payload = payloadBuilder.toString().trim();
//                  System.out.println("Payload from client: "+payload);
//                 }

//                 String request = requestBuilder.toString();
//                 System.out.println("Received request:\n" + request);

//                 // Add CORS headers to allow requests from any origin
//                 writer.print("HTTP/1.1 200 OK\r\n");
//                 writer.print("Content-Type: text/plain\r\n");
//                 writer.print("Access-Control-Allow-Origin: *\r\n"); // Allow requests from any origin
//                 writer.print("\r\n");

//                 // Send a simple response
//                 writer.print("Hello, client! Your request was received.\r\n");
//                 writer.print("Users name is "+person);
                

//                 writer.flush();

//                 client.close();
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//             System.out.println("Error");4
//         }
//     }
// }


