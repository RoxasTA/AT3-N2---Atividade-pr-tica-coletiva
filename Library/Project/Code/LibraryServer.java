import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
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
                    String answer = processInput(requireClint);
                    exit.println(answer);
                }

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String processInput(String input) {
            String[] tokens = input.split("#");
            String command = tokens[0];

            switch (command) {
                case "list":
                    return listbooks();
                case "rent":
                    return rentBook();
                case "return":
                    return returnBook();
                case "register":
                    return rigisterBook();
                case "exit":
                    return "exit";
                default:
                    return "Invalid command";
            }
        }

        private String listbooks() {
            StringBuilder list = new StringBuilder();
            for (Book book : books) {
                list.append(book.toString()).append("\n");
            }
            return list.toString();
        }

        private String rentBook(String bookName) {
            for (Book book : books) {
                if (book.getTitle.equals(bookName)) {
                    if (livro.getExemplares() > 0) {
                        book.setExemplares(book.getSemple() -1);
                        saveBooks();
                        return "Book Rent!";
                    } else {
                        return "Theres no sample of that book!";
                    }
                }
            }
            return "Book not found!";
        }

        private String returnBook(String bookName) {
            for (Book book : books) {
                if (book.getTitle().equals(bookName)) {
                    book.setSamples(book.getSamples() + 1);
                    saveBooks();
                    return "Book Returned!";
                }
            }
            return "Book not found!";
        }

        private String rigisterBook(String bookName) {
            String[] atribites = bookName.split("#");
            String author = atribites[0];
            String title = atribites[1];
            String genre = atribites[2];
            int samples = Integer.parseInt(atribites[3].trim());
            Book newBook = new Book(author, title, genre, samples);
            books.add(newBook);
            saveBooks();
            return "Book Rented!";
        }
    }

    static class BooksWrapper {
        private List<Book> books;

        public BooksWrapper(List<Book> books) {this.books = books;}

        public List<Book> getBooks() {return books;}

        public void setBooks(List<Book> books) {this.books = books;}
    }
}