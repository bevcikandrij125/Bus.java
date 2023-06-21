package usecases.admin_only.show_all;

import java.util.Iterator;
import java.util.List;

import bean.Bus;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.BusException;

public class showBusUseCase {

	public static void main(String[] args) {

		AdminDao dao = new AdminDaoImpl();
		
		try {
			List<Bus> buses = dao.showBuses();
			
			Iterator<Bus> itr = buses.iterator();
			
			buses.forEach((Bus b) -> {

				System.out.println("Bus Id : "+b.getId());
				System.out.println("Bus brand name : "+b.getBrand());
				System.out.println("Bus capacity : "+b.getCapacity());
				System.out.println("Bus board number : "+b.getBoardNumber());
				System.out.println("Bus license number : "+b.getLicenseNumber());
				
				System.out.println("***************************************");
			});
			
		} catch (BusException e) {
			
			System.out.println(e.getMessage());
		}
	}

}
