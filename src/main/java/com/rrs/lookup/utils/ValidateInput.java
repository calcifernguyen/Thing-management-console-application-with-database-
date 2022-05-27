package com.rrs.lookup.utils;

import com.rrs.lookup.utils.myexception.NegativeException;

import java.math.BigDecimal;
import java.util.Scanner;

/*
    Support validate and handle exception input
*/
public class ValidateInput {
    public int getIntInput(Scanner scanner, boolean zeroOrPositive /* value >= 0 flag */) {
        int value = 0;
        boolean isValid = false;

        //input until the value is valid
        while (!isValid) {
            System.out.print("Enter value: ");
            try {
                value = Integer.parseInt(scanner.nextLine());

                //check condition: value >= 0
                if (zeroOrPositive && (value < 0))
                    throw new NegativeException();
                else
                    isValid = true;

            } catch (NumberFormatException | NegativeException e) {     //input is not number or < 0
                System.out.println("Please enter a integer number greater than zero!");
            }
        }
        return value;
    }

    public long getLongInput(Scanner scanner, boolean zeroOrPositive /* value >= 0 flag */) {
        long value = 0;
        boolean isValid = false;

        //input until the value is valid
        while (!isValid) {
            System.out.print("Enter value: ");
            try {
                value = Long.parseLong(scanner.nextLine());

                //check condition: value >= 0
                if (zeroOrPositive && (value < 0))
                    throw new NegativeException();
                else
                    isValid = true;

            } catch (NumberFormatException | NegativeException e) {     //input is not number or < 0
                System.out.println("Please enter a long number greater than zero!");
            }
        }
        return value;
    }

    public BigDecimal getBigDecimalInput(Scanner scanner, boolean zeroOrPositive /* value >= 0 flag */) {
        BigDecimal value = null;
        boolean isValid = false;

        //input until the value is valid
        while (!isValid) {
            System.out.print("Enter value: ");
            try {
                value = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));

                //check condition: value >= 0
                if (zeroOrPositive && (value.compareTo(BigDecimal.ZERO) < 0))
                    throw new NegativeException();
                else
                    isValid = true;

            } catch (NumberFormatException | NegativeException e) {     //input is not number < 0
                System.out.println("Please enter a decimal number greater than zero!");
            }
        }
        return value;
    }
}
