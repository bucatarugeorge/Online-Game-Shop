package com.sda.grouproject;

import com.sda.grouproject.enums.AgeRestriction;
import com.sda.grouproject.enums.Exclusive;
import com.sda.grouproject.enums.Genre;
import com.sda.grouproject.model.Developer;
import com.sda.grouproject.model.Game;
import com.sda.grouproject.model.Publisher;
import com.sda.grouproject.model.User;
import com.sda.grouproject.repository.*;
import com.sda.grouproject.utils.SessionManager;

import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        DeveloperRepository developerRepository= new DeveloperRepository();
        PublisherRepository publisherRepository= new PublisherRepository();
        GameRepository gameRepository= new GameRepository();

        User user1= new User("bigPePeEnergy69", "bibPP420@gmail.smokeit","123456");
        Developer developer1= new Developer("ABCD");
        Publisher publisher1= new Publisher("EFGH");
        Developer developer2= new Developer("Bungie");
        Publisher publisher2= new Publisher("Bungie");
        User user2= new User("D", "D@", "12");

        UserRepository.getInstance().save(user1);
        UserRepository.getInstance().save(user2);
        developerRepository.save(developer1);
        developerRepository.save(developer2);
        publisherRepository.save(publisher1);
        publisherRepository.save(publisher2);

        User user= new User();

        GameUserInterface gameUserInterface= new GameUserInterface();
        //gameUserInterface.registerSave(user);


        Game game1= new Game("Cyberpunk 2077", 60.0,"Dissapointment", Genre.SANDBOX,
                Exclusive.ALL,3, AgeRestriction.MATURE, publisher1, developer1);
        Game game2= new Game("Destiny 2", 60.0, "Depression", Genre.MMORPG,
                Exclusive.ALL, 1, AgeRestriction.TEEN,publisher2, developer2);
        gameRepository.save(game1);
        gameRepository.save(game2);
//        gameUserInterface.logIn();
//        gameUserInterface.findGames();
        //Scanner scanner = new Scanner(System.in);
        List<Game> listOfGamesBought= gameUserInterface.findGames();
            gameUserInterface.addGamesToShoppingCart(listOfGamesBought);
        List<Game> listOfGamesInShoppingCart= gameUserInterface.addGamesToShoppingCart(listOfGamesBought);


        SessionManager.shutDown();
    }
}
