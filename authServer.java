import java.io.*;
import java.net.*;
import java.util.*;

public class authServer{

	public static void main(String[] args) throws Exception {

		
		ServerSocket server = new ServerSocket(9000);

		while(true){

			final Socket client = server.accept();
			System.out.println("Client connected: " + client.getInetAddress().getHostAddress());

			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

			writer.println("HTTP/1.1 401 Unauthorised");
			writer.println("WWW-Authenticate: Basic realm=\"Restricted Area\"");
			writer.println();
			writer.flush();

			// String authHeader = reader.readLine();


			String authHeader = null;
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                if (line.startsWith("Authorization: Basic")) {
                    authHeader = line;
                    System.out.println(authHeader);
                    break;

                }
            }



			if(authHeader != null && authHeader.startsWith("Authorization: Basic")){
				String encoded = authHeader.substring("Authorization: Basic".length()).trim();
				String cred = new String(Base64.getDecoder().decode(encoded));
				String[] parts = cred.split(":");

				String name = "Hardik";
				String name2 = "Kamran";

				if(parts.length == 2 && (parts[0].equals(name)|| parts[0].equals(name2)) && parts[1].equals("pass")){

					String crlf = "\r\n";
					writer.println("HTTP/1.1 200 OK"+crlf);
					writer.println("Cache-Control: no-cache"+crlf);
					writer.println();
					writer.print("welcome admin to the server");
					writer.flush();

				}else{

					writer.println("HTTP/1.1 403 Forbidden");
					writer.println();
					writer.println("Invalid username or password");
					writer.flush();

				}
			}

			client.close();


		}
	}
}