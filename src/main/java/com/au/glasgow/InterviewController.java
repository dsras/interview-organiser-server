package com.au.glasgow;

import org.springframework.web.bind.annotation.RestController;

/*
interview controller
handles interview-related requests
 */
@RestController
public class InterviewController {
    /*
    requests:
    GET:
    - interview
        - interviewer(s)
        - candidate
        - skills evaluated
        - date/time
        - confirmed (y/n)

    POST:
    - create interview

    PUT:
    - book interviewer (this is adding interviewer to interview)
    - confirm interview

    extra:
    PUT:
    - update interview requirements (adding another skill to be evaluated)
    - update interview date/time

    nb: no deletion of scheduled interviews as we need to track panel/candidate no shows
    */
}
