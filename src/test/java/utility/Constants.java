package utility;

import com.accolite.intervieworganiser.entities.Interview;
import com.accolite.intervieworganiser.entities.User;
import com.accolite.intervieworganiser.entities.UserAvailability;

import java.time.LocalDate;
import java.time.LocalTime;

public final class Constants {

    private Constants() {
        // restrict instantiation
    }

    public static final LocalDate startDate = LocalDate.of(2022, 5, 3);
    public static final LocalDate endDate = LocalDate.of(2022, 5, 4);
    public static final LocalTime startTime = LocalTime.of(10, 0);
    public static final LocalTime endTime = LocalTime.of(11, 0);

    public static final String interviewerUsername = "interviewer@accolitedigital.com";
    public static final String recruiterUsername = "recruiter@accolitedigital.com";
    public static final String password = "testPassword";
    public static final String email = "email@email.com";
    public static final String interviewerName = "Interviewer";
    public static final String recruiterName = "Recruiter";
    public static final String interviewerBusTitle = "Accolite Interviewer";
    public static final String recruiterBusTitle = "Accolite Recruiter";

    public static final User interviewer = new User(
            interviewerUsername,
            password,
            email,
            interviewerName,
            interviewerBusTitle
    );
    public static final User recruiter = new User(
            recruiterUsername,
            password,
            email,
            recruiterName,
            recruiterBusTitle
    );

    public static final UserAvailability availability1 = new UserAvailability(
            interviewer,
            startDate,
            startTime,
            endTime
    );
    public static final UserAvailability availability2 = new UserAvailability(
            interviewer,
            endDate,
            startTime,
            endTime
    );

    public static final Interview interview = new Interview(
            recruiter,
            startDate,
            startTime,
            endTime,
            " ",
            "Pending",
            "Awaiting Completion"
    );

}
