package usecases.admin_only.show_all;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import bean.Booking;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.BookingException;

public class CreateBookingUseCase {

    public static void main(String[] args) {

        AdminDao dao = new AdminDaoImpl();

        try {
            List<Booking> bookings = dao.showBookings();
            Scanner input = new Scanner(System.in);
            System.out.println("Trip Id: ");
            int Tid = input.nextInt();
            System.out.println("User Id: ");
            int Uid = input.nextInt();
            System.out.println("Canceled: ");
            boolean canceled = input.nextBoolean();
            System.out.println("***************************************");
            dao.createBooking(new Booking(0,Tid, Uid, canceled));

        } catch (BookingException e) {

            System.out.println(e.getMessage());
        }
    }
}
