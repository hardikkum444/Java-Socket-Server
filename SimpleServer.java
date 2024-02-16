// import java.util.*;
// import java.net.ServerSocket;
// import java.io.IOException;
// import java.net.Socket;
// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.io.OutputStreamReader;
// import java.io.PrintWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class SimpleServer {
    public static void main(String[] args) throws IOException{

    	ServerSocket server = new ServerSocket(9000);

    	while(true){
    		final Socket client = server.accept();

    		BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    		PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

    		while(true){

    			String line = reader.readLine();
    			System.out.println("Header : "+line);
    			if(line.equals("")){
    				break;
    			}

    			if(line.contains("GET")){

    				int startIndex = line.indexOf("/");
    				int endIndex = line.indexOf(" HTTP");
    				String person = line.substring(startIndex+1, endIndex);
    				System.out.println("User's name is: "+person);
    			}
    		}
    	}
        
    }
}




