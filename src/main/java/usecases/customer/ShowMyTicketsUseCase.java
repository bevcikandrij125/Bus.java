package usecases.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bean.Booking;
import dao.CustomerDao;
import dao.CustomerDaoImpl;
import exception.CustomerException;

public class ShowMyTicketsUseCase {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        CustomerDao dao = new CustomerDaoImpl();
        try {
            List<Booking> myBookings = dao.showTickets();
            if (myBookings.isEmpty()) {
                System.out.println("You have not ordered any tickets yet.");
                return;
            }
            printBookings(dao, myBookings);
            System.out.println("Select bookings using id's or type \"all\"");
            System.out.println("Example of input: 12 189 34");
            String inputLine = input.nextLine();
            System.out.println("See more - s\nCancel a ticket(s) - c");
            String option = input.next();
            if (inputLine.toLowerCase().contains("all")) {
                List<Integer> ids = extractIds(myBookings);
                performAction(dao, ids, option);
            } else {
                List<Integer> numberList = extractNumbers(inputLine);
                performAction(dao, numberList, option);
            }
        } catch (CustomerException e) {
            throw new RuntimeException(e);
        }
    }


    private static void printBookings(CustomerDao dao, List<Booking> bookings) {
        System.out.println("Your bookings: ");
        for (Booking b : bookings) {
            try {
                System.out.println(dao.shortBookingInfo(b.getId()));
            } catch (CustomerException e) {
                throw new RuntimeException(e);
            }
            System.out.println("******************************");
        }
    }

    private static void performAction(CustomerDao dao, List<Integer> ids, String option) {
        switch (option) {
            case "s":
                showDetailedBookingInfo(dao, ids);
                break;
            case "c":
                cancelBookings(dao, ids);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
    private static List<Integer> extractNumbers(String input) {
        String[] numbers = input.split(" ");
        List<Integer> numberList = new ArrayList<>();

        for (String number : numbers) {
            try {
                int num = Integer.parseInt(number);
                numberList.add(num);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number: " + number);
            }
        }

        return numberList;
    }

    private static void showDetailedBookingInfo(CustomerDao dao, List<Integer> bookingIds) {
        for (Integer id : bookingIds) {
            try {
                System.out.println(dao.detailedBookingInfo(id));
            } catch (CustomerException e) {
                throw new RuntimeException(e);
            }
            System.out.println("******************************");
        }
    }

    private static void cancelBookings(CustomerDao dao, List<Integer> bookingIds) {
        for (Integer id : bookingIds) {
            System.out.println(dao.cancelBooking(id));
            System.out.println("******************************");
        }
    }
    public static List<Integer> extractIds(List<Booking> bookings) {
        List<Integer> ids = new ArrayList<>();

        for (Booking booking : bookings) {
            ids.add(booking.getId());
        }

        return ids;
    }
}
