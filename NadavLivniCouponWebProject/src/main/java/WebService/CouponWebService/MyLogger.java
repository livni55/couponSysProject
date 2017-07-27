package WebService.CouponWebService;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import e.Facades.AdminFacade;

public class MyLogger {

	public static final Logger logger = Logger.getLogger(AdminFacade.class.getName());
	{
		ConsoleHandler cHandler = new ConsoleHandler();
		cHandler.setLevel(Level.INFO);
		logger.addHandler(cHandler);
		logger.setLevel(Level.INFO);
		FileHandler fHandler;
		try {
			fHandler = new FileHandler("/myLogFile.txt");
			fHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fHandler);
		} catch (SecurityException | IOException e1) {
			e1.getMessage();
		}
	}

}
