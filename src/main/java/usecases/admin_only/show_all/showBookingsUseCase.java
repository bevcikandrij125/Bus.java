package usecases.admin_only.show_all;

import java.util.Iterator;
import java.util.List;

import bean.Booking;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.BookingException;

public class showBookingsUseCase {

    public static void main(String[] args) {

        AdminDao dao = new AdminDaoImpl();

        try {
            List<Booking> bookings = dao.showBookings();

            Iterator<Booking> itr = bookings.iterator();

            bookings.forEach((Booking b) -> {

                System.out.println("Booking Id: " + b.getId());
                System.out.println("Trip Id: " + b.getTripId());
                System.out.println("User Id: " + b.getUserId());
                System.out.println("Status: " + (b.isCanceled() ? "Canceled" : "Active"));

                System.out.println("***************************************");
            });

        } catch (BookingException e) {

            System.out.println(e.getMessage());
        }
    }
}
