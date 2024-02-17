// import java.util.*;
// import java.net.ServerSocket;
// import java.io.IOException;
// import java.net.Socket;
// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.io.OutputStreamReader;
// import java.io.PrintWriter;

// import com.hardik.Details;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(9000);
            System.err.println("Server is running on port 9000");

            while (true) {
                final Socket client = server.accept(); 

                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                String person = "";

                while (true) {
                    String line = reader.readLine();
                    System.out.println("Header : " + line);
                    if (line.equals("")) {
                        break;
                    }

                    if (line.contains("GET")) {
                        int startIndex = line.indexOf("/");
                        int endIndex = line.indexOf(" HTTP");
                        person = line.substring(startIndex + 1, endIndex);
                        System.out.println("User's name is: " + person);
                        
                        // Retrieve user details
                        Details userDetails = getDetails(person);
                        // You can use userDetails object to send appropriate response


                // writer.println("please enter your name");
                // writer.flush(); // Ensure message is sent immediately

                // String info = reader.readLine();
                // System.out.println("Received from client: " + info);


                    }
                }






                String crlf = "\r\n";
                Details detail = getDetails(person);
                writer.print("HTTP/1.1 200 OK"+crlf);
                writer.print("Content-Type: application/json"+crlf);
                writer.println("Content-Length: "+detail.toString().length()+crlf);

                writer.print(detail.name);

                writer.flush();
                writer.close();
                reader.close();
                client.close();






            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println();
            System.out.println("An error has occurred");
            System.out.println("Port is probably already in use!");
        }
    }


    public static Details getDetails(String name){
        Details hardik = new Details();
        if(name.equals("Hardik")){

            hardik.name = "Hardik";
            hardik.age = 20;
            hardik.phoneNumber = 8208;
            hardik.email = "hardikkumawat444@gmail.com";
            // System.out.println(hardik.email);
        }

        return hardik;
    }



}

class Details{
    String name;
    int age;
    int phoneNumber;
    String email;
}




