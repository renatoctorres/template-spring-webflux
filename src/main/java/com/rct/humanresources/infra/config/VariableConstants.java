package com.rct.humanresources.infra.config;

/**
 * Variable Constants
 */
public class VariableConstants {
    public static final String HANDLER = "/handler";
    public static final String BY_ID = "/{id}";
    public static final String SEARCH = "/search";
    public static final String STREAM = "/stream";

    /**
     * Handlers - Employees - Context Path
     */
    public static final String HANDLER_EMPLOYEES_PATH = HANDLER + "/employees";
    public static final String HANDLER_EMPLOYEES_BY_DEPARTMENT_ID_PATH = HANDLER_EMPLOYEES_PATH + "/departments/{departmentId}";
    public static final String HANDLER_EMPLOYEES_BY_JOB_ID_PATH = HANDLER_EMPLOYEES_PATH + "/jobs/{jobId}";
    public static final String HANDLER_EMPLOYEES_PATH_BY_ID = HANDLER_EMPLOYEES_PATH + BY_ID;
    public static final String HANDLER_EMPLOYEES_SEARCH_PATH = HANDLER_EMPLOYEES_PATH + SEARCH;
    public static final String HANDLER_EMPLOYEES_STREAM_PATH = HANDLER_EMPLOYEES_PATH + STREAM;

    /**
     * Handlers - Departments - Context Path
     */
    public static final String HANDLER_DEPARTMENTS_PATH = HANDLER + "/departments";
    public static final String HANDLER_DEPARTMENTS_PATH_BY_ID = HANDLER_DEPARTMENTS_PATH + BY_ID;
    public static final String HANDLER_DEPARTMENTS_PATH_BY_MANAGER_ID = HANDLER_DEPARTMENTS_PATH + "/managers/{managerId}";
    public static final String HANDLER_DEPARTMENTS_PATH_BY_LOCATION_ID = HANDLER_DEPARTMENTS_PATH + "/locations/{locationId}";
    public static final String HANDLER_DEPARTMENTS_SEARCH_PATH = HANDLER_DEPARTMENTS_PATH + SEARCH;
    public static final String HANDLER_DEPARTMENTS_STREAM_PATH = HANDLER_DEPARTMENTS_PATH + STREAM;

    /**
     * Handlers - Jobs - Context Path
     */
    public static final String HANDLER_JOBS_PATH = HANDLER + "/jobs";
    public static final String HANDLER_JOBS_PATH_BY_ID = HANDLER_JOBS_PATH + BY_ID;
    public static final String HANDLER_JOBS_SEARCH_PATH = HANDLER_JOBS_PATH + SEARCH;
    public static final String HANDLER_JOBS_STREAM_PATH = HANDLER_JOBS_PATH + STREAM;

    /**
     * Handlers - Jobs Histories - Context Path
     */
    public static final String HANDLER_JOB_HISTORIES_PATH = HANDLER_JOBS_PATH + "/histories";
    public static final String HANDLER_JOB_HISTORIES_PATH_BY_ID = HANDLER_JOB_HISTORIES_PATH + BY_ID;
    public static final String HANDLER_JOB_HISTORIES_PATH_BY_JOB_ID =  HANDLER_JOB_HISTORIES_PATH + "/jobs/{jobId}";
    public static final String HANDLER_JOB_HISTORIES_PATH_BY_DEPARTMENT_ID =  HANDLER_JOB_HISTORIES_PATH + "/departments/{departmentId}";
    public static final String HANDLER_JOB_HISTORIES_SEARCH_PATH =  HANDLER_JOB_HISTORIES_PATH + SEARCH;
    public static final String HANDLER_JOB_HISTORIES_STREAM_PATH = HANDLER_JOB_HISTORIES_PATH + STREAM;

    /**
     * Handlers - Locations - Context Path
     */
    public static final String HANDLER_LOCATIONS_PATH = HANDLER + "/locations";
    public static final String HANDLER_LOCATIONS_PATH_BY_ID = HANDLER_LOCATIONS_PATH + BY_ID;
    public static final String HANDLER_LOCATIONS_PATH_BY_CITY_ID = HANDLER_LOCATIONS_PATH + "/cities/{cityId}";
    public static final String HANDLER_LOCATIONS_SEARCH_PATH = HANDLER_LOCATIONS_PATH + SEARCH;
    public static final String HANDLER_LOCATIONS_STREAM_PATH = HANDLER_LOCATIONS_PATH + STREAM;

    /**
     * Handlers - Countries - Context Path
     */
    public static final String HANDLER_COUNTRIES_PATH = HANDLER + "/countries";
    public static final String HANDLER_COUNTRIES_PATH_BY_ID = HANDLER_COUNTRIES_PATH + BY_ID;
    public static final String HANDLER_COUNTRIES_SEARCH_PATH = HANDLER_COUNTRIES_PATH + SEARCH;
    public static final String HANDLER_COUNTRIES_STREAM_PATH = HANDLER_COUNTRIES_PATH + STREAM;

    /**
     * Handlers - States - Context Path
     */
    public static final String HANDLER_STATES_PATH = HANDLER + "/states";
    public static final String HANDLER_STATES_PATH_BY_ID = HANDLER_STATES_PATH + BY_ID;
    public static final String HANDLER_STATES_PATH_BY_COUNTRY_ID = HANDLER_STATES_PATH + "/countries/{countryId}";
    public static final String HANDLER_STATES_SEARCH_PATH = HANDLER_STATES_PATH + SEARCH;
    public static final String HANDLER_STATES_STREAM_PATH = HANDLER_STATES_PATH + STREAM;

    /**
     * Handlers - Cities - Context Path
     */
    public static final String HANDLER_CITIES_PATH = HANDLER + "/cities";
    public static final String HANDLER_CITIES_PATH_BY_ID = HANDLER_CITIES_PATH + BY_ID;
    public static final String HANDLER_CITIES_PATH_BY_STATE_ID = HANDLER_CITIES_PATH + "/states/{stateId}";
    public static final String HANDLER_CITIES_SEARCH_PATH = HANDLER_CITIES_PATH + SEARCH;
    public static final String HANDLER_CITIES_STREAM_PATH = HANDLER_CITIES_PATH + STREAM;

    /**
     * Handlers Constructor
     */
    private VariableConstants() {
        throw new IllegalStateException("Utility class");
    }
}