package dao.AdminsFirstChoiceInt;

import bean.Bus;
import bean.Trip;
import bean.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface See_satistics {
    List<Bus> getBusesForRouteInDate(int routeId, LocalDate date);

    List<Trip> getCanceledTrips();

    int getTotalCanceledTrips();

    int getTotalDelayedTrips();

    int getTotalTicketsReturnedForDelays();

    List<User> getPassengersOnCompletedTrip(int tripId);

    int getDailyPassengerCount(LocalDate date);

    int getDailyRevenue(LocalDate date);

    int getMonthlyPassengerCount(LocalDate date);

    int getMonthlyRevenue(LocalDate date);

    int getPassengerCountForRoute(int routeId);

    int getRevenueForRoute(int routeId);

    int getTicketCountForRouteInTimeRange(int routeId, LocalDateTime startTime, LocalDateTime endTime);

    double getAverageTicketPriceForRouteInTimeRange(int routeId, LocalDateTime startTime, LocalDateTime endTime);

    int getTicketCountForRoute(int routeId);

    double getAverageTicketPriceForRoute(int routeId);

    int getSoldTicketsOnDate(LocalDate date);

    int getUnsoldTicketsOnDate(LocalDate date);

    int getReturnedTicketsOnDate(LocalDate date);

    int getSoldTicketsOnRoute(int routeId);

    int getUnsoldTicketsOnRoute(int routeId);

    int getReturnedTicketsOnRoute(int routeId);

    int getSoldTicketsOnTrip(int tripId);

    int getUnsoldTicketsOnTrip(int tripId);

    int getReturnedTicketsOnTrip(int tripId);

    boolean hasTicketWithLastName(String lastName);

    Trip getTripByPassengerLastName(String lastName);

    int getPassengerCountOnTrip(int tripId);

    int getTripRevenue(int tripId);
}
