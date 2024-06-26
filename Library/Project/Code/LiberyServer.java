package java;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import Book;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class LiberyServer {
    private static final String JSONFIFILE = "";
    private static List<BOOK> books;

    public static void main(String[] args) {

        final int PORTA = 1337;

        try {
            ServerSocket servidorSocket = new ServerSocket;
            System.out.println("Servidor carregado com sucesso!");
            while (true) {
                Socket clientSocket = servidorSocket.accept();
                System.out.println("Conectado com sucesso!" + clientSocket);

                Thread clientThread = new Thread(new Client(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void loadbooks() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(JSONFIFILE);
        if (!file.exists()) {
            try {
                JsonNode rootNode = mapper.readTree(file);
                JsonNode booksNode = rootNode.path("books");
                CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, Book.class);
                books = mapper.convertValue(booksNode, listType);
            } catch (IOException e) {
                e.printStackTrace();
                books = new ArrayList<>();
            }
        } else {
            System.out.println("Books cadastrados com sucesso!");
            books = new ArrayList<>();
            saveBooks();
        }
    }

    private static synchronized void saveBooks() {
        ObjecMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(JSONFIFILE), new BooksWrapper(books));
        } catch (IOExpection e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {this.clientSocket = clientSocket;}

        @overide
        public void run() {
            try {
                BufferReader enter = new BufferReader(new InpoutStreamReader(clientSocket.getInputSream()));
                PrintWriter exit = new PrintWriter(clientSocket.getOutStream(), true);

                String requireClint;
                while ((requireClint = enter.readLine()) != null) {
                    String clint = requireClint.trim().split(" ");
                    exit.println(clint);
                }

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}