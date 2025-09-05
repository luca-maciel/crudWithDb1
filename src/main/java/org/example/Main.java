package org.example;
import java.util.Scanner;
import java.sql.*;
import javax.swing.*;

//CREATE TABLE IF NOT EXISTS Author(
//        id SERIAL PRIMARY KEY NOT NULL UNIQUE,
//        name VARCHAR(255) NOT NULL UNIQUE
//);
//
//
//CREATE TABLE IF NOT EXISTS Book (
//        id SERIAL PRIMARY KEY NOT NULL UNIQUE,
//        author_id INT NOT NULL,
//        title VARCHAR(255) NOT NULL UNIQUE,
//        gender VARCHAR(255) NOT NULL,
//
//FOREIGN KEY (author_id) references Author(id)
//        );


public class Main {
    public static String url = "jdbc:postgresql://localhost:5432/library";
    public static String username = "lucas";
    public static String password = "7695";
    public static Scanner input = new Scanner(System.in);
    public static int menu_option = 99;


//    String queryAuthor = "CREATE TABLE IF NOT EXISTS author(" +
//            "id SERIAL PRIMARY KEY NOT NULL UNIQUE," +
//            "name VARCHAR(255) NOT NULL UNIQUE);";
//
//    String queryBook = "CREATE TABLE IF NOT EXISTS book(" +
//            "id SERIAL PRIMARY KEY NOT NULL UNIQUE," +
//            "author_id INT NOT NULL," +
//            "title VARCHAR(255) NOT NULL UNIQUE," +
//            "gender VARCHAR(255) NOT NULL," +
//            "FOREIGN KEY (author_id) REFERENCES author(id));";

//    void createTables(){
//        try (Connection conn = DriverManager.getConnection(url, username, password)){
//            Statement statement = conn.createStatement();
//            statement.executeUpdate(queryAuthor);
//            statement.executeUpdate(queryBook);
//            System.out.println("✅ Tabelas criadas com sucesso!");
//        }
//        catch (SQLException e){
//            System.err.println("Erro ao criar tabelas: " + e.getMessage());
//        }
//    }

    void showAuthors(){
        String authors = "";
        try (Connection conn = DriverManager.getConnection(url, username, password)){
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM author;");
            while (result.next()){
                authors += ("ID do autor: " + result.getString("id") + " | Nome: " + result.getString("name") + "\n");
//                JOptionPane.showMessageDialog(null, autores);
//                System.out.print("Id do autor: ");
//                System.out.println(result.getString("id"));
//                System.out.print("Nome do autor: ");
//                System.out.println(result.getString("name"));
//                System.out.println("==================================================");
//                System.out.println(result.getString("id") + " | " + result.getString("name"));

            }
            JOptionPane.showMessageDialog(null, authors);

        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    void showBooks(){
        String books = "";
        try (Connection conn = DriverManager.getConnection(url, username, password)){
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM book;");
            while (result.next()){
                books += ("Id do livro: " + result.getString("id") + " | Id do autor: " + result.getString("author_id") + " | Titulo: " + result.getString("title") + " | Gênero: " + result.getString("gender") + "\n");
//                System.out.print("Id do livro: ");
//                System.out.println(result.getString("id"));
//                System.out.print("Id do autor do livro: ");
//                System.out.println(result.getString("author_id"));
//                System.out.print("Titulo do livro: ");
//                System.out.println(result.getString("title"));
//                System.out.print("Gênero do livro: ");
//                System.out.println(result.getString("gender"));
//                System.out.println("==================================================");
//                System.out.println(result.getString("id") + " | " + result.getString("author_id") + " | " + result.getString("title") + " | " + result.getString("gender"));
            }
            JOptionPane.showMessageDialog(null, books);
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    void insertAuthor(){
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String nameNewAuthor = JOptionPane.showInputDialog("Name for new author");
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO author (name) VALUES ('" + nameNewAuthor + "');");
//            ResultSet result = statement.executeUpdate("INSERT INTO author (name) VALUES (" + name + ");");
            JOptionPane.showMessageDialog(null, "Autor adicionado.");
            System.out.println("Author added");
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    void insertBook(){
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            Statement statement = conn.createStatement();
            int author_id = Integer.parseInt(JOptionPane.showInputDialog(null, "Id do autor"));
            String title = JOptionPane.showInputDialog(null, "Titulo do livro");
            String gender = JOptionPane.showInputDialog(null, "Gênero do livro");
            statement.executeUpdate("INSERT INTO book (author_id, title, gender) VALUES ('" + author_id + "', '" + title + "', '" + gender + "');");
//            ResultSet result = statement.executeUpdate("INSERT INTO author (name) VALUES (" + name + ");");
            JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso.");
//            System.out.println("book added");
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    void updateAuthor(){
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            Statement statement = conn.createStatement();
            int id_author = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do author?"));
            try {
                statement.executeQuery(("SELECT * FROM author WHERE id="+id_author+";"));
                String newName = JOptionPane.showInputDialog(null, "Novo nome");
                statement.executeUpdate("UPDATE author SET name='"+newName+"' WHERE id="+id_author+";");
                JOptionPane.showMessageDialog(null, "Nome do autor alterado com sucesso.");
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Erro\n" + e);
            }


        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    void updateBook(){
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            Statement statement = conn.createStatement();
            int id_book = Integer.parseInt(JOptionPane.showInputDialog("Id do livro a ser alterado"));
            try{
                ResultSet result = statement.executeQuery("SELECT * FROM book WHERE id="+id_book+";");
                if (Boolean.parseBoolean(String.valueOf(result.next()))){
                    int editOption = Integer.parseInt(JOptionPane.showInputDialog("O que deseja alterar no livro?\n[1] - Titulo\n[2] - Gênero\n[0] - Cancelar"));
                    switch (editOption){
                        case 1:
                            String newTitle = JOptionPane.showInputDialog(null, "Novo titulo do livro");
                            statement.executeUpdate("UPDATE book SET title='"+newTitle+"' WHERE id="+id_book+";");
                            JOptionPane.showMessageDialog(null, "Titulo alterado");
                            break;
                        case 2:
                            String newGender = JOptionPane.showInputDialog("Novo gênero");
                            statement.executeUpdate("UPDATE book SET gender='"+newGender+"' WHERE id="+id_book+";");
                            JOptionPane.showMessageDialog(null, "Gênero alterado com sucesso.");
                            break;
                        case 0:
                            JOptionPane.showMessageDialog(null, "Operação cancelada");
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Escolha uma opção válida");
                            break;
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Livro inexistente");
                    return;
                }

            }
            catch (SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    void deleteAuthor(){
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            int target_id = Integer.parseInt(JOptionPane.showInputDialog("Id do autor a ser deletado"));
            try{
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM author WHERE id="+target_id+";");
                if (Boolean.parseBoolean(String.valueOf(result.next()))){
                    statement.executeUpdate("DELETE FROM book WHERE author_id="+target_id+";");
                    statement.executeUpdate("DELETE FROM author WHERE id="+target_id+";");
                    JOptionPane.showMessageDialog(null, "Autor deletado com sucesso");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Erro.");
                    return;
                }
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    void deleteBook(){
        try(Connection conn = DriverManager.getConnection(url, username, password)){
            int target_id = Integer.parseInt(JOptionPane.showInputDialog("Id do livro a ser deletado"));
            try{
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM book WHERE id="+target_id+";");
                if (Boolean.parseBoolean(String.valueOf(result.next()))){
                    statement.executeUpdate("DELETE FROM book WHERE id="+target_id+";");
                    JOptionPane.showMessageDialog(null, "Livro deletado com sucesso");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Erro.");
                    return;
                }
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static void main(String[] args) throws SQLException {

        // MENU


//        frame.setSize(1280, 720);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);

        Main main = new Main();
//        main.createTables();
//        main.insertAuthor("Aluisio Azevedo");
//        main.insertBook(1, "O cortiço", "Romance");
        while (menu_option != 0){
//            System.out.println("O que você deseja fazer?\n[1] - Ver todos os livros\n[2] - Ver todos os autores\n[3] - Adicionar autor\n[0] - Sair");
            menu_option = Integer.parseInt(JOptionPane.showInputDialog(null, "O que você deseja fazer?\n[1] - Ver todos os livros\n[2] - Ver todos os autores\n[3] - Adicionar autor\n[4] - Adicionar livro\n[5] - Editar autor\n[6] - Editar livro\n[7] - Excluir um autor\n[8] - Excluir um livro\n[0] - Sair"));
//            input.nextLine();

            switch (menu_option){
                case 1:
                    main.showBooks();
                    break;
                case 2:
                    main.showAuthors();
                    break;
                case 3:
                    main.insertAuthor();
                    break;
                case 4:
                    main.insertBook();
                    break;
                case 5:
                    main.updateAuthor();
                    break;
                case 6:
                    main.updateBook();
                    break;
                case 7:
                    main.deleteAuthor();
                    break;
                case 8:
                    main.deleteBook();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Escolha uma opção válida.");
                    break;
            }
        }

//        try (Connection conn = DriverManager.getConnection(url, username, password)){
//            if (conn != null) {
//                System.out.println("Connected to database.");
//            }
//            else{
//                System.out.println("Failed to connect to database.");
//            }
//        }
//        catch (SQLException e){
//            System.out.println(e.getMessage());
//        }



    }
}