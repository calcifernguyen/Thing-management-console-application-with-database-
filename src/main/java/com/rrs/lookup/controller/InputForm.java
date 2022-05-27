package com.rrs.lookup.controller;

import com.rrs.lookup.entity.BookOnTape;
import com.rrs.lookup.entity.Furniture;
import com.rrs.lookup.entity.Thing;
import com.rrs.lookup.entity.Video;
import com.rrs.lookup.utils.ThingType;
import com.rrs.lookup.utils.ValidateInput;

import java.util.Scanner;

public class InputForm {
    private final Scanner scanner;
    private final Thing thing;
    private final ThingType thingType;

    public InputForm(Scanner scanner, ThingType thingType, Thing thing) {
        this.scanner = scanner;
        this.thing = thing;
        this.thingType = thingType;
    }

    // input: none
    // output: Thing object contain form data
    public Thing input() {
        ValidateInput validateInput = new ValidateInput();
        boolean submit = false;     //flag = true => return form data
        int choice = -1;
        while (!submit) {
            //print form
            System.out.println(thing.getDescription());
            //print options
            System.out.format("|   Order number - Set the value for the that field      |%n");
            System.out.format("|   8 - Submit                                           |%n");
            System.out.format("|   0 - Cancel                                           |%n");
            System.out.format("|   *Note: serial = 0 => serial = auto generate          |%n");
            System.out.format("+--------------------------------------------------------+%n");
            System.out.print("Your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {   //input is not number
                choice = -1;
            }

            switch (choice) {
                case 1:
                    thing.setSerial(validateInput.getLongInput(scanner,true));
                    break;
                case 2:
                    System.out.print("Enter value: ");
                    thing.setName(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Enter value: ");
                    case3FollowThingType();
                    break;
                case 4:
                    System.out.print("Enter value: ");
                    case4FollowThingType();
                    break;
                case 5:
                    thing.setPrice(validateInput.getBigDecimalInput(scanner,true));
                    break;
                case 6:
                    int total = validateInput.getIntInput(scanner,true);
                    thing.setTotal(total);

                    //set total = available
                    System.out.print("Do you want set available field with this value? [y/n] ");
                    String selected = scanner.nextLine();
                    if(selected.equals("y"))
                        thing.setAvailable(total);

                    break;
                case 7:
                    int available = Integer.MAX_VALUE;
                    boolean isValid = false;
                    while (!isValid) {
                        available = validateInput.getIntInput(scanner,true);

                        //Validate available > total
                        if (available > thing.getTotal())
                            System.out.println("Available must be less than total!");
                        else
                            isValid = true;
                    }
                    thing.setAvailable(available);
                    break;
                case 8:
                    submit = true;
                    System.out.println("Submitted!");
                    break;
                case 0:
                    System.out.println("Canceled!");
                    return null;
            }
        }

        return thing;
    }

    public void case3FollowThingType() {
        switch (thingType) {
            case VIDEO: ((Video) thing).setLength(scanner.nextLine()); break;
            case FURNITURE: ((Furniture) thing).setMaterial(scanner.nextLine()); break;
            case BOOK_ON_TAPE: ((BookOnTape) thing).setAuthor(scanner.nextLine()); break;
        }
    }

    public void case4FollowThingType() {
        switch (thingType) {
            case VIDEO: ((Video) thing).setSummary(scanner.nextLine()); break;
            case FURNITURE: ((Furniture) thing).setColor(scanner.nextLine()); break;
            case BOOK_ON_TAPE: ((BookOnTape) thing).setSummary(scanner.nextLine()); break;
        }
    }
}
