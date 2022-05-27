package com.rrs.lookup.controller;

import com.rrs.lookup.dao.BookOnTapeDAO;
import com.rrs.lookup.dao.FurnitureDAO;
import com.rrs.lookup.dao.ThingDAO;
import com.rrs.lookup.dao.VideoDAO;
import com.rrs.lookup.entity.BookOnTape;
import com.rrs.lookup.entity.Furniture;
import com.rrs.lookup.entity.Thing;
import com.rrs.lookup.entity.Video;
import com.rrs.lookup.utils.ConnectionUtils;
import com.rrs.lookup.utils.ThingType;
import com.rrs.lookup.utils.ValidateInput;

import java.util.List;
import java.util.Scanner;

public class Lookup {
    private final Scanner scanner;

    private final ValidateInput validateInput;

    // 1 row in THING table + 1 row in type table
    private static final int AFFECTED_ROWS_INSERT_THING_SUCCESSFULLY = 2;

    public Lookup() {
        this.validateInput = new ValidateInput();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice = -1;

        while (true) {
            printMainMenu();

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {   //input is not number
                choice = -1;
            }

            switch (choice) {
                case 1: addThing(); break;
                case 2: searchThingBySerial(); break;
                case 3: showAllVideo(); break;
                case 4: showAllFurniture(); break;
                case 5: showAllBookOnTape(); break;
                case 0: System.exit(0); break;
            }
        }
    }

    public void printMainMenu() {
        System.out.println("+----------------------------------+");
        System.out.println("|               MENU               |");
        System.out.println("+----------------------------------+");
        System.out.println("|   1 -- Add thing                 |");
        System.out.println("|   2 -- Search thing by serial    |");
        System.out.println("|   3 -- Show all video            |");
        System.out.println("|   4 -- Show all furniture        |");
        System.out.println("|   5 -- Show all book on tape     |");
        System.out.println("|   0 -- Exit                      |");
        System.out.println("+----------------------------------+");
        System.out.print("Your choice: ");
    }

    public void addThing() {
        boolean backFlag = false;
        int choice;
        while (!backFlag) {
            System.out.println("+------------------------+");
            System.out.println("|   Please choose type:  |");
            System.out.println("+------------------------+");
            System.out.println("|   1 -- Video           |");
            System.out.println("|   2 -- Furniture       |");
            System.out.println("|   3 -- Book on tape    |");
            System.out.println("|   0 -- Back            |");
            System.out.println("+------------------------+");
            System.out.print("Your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {   //input is not number
                choice = -1;
            }

            switch (choice) {
                case 1:
                    addVideo();
                    break;
                case 2:
                    addFurniture();
                    break;
                case 3:
                    addBookOnTape();
                    break;
                case 0: backFlag = true;
                    break;
            }
        }
    }

    public void addVideo() {
        //Create video form
        InputForm form = new InputForm(scanner, ThingType.VIDEO,new Video());
        //get input
        Video video = (Video) form.input();

        if (video == null) return;  //canceled

        //insert to database
        VideoDAO videoDAO = new VideoDAO(ConnectionUtils.getMyConnection());
        if (videoDAO.insert(video) == AFFECTED_ROWS_INSERT_THING_SUCCESSFULLY)
            System.out.println("Add successfully!");
        else
            System.out.println("Failed!");
    }

    public void addFurniture() {
        //Create furniture form
        InputForm form = new InputForm(scanner, ThingType.FURNITURE,new Furniture());
        //get input
        Furniture furniture = (Furniture) form.input();

        if (furniture == null) return; //canceled

        //insert to database
        FurnitureDAO furnitureDAO = new FurnitureDAO(ConnectionUtils.getMyConnection());
        if (furnitureDAO.insert(furniture) == AFFECTED_ROWS_INSERT_THING_SUCCESSFULLY)
            System.out.println("Add successfully!");
        else
            System.out.println("Failed!");

    }

    public void addBookOnTape() {
        //Create book on tape form
        InputForm form = new InputForm(scanner, ThingType.BOOK_ON_TAPE,new BookOnTape());
        //get input
        BookOnTape bookOnTape = (BookOnTape) form.input();

        if (bookOnTape == null) return; //canceled

        //insert to database
        BookOnTapeDAO bookOnTapeDAO = new BookOnTapeDAO(ConnectionUtils.getMyConnection());
        if (bookOnTapeDAO.insert(bookOnTape) == AFFECTED_ROWS_INSERT_THING_SUCCESSFULLY)
            System.out.println("Add successfully!");
        else
            System.out.println("Failed!");
    }

    public void searchThingBySerial() {
        //get serial
        System.out.println("Enter serial of thing");
        long serial = validateInput.getLongInput(scanner,true);

        //find in database
        ThingDAO thingDAO = new ThingDAO(ConnectionUtils.getMyConnection());
        Thing thing = thingDAO.selectById(serial);  //result
        //show information of thing
        if (thing != null) System.out.println(thing.getDescription());
    }

    public void showAllVideo() {
        //get in database
        VideoDAO videoDAO = new VideoDAO(ConnectionUtils.getMyConnection());
        List<Video> videos = videoDAO.selectAll();  //result

        System.out.println("\n=================================================================");
        if (!videos.isEmpty()) {
            System.out.println("Have " + videos.size() + " video(s)\n");
            videos.stream().map(Video::getDescription).forEach(System.out::println);
            System.out.println("\nHave " + videos.size() + " video(s)");
        } else {    //Not found any things
            System.out.println("No videos found!");
        }
        System.out.println("=================================================================");

    }

    public void showAllFurniture() {
        //get in database
        FurnitureDAO furnitureDAO = new FurnitureDAO(ConnectionUtils.getMyConnection());
        List<Furniture> furnitureList = furnitureDAO.selectAll();   //result

        System.out.println("\n=================================================================");
        if (!furnitureList.isEmpty()) {
            System.out.println("Have " + furnitureList.size() + " furniture\n");
            furnitureList.stream().map(Furniture::getDescription).forEach(System.out::println);
            System.out.println("\nHave " + furnitureList.size() + " furniture");
        } else {    //Not found any things
            System.out.println("No furniture found!");
        }
        System.out.println("=================================================================");
    }

    public void showAllBookOnTape() {
        //get in database
        BookOnTapeDAO bookOnTapeDAO = new BookOnTapeDAO(ConnectionUtils.getMyConnection());
        List<BookOnTape> bookOnTapes = bookOnTapeDAO.selectAll();   //result

        System.out.println("\n=================================================================");
        if (!bookOnTapes.isEmpty()) {
            System.out.println("Have " + bookOnTapes.size() + " Book on tape\n");
            bookOnTapes.stream().map(BookOnTape::getDescription).forEach(System.out::println);
            System.out.println("\nHave " + bookOnTapes.size() + " Book on tape");
        } else {    //Not found any things
            System.out.println("No book on tape found!");
        }
        System.out.println("=================================================================\n");
    }
}
