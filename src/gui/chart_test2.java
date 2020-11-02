package gui;


import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Time;
import java.io.*;
import java.net.Socket;

/**
 * same as Game_Phase_Chart_Box.java
 * Just for test and demo
 */

public class chart_test2 implements ActionListener {
    //copy from join_game gui
    static JFrame frame;
    static JPanel panel;
    static JTextArea chat;
    static JTextField unsend;
    static JButton send;
    static String chat_log = "";
    static String My_Name = "Micheal";
    static Client me = new Client();

    public chart_test2() throws IOException {
        frame = new JFrame();
        panel = new JPanel();
        frame.setTitle("TEST 2");
        frame.setSize(300,540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);

        chat = new JTextArea();
        chat.setText(chat_log);
        chat.setEditable(false);
        chat.setLineWrap(true);
        JScrollPane Text_scroll = new JScrollPane(chat);
        Text_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        Text_scroll.setBounds(10,10,260,450);
        panel.add(Text_scroll);

        unsend = new JTextField();
        unsend.setBounds(10,460,190,30);
        panel.add(unsend);

        send = new JButton("Send");
        send.setBounds(200,460,70,30);
        send.addActionListener(this);
        panel.add(send);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new chart_test2();
        Client.setName(My_Name);
        Client.run();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(send)) {
            String input = unsend.getText();
            if (input != null && !input.equals("")) {
                Client.writeMessage(input);
                Time time = new Time(System.currentTimeMillis());
                chat_log = chat_log + "" + time + "\n * Me:" + input + "\n\n";
                chat.setText(chat_log);
                unsend.setText("");
            }
        }
    }

    /**Client Class*/
    static class Client {
        private static String message;
        private static String name = "";

        public static void writeMessage(String m) {
            message = m;
        }

        public static void setName(String input) {
            name = input;
        }

        public static void run() throws IOException {
            InetAddress address = InetAddress.getByName("localhost");
            int port = 16225;

            if (name.equals(""))
                return;

            Socket client = null;

            try {
                client = new Socket(address, port);
            } catch (IOException e) {
                System.err.println(name + " connect failed");
            }

            assert client != null;
            Send s = new Send(client, name);
            Thread send_thread = new Thread(s);
            send_thread.start();
            Receive r = new Receive(client);
            Thread receive_thread = new Thread(r);
            receive_thread.start();
        }

        /**send class*/
        static class Send implements Runnable {
            private DataOutputStream os;
            private boolean status = true;
            private String msg;

            public Send(Socket client, String name) {
                try {
                    os = new DataOutputStream(client.getOutputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    send(name);
                } catch (IOException e) {
                    e.printStackTrace();
                    status = false;
                }
            }

            @Override
            public void run() {
                while (status) {
                    try {
                        send(message);
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void send(String msg) {
                try{
                    if (msg != null) {
                        os.writeUTF(msg);
                        os.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    status = false;
                }
                message = null; //rest
            }
        }

        /**receive class**/
        static class Receive implements Runnable{
            private DataInputStream is;
            private boolean status = true;

            public Receive(Socket client) {
                try {
                    is = new DataInputStream(client.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    status = false;
                }
            }

            @Override
            public void run() {
                while(status){
                    receive();
                }
            }

            public void receive() {
                String msg = null;
                try {
                    msg = is.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                    status = false;
                }
                System.out.println(msg);
                Time time = new Time(System.currentTimeMillis());
                chat_log = chat_log + "" + time + "\n" + msg + "\n\n";
                chat.setText(chat_log);
                //Automatically scroll down
                DefaultCaret caret = (DefaultCaret)chat.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            }
        }
    }
}