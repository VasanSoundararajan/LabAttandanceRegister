import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.*;
import org.json.*;
import java.util.*;

class Server extends JFrame {
    private DefaultTableModel tableModel;
    private File jsonFile;

    public Server() {
        super("Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        // Create table with default table model
        JTable table = new JTable();
        tableModel = new DefaultTableModel(new Object[] { "Client", "Message" }, 0);
        table.setModel(tableModel);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane);

        setVisible(true);

        // Initialize JSON file
        jsonFile = new File("messages.json");
        if (!jsonFile.exists()) {
            try {
                jsonFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMessage(String client, String message) {
        tableModel.addRow(new Object[] { client, message });

        // Store message in JSON file
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("client", client);
            jsonObject.put("message", message);

            JSONArray jsonArray;
            if (jsonFile.length() == 0) {
                jsonArray = new JSONArray();
            } else {
                String content = new Scanner(jsonFile).useDelimiter("\\Z").next();
                jsonArray = new JSONArray(content);
            }

            jsonArray.put(jsonObject);

            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonArray.toString());
            fileWriter.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final int PORT = 12345;

        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for clients to connect...");

            // Create server frame
            Server server = new Server();

            // Keep accepting client connections
            while (true) {
                // Accept client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Create a new thread to handle the client
                ClientHandler clientHandler = new ClientHandler(clientSocket, server);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Error in server: " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            // Input stream to read data from client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Output stream to send data to client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read input from client and send it to server for display
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                server.addMessage(clientSocket.getInetAddress().getHostAddress(), inputLine);
                out.println("Server echo: " + inputLine);
            }

            // Close streams and socket
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}
