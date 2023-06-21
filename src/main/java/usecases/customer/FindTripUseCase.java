package usecases.customer;

import java.util.List;
import java.util.Scanner;

import bean.Trip;
import dao.CustomerDao;
import dao.CustomerDaoImpl;
import exception.BusException;
import usecases.customer.BookTicketUseCase;

public class FindTripUseCase {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Enter Source location :");
        String start_city = input.nextLine();

        System.out.println("Enter Destination :");
        String target_city = input.nextLine();

        System.out.println("If a certain question is not important for you, enter \"-\"");
        System.out.println("The date format is YY-MM-DD :");
        System.out.println("Default 14 days - since now");
        System.out.println("Example of a date range: 23-05-28 23-05-30");
        System.out.println("Enter range of date :");
        String range0fDate = input.nextLine();

        System.out.println("Example of fare range: f <= 200");
        System.out.println("Enter range of fare :");
        String rangeOFare = input.nextLine();


        System.out.println("Sort by 1-time, 2-price, 3-date:");
        String sortCriterion = input.nextLine();

        CustomerDao dao = new CustomerDaoImpl();
        List<Trip> trips;
        try {
            trips = dao.findTrips(start_city, target_city, range0fDate, rangeOFare, sortCriterion);
            trips.forEach((Trip t) -> {

                System.out.println("Id : "+t.getId());
                System.out.println("Travel time : from "+ t.getStartTime() + " to " + t.getEndTime());
                System.out.println("Fare : " + t.getFare());
                System.out.println("Free places : " + CustomerDaoImpl.numberOfFreePlaces(t.getId()));
                System.out.println("***************************************");
            });

        } catch (BusException e) {
            throw new RuntimeException(e);
        }
        if(trips.size() > 0){
            System.out.println("Want to book?(y/n)");
            if(input.nextLine().contains("y")){
                new BookTicketUseCase().main(null);
            }
        }else{
            System.out.println("Unfortunately, there are no such trips now");
        }
    }

}
