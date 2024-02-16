package com.rct.humanresources.infra.config;

import com.rct.humanresources.infra.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.rct.humanresources.core.util.VariableConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Router WebFLux Configuration
 */
@Configuration
public class RouterConfig {

    /**
     * Routes Configuration - Employer
     * @param handler EmployerHandler
     * @return RouterFunction ServerResponse
     */
    @Bean
    public RouterFunction<ServerResponse> employerRoutes(EmployerHandler handler) {
        return route(GET(HANDLER_EMPLOYERS_PATH).and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(GET(HANDLER_EMPLOYERS_BY_DEPARTMENT_ID_PATH).and(accept(APPLICATION_JSON)), handler::findByDepartmentId)
                .andRoute(GET(HANDLER_EMPLOYERS_BY_JOB_ID_PATH).and(accept(APPLICATION_JSON)), handler::findByJobId)
                .andRoute(GET(HANDLER_EMPLOYERS_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::findById)
                .andRoute(GET(HANDLER_EMPLOYERS_SEARCH_PATH).and(contentType(APPLICATION_JSON)), handler::search)
                .andRoute(GET(HANDLER_EMPLOYERS_STREAM_PATH).and(contentType(APPLICATION_JSON)), handler::stream)
                .andRoute(POST(HANDLER_EMPLOYERS_PATH).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(PUT(HANDLER_EMPLOYERS_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::updateById)
                .andRoute(DELETE(HANDLER_EMPLOYERS_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::deleteById);
    }

    /**
     * Routes Configuration - Department
     * @param handler DepartmentHandler
     * @return RouterFunction ServerResponse
     */
    @Bean
    public RouterFunction<ServerResponse> departmentRoutes(DepartmentHandler handler) {
        return route(GET(HANDLER_DEPARTMENTS_PATH).and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(GET(HANDLER_DEPARTMENTS_PATH_BY_LOCATION_ID).and(accept(APPLICATION_JSON)), handler::findByLocationId)
                .andRoute(GET(HANDLER_DEPARTMENTS_PATH_BY_MANAGER_ID).and(accept(APPLICATION_JSON)), handler::findByManagerId)
                .andRoute(GET(HANDLER_DEPARTMENTS_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::findById)
                .andRoute(GET(HANDLER_DEPARTMENTS_SEARCH_PATH).and(contentType(APPLICATION_JSON)), handler::search)
                .andRoute(GET(HANDLER_DEPARTMENTS_STREAM_PATH).and(contentType(APPLICATION_JSON)), handler::stream)
                .andRoute(POST(HANDLER_DEPARTMENTS_PATH).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(PUT(HANDLER_DEPARTMENTS_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::updateById)
                .andRoute(DELETE(HANDLER_DEPARTMENTS_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::deleteById);
    }

    /**
     * Routes Configuration - State
     * @param handler StateHandler
     * @return RouterFunction ServerResponse
     */
    @Bean
    public RouterFunction<ServerResponse> stateRoutes(StateHandler handler) {
        return route(GET(HANDLER_STATES_PATH).and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(GET(HANDLER_STATES_PATH_BY_COUNTRY_ID).and(accept(APPLICATION_JSON)), handler::findByCountryId)
                .andRoute(GET(HANDLER_STATES_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::findById)
                .andRoute(GET(HANDLER_STATES_SEARCH_PATH).and(contentType(APPLICATION_JSON)), handler::search)
                .andRoute(GET(HANDLER_STATES_STREAM_PATH).and(contentType(APPLICATION_JSON)), handler::stream)
                .andRoute(POST(HANDLER_STATES_PATH).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(PUT(HANDLER_STATES_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::updateById)
                .andRoute(DELETE(HANDLER_STATES_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::deleteById);
    }

    /**
     * Routes Configuration - Country
     * @param handler CountryHandler
     * @return RouterFunction ServerResponse
     */
    @Bean
    public RouterFunction<ServerResponse> countryRoutes(CountryHandler handler) {
        return route(GET(HANDLER_COUNTRIES_PATH).and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(GET(HANDLER_COUNTRIES_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::findById)
                .andRoute(POST(HANDLER_COUNTRIES_PATH).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(PUT(HANDLER_COUNTRIES_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::updateById)
                .andRoute(DELETE(HANDLER_COUNTRIES_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::deleteById);
    }

    /**
     * Routes Configuration - City
     * @param handler CityHandler
     * @return RouterFunction ServerResponse
     */
    @Bean
    public RouterFunction<ServerResponse> cityRoutes(CityHandler handler) {
        return route(GET(HANDLER_CITIES_PATH).and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(GET(HANDLER_CITIES_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::findById)
                .andRoute(GET(HANDLER_CITIES_PATH_BY_STATES_ID).and(contentType(APPLICATION_JSON)), handler::findByStateId)
                .andRoute(GET(HANDLER_COUNTRIES_SEARCH_PATH).and(contentType(APPLICATION_JSON)), handler::search)
                .andRoute(GET(HANDLER_COUNTRIES_STREAM_PATH).and(contentType(APPLICATION_JSON)), handler::stream)
                .andRoute(POST(HANDLER_CITIES_PATH).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(PUT(HANDLER_CITIES_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::updateById)
                .andRoute(DELETE(HANDLER_CITIES_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::deleteById);
    }

    /**
     * Routes Configuration - Location
     * @param handler LocationHandler
     * @return RouterFunction ServerResponse
     */
    @Bean
    public RouterFunction<ServerResponse> locationRoutes(LocationHandler handler) {
        return route(GET(HANDLER_LOCATIONS_PATH).and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(GET(HANDLER_LOCATIONS_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::findById)
                .andRoute(GET(HANDLER_LOCATIONS_SEARCH_PATH).and(contentType(APPLICATION_JSON)), handler::search)
                .andRoute(GET(HANDLER_LOCATIONS_STREAM_PATH).and(contentType(APPLICATION_JSON)), handler::stream)
                .andRoute(POST(HANDLER_LOCATIONS_PATH).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(PUT(HANDLER_LOCATIONS_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::updateById)
                .andRoute(DELETE(HANDLER_LOCATIONS_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::deleteById);
    }

    /**
     * Routes Configuration - Job
     * @param handler JobHandler
     * @return RouterFunction ServerResponse
     */
    @Bean
    public RouterFunction<ServerResponse> jobRoutes(JobHandler handler) {
        return route(GET(HANDLER_JOBS_PATH).and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(GET(HANDLER_JOBS_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::findById)
                .andRoute(GET(HANDLER_JOBS_SEARCH_PATH).and(contentType(APPLICATION_JSON)), handler::search)
                .andRoute(GET(HANDLER_JOBS_STREAM_PATH).and(contentType(APPLICATION_JSON)), handler::stream)
                .andRoute(POST(HANDLER_JOBS_PATH).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(PUT(HANDLER_JOBS_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::updateById)
                .andRoute(DELETE(HANDLER_JOBS_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::deleteById);
    }

    /**
     * Routes Configuration - Job History
     * @param handler JobHistoryHandler
     * @return RouterFunction ServerResponse
     */
    @Bean
    public RouterFunction<ServerResponse> jobHistoryRoutes(JobHistoryHandler handler) {
        return route(GET(HANDLER_JOB_HISTORIES_PATH).and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(GET(HANDLER_JOB_HISTORIES_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::findById)
                .andRoute(GET(HANDLER_JOB_HISTORIES_PATH_BY_DEPARTMENT_ID).and(contentType(APPLICATION_JSON)), handler::findByDepartmentId)
                .andRoute(GET(HANDLER_JOB_HISTORIES_PATH_BY_JOB_ID).and(contentType(APPLICATION_JSON)), handler::findByDepartmentId)
                .andRoute(GET(HANDLER_JOB_HISTORIES_STREAM_PATH).and(contentType(APPLICATION_JSON)), handler::stream)
                .andRoute(POST(HANDLER_JOB_HISTORIES_PATH).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(PUT(HANDLER_JOB_HISTORIES_PATH_BY_ID).and(contentType(APPLICATION_JSON)), handler::updateById)
                .andRoute(DELETE(HANDLER_JOB_HISTORIES_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::deleteById);
    }

}
