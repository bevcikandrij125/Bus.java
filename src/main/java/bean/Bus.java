package bean;

public class Bus {

	private int id;
	private String brand;
	private int capacity;
	private int board_number;
	private int license_number;

	public Bus() {
		super();
	}

	public Bus(int id, String brand, int capacity, int board_number, int license_number) {
		super();
		this.id = id;
		this.brand = brand;
		this.capacity = capacity;
		this.board_number = board_number;
		this.license_number = license_number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getBoardNumber() {
		return board_number;
	}

	public void setBoardNumber(int board_number) {
		this.board_number = board_number;
	}

	public int getLicenseNumber() {
		return license_number;
	}

	public void setLicenseNumber(int license_number) {
		this.license_number = license_number;
	}
}
