import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

class ClientGUI extends JFrame implements ActionListener {
    private JTextField userInputField;
    private JLabel NameLabel;
    private JTextArea chatArea;
    private JButton sendButton;
    private PrintWriter output;
    private BufferedReader input;
    private Socket socket;

    public ClientGUI() {
        setTitle("Register");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        NameLabel = new JLabel("User name");

        userInputField = new JTextField(20);
        chatArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(chatArea);
        sendButton = new JButton("Enter");
        sendButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4));
        panel.add(NameLabel);
        panel.add(userInputField);
        panel.add(sendButton);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        setVisible(true);

        try {
            // Connect to the server
            socket = new Socket("localhost", 12345);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start a thread to handle receiving messages from the server
            Thread receivingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String message;
                        while ((message = input.readLine()) != null) {
                            chatArea.append("Server: " + message + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receivingThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userInput = userInputField.getText();
        if (!userInput.isEmpty()) {
            // Send user input to the server
            output.println(userInput);
            chatArea.append("Client: " + userInput + "\n");
            userInputField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClientGUI();
            }
        });
    }

    // Close the socket when the client window is closed
    @Override
    public void dispose() {
        super.dispose();
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
