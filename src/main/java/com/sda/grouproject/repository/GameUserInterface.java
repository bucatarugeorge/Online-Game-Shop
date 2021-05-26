package com.sda.grouproject.repository;

import com.sda.grouproject.enums.Exclusive;
import com.sda.grouproject.enums.Genre;
import com.sda.grouproject.model.Game;
import com.sda.grouproject.model.User;
import com.sda.grouproject.utils.SessionManager;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameUserInterface {

    String FIND_BY_DEVELOPER = "from Game g WHERE g.developer.developerName =";
    String FIND_BY_PUBLISHER = "from Game g WHERE g.publisher.publisherName =";
    String FIND_BY_NAME = "from Game g WHERE g.name =";
    String FIND_BY_EXCLUSIVE = "from Game g WHERE g.exclusive =";
    String FIND_BY_GENRE = "from Game g WHERE g.genre =";
    String FIND_BY_RATING = "from Game g WHERE g.rating >=";
    String FIND_ALL_GAMES = "from Game";
    private List<Game> selectedGamesForShoppingCart= new ArrayList<>();

    public void registerSave(User user) {
        Scanner scanner = new Scanner(System.in);
        registerUserName(user);
        registerEmail(user);
        System.out.println("Pasword:");
        user.setPassword(scanner.nextLine());
        UserRepository.getInstance().save(user);

    }

    public void logIn() {
        System.out.print("Welcome to GameShop: Register or Login?:");
        Scanner scanner = new Scanner(System.in);
        String chosenOption = scanner.nextLine();
        if (chosenOption.equals("Register")) {
            User user = new User();
            registerSave(user);
            logIn();
        } else if (chosenOption.equals("Login")) {
            System.out.println("Please introduce your username or email:");
            String usernameOrEmail = scanner.nextLine();
            if (!UserRepository.getInstance().findByColumn("username", usernameOrEmail)
                    || !UserRepository.getInstance().findByColumn("email", usernameOrEmail)) {
                System.out.println("Please introduce your password:");
                String password = scanner.nextLine();
                if (!UserRepository.getInstance().checkPassword(password)) {
                    System.out.println(usernameOrEmail + " welcome to GameShop.");
                }
            } else {
                System.out.println("Something went wrong, check your credentials.");
                logIn();
            }
        } else {
            System.out.println("The option introduced is incorrect!");
        }

    }

    private void registerUserName(User user) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Username:");
        String usernameOption = scanner.nextLine();

        if (UserRepository.getInstance().findByColumn("username", usernameOption)) {
            user.setUsername(usernameOption);
        } else {
            System.out.println("Username is already in use. Please introduce another.");
            registerUserName(user);
        }

    }

    private void registerEmail(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Email:");
        String emailOption = scanner.nextLine();

        if (UserRepository.getInstance().findByColumn("email", emailOption)) {
            user.setEmail(emailOption);
        } else {
            System.out.println("Email is already in use. Please introduce another.");
            registerEmail(user);
        }
    }

    private List<Game> findByGameColumnName(String findBy, String object) {
        Session session = SessionManager.getSessionFactory().openSession();
        String genreQuery;
        if(!object.equals(""))
        {
            genreQuery = findBy + " '" + object + "' ";
        }
        else
            {
            genreQuery= findBy;
        }
        Query<Game> gameQuery = session.createQuery(genreQuery);
        List<Game> gameList = gameQuery.list();
        //todo might need to reAdd this System.out.println(gameList)
        session.close();
        return gameList;
    }

    public List<Game> findGames() {
        System.out.println("Please select a number from 0 to 7.\n" +
                "0. Show All\n" +
                "1. Developer Name\n" +
                "2. Publisher Name\n" +
                "3. Game Name\n" +
                "4. Genre\n" +
                "5. Exclusives\n" +
                "6. Rating\n" +
                "7. Exit");
        Scanner scanner = new Scanner(System.in);
        String number = scanner.nextLine();
        List<Game> games = new ArrayList<>();
        try {
            switch (number) {

                case "0":
                    System.out.println("Show all games.");
                    games = findByGameColumnName(FIND_ALL_GAMES, "");
                    break;
                case "1":
                    Scanner scanner2 = new Scanner(System.in);
                    System.out.println("Find By developer name. Please input one.");
                        games = findByGameColumnName(FIND_BY_DEVELOPER, scanner2.nextLine());
                    break;
                case "2":
                    Scanner scanner3 = new Scanner(System.in);
                    System.out.println("Find by publisher name. Please input one.");
                    games = findByGameColumnName(FIND_BY_PUBLISHER, scanner3.nextLine());
                    break;
                case "3":
                    Scanner scanner4 = new Scanner(System.in);
                    System.out.println("Find by game name. Please input one.");
                    games = findByGameColumnName(FIND_BY_NAME, scanner4.nextLine());
                    break;
                case "4":
                    Scanner scanner5 = new Scanner(System.in);
                    System.out.println("Find by genre");
                    games = findByGameColumnName(FIND_BY_GENRE, scanner5.nextLine());
                    break;
                case "5":
                    Scanner scanner6 = new Scanner(System.in);
                    System.out.println("Find by exclusives. Please input one.");
                    games = findByGameColumnName(FIND_BY_EXCLUSIVE, scanner6.nextLine());
                    break;
                case "6":
                    Scanner scanner7 = new Scanner(System.in);
                    System.out.println("Find by rating. Please input one.");
                    games = findByGameColumnName(FIND_BY_RATING, scanner7.nextLine());
                    break;
                case "7":
                    System.out.println("ByeBye.");
                    break;

                default:
                    System.out.println("YO DAWG, I SAID NUMBERS.");
                    findGames();
                    break;
            }
        }
        catch (NoResultException e)
        {
            System.out.println("No results found.");
            findGames();
        }
        return games;
    }
    public List<Game> addGamesToShoppingCart(List<Game> listOfGamesBought) {

        System.out.println("List of Games selected:");
        int i=1;
        for (Game game: listOfGamesBought) {
            System.out.println(i+ ". " + game);
            i++;
        }
        System.out.println("Type Exit if you wish to leave or Add if you want to continue.");

        Scanner scanner1= new Scanner(System.in);
        String choice= scanner1.nextLine();

        if (!choice.equals("Exit") && choice.equals("Add"))
        {
            System.out.println("Select the game you wish to add to your shopping cart or Exit.");
            Scanner scanner = new Scanner(System.in);
            int selectedIndexGame = scanner.nextInt();
            selectedGamesForShoppingCart.add(listOfGamesBought.get(selectedIndexGame - 1));
            System.out.println("List of Games in Shopping cart\n" +
                    selectedGamesForShoppingCart);
            addGamesToShoppingCart(listOfGamesBought);
        }
        else if(choice.equals("Exit"))
        {
            System.out.println("BYE-BYE");
        }
        return selectedGamesForShoppingCart;
    }

    public void payForGames(List<Game> listOfGamesInShoppingCart)
    {
        //todo add superficial functionality for paying for the games
    }
}




