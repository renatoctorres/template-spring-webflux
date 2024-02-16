package com.rct.humanresources.core.util;

/**
 * Variable Constants
 */
public class VariableConstants {

    /**
     * Handlers - Employers - Context Path
     */
    public static final String HANDLER_EMPLOYERS_PATH = "/handler/employers";
    public static final String HANDLER_EMPLOYERS_BY_DEPARTMENT_ID_PATH = "/handler/employers/department/{departmentId}";
    public static final String HANDLER_EMPLOYERS_BY_JOB_ID_PATH = "/handler/employers/jobs/{jobId}";
    public static final String HANDLER_EMPLOYERS_PATH_BY_ID = "/handler/employers/{id}";
    public static final String HANDLER_EMPLOYERS_SEARCH_PATH = "/handler/employers/search";
    public static final String HANDLER_EMPLOYERS_STREAM_PATH = "/handler/employers/stream";

    /**
     * Handlers - Departments - Context Path
     */
    public static final String HANDLER_DEPARTMENTS_PATH = "/handler/departments";
    public static final String HANDLER_DEPARTMENTS_PATH_BY_ID = "/handler/departments/{id}";
    public static final String HANDLER_DEPARTMENTS_PATH_BY_MANAGER_ID = "/handler/departments/managers/{managerId}";
    public static final String HANDLER_DEPARTMENTS_PATH_BY_LOCATION_ID = "/handler/departments/locations/{locationId}";
    public static final String HANDLER_DEPARTMENTS_SEARCH_PATH = "/handler/departments/search";
    public static final String HANDLER_DEPARTMENTS_STREAM_PATH = "/handler/departments/stream";

    /**
     * Handlers - Jobs - Context Path
     */
    public static final String HANDLER_JOBS_PATH = "/handler/jobs";
    public static final String HANDLER_JOBS_PATH_BY_ID = "/handler/jobs/{id}";
    public static final String HANDLER_JOBS_SEARCH_PATH = "/handler/jobs/search";
    public static final String HANDLER_JOBS_STREAM_PATH = "/handler/jobs/stream";

    /**
     * Handlers - Jobs Histories - Context Path
     */
    public static final String HANDLER_JOB_HISTORIES_PATH = "/handler/jobs/histories";
    public static final String HANDLER_JOB_HISTORIES_PATH_BY_ID = "/handler/jobs/histories/{id}";
    public static final String HANDLER_JOB_HISTORIES_PATH_BY_JOB_ID = "/handler/jobs/histories/jobs/{jobId}";
    public static final String HANDLER_JOB_HISTORIES_PATH_BY_DEPARTMENT_ID = "/handler/jobs/histories/departments/{departmentId}";
    public static final String HANDLER_JOB_HISTORIES_SEARCH_PATH = "/handler/jobs/histories/search";
    public static final String HANDLER_JOB_HISTORIES_STREAM_PATH = "/handler/jobs/histories/stream";

    /**
     * Handlers - Locations - Context Path
     */
    public static final String HANDLER_LOCATIONS_PATH = "/handler/locations";
    public static final String HANDLER_LOCATIONS_PATH_BY_ID = "/handler/locations/{id}";
    public static final String HANDLER_LOCATIONS_SEARCH_PATH = "/handler/locations/search";
    public static final String HANDLER_LOCATIONS_STREAM_PATH = "/handler/locations/stream";

    /**
     * Handlers - Countries - Context Path
     */
    public static final String HANDLER_COUNTRIES_PATH = "/handler/countries";
    public static final String HANDLER_COUNTRIES_PATH_BY_ID = "/handler/countries/{id}";
    public static final String HANDLER_COUNTRIES_SEARCH_PATH = "/handler/countries/search";
    public static final String HANDLER_COUNTRIES_STREAM_PATH = "/handler/countries/stream";

    /**
     * Handlers - States - Context Path
     */
    public static final String HANDLER_STATES_PATH = "/handler/states";
    public static final String HANDLER_STATES_PATH_BY_ID = "/handler/states/{id}";
    public static final String HANDLER_STATES_PATH_BY_COUNTRY_ID = "/handler/states/countries/{countryId}";
    public static final String HANDLER_STATES_SEARCH_PATH = "/handler/states/search";
    public static final String HANDLER_STATES_STREAM_PATH = "/handler/states/stream";

    /**
     * Handlers - Cities - Context Path
     */
    public static final String HANDLER_CITIES_PATH = "/handler/cities";
    public static final String HANDLER_CITIES_PATH_BY_ID = "/handler/cities/{id}";
    public static final String HANDLER_CITIES_PATH_BY_STATES_ID = "/handler/cities/states/{stateId}";
    public static final String HANDLER_CITIES_SEARCH_PATH = "/handler/cities/search";
    public static final String HANDLER_CITIES_STREAM_PATH = "/handler/cities/stream";

    /**
     * Handlers Constructor
     */
    private VariableConstants() {
        throw new IllegalStateException("Utility class");
    }
}
