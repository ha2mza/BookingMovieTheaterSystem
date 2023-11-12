package app.booking.movietheater.threads;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import app.booking.movietheater.services.SessionService;

public class UpdateSessionStateRunnable  implements Runnable {

	SessionService sessionService = SessionService.getInstance();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				ZoneId defaultZoneId = ZoneId.systemDefault();
				sessionService.updateSessions(Date.from(LocalDate.now().atStartOfDay(defaultZoneId).toInstant()));
				
				Thread.sleep(1000*60); // check session stated every minute
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
