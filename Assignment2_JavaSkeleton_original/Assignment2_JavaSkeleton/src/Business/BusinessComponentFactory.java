package Business;


public class BusinessComponentFactory {

	private static  BusinessComponentFactory sFactory = new BusinessComponentFactory();
	
	public static BusinessComponentFactory getInstance()
	{
		return sFactory;
	}
	
	public IBookingProvider getBookingProvider()
	{
		return new BookingProvider();
	}
}
