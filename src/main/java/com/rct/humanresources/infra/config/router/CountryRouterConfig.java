package com.rct.humanresources.infra.config.router;

import com.rct.humanresources.core.model.dto.CountryDTO;
import com.rct.humanresources.infra.handler.CountryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.jetbrains.annotations.NotNull;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_COUNTRIES_PATH;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_COUNTRIES_PATH_BY_ID;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_COUNTRIES_SEARCH_PATH;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_COUNTRIES_STREAM_PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CountryRouterConfig {
    @Bean
    @RouterOperations({
            @RouterOperation(path = HANDLER_COUNTRIES_PATH, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    CountryHandler.class, method = GET, beanMethod = "findAll", operation =
            @Operation(operationId = "findAll", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = CountryDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Country details supplied")})),

            @RouterOperation(path = HANDLER_COUNTRIES_PATH_BY_ID, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    CountryHandler.class, method = GET, beanMethod = "findById", operation =
            @Operation(operationId = "findById", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = CountryDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Country details supplied"),
                    @ApiResponse(responseCode = "404", description = "Country not found")}, parameters = {@Parameter(in = PATH, name = "id")})),

            @RouterOperation(path = HANDLER_COUNTRIES_SEARCH_PATH, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    CountryHandler.class, method = GET, beanMethod = "search", operation =
            @Operation(operationId = "search", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = CountryDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Country details supplied"),
                    @ApiResponse(responseCode = "404", description = "Country not found")}, parameters = {
                    @Parameter(in = QUERY, name = "name")})),

            @RouterOperation(path = HANDLER_COUNTRIES_STREAM_PATH, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    CountryHandler.class, method = GET, beanMethod = "stream", operation =
            @Operation(operationId = "stream", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = CountryDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Country details supplied")})),


            @RouterOperation(path = HANDLER_COUNTRIES_PATH, produces = {APPLICATION_JSON_VALUE}, method = PUT, beanClass =
                    CountryHandler.class, beanMethod = "updateById", operation =
            @Operation(operationId = "updateById", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = CountryDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Country ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Country not found")}, parameters = {
                    @Parameter(in = PATH, name = "id")}, requestBody =
            @RequestBody(content = @Content(schema = @Schema(implementation = CountryDTO.class))))),

            @RouterOperation(path = HANDLER_COUNTRIES_PATH, produces = {APPLICATION_JSON_VALUE}, method = POST, beanClass =
                    CountryHandler.class, beanMethod = "create", operation =
            @Operation(operationId = "create", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = CountryDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Country details supplied")}, requestBody =
            @RequestBody(content = @Content(schema = @Schema(implementation = CountryDTO.class))))),

            @RouterOperation(path = HANDLER_COUNTRIES_PATH_BY_ID, produces = {APPLICATION_JSON_VALUE}, method = DELETE, beanClass =
                    CountryHandler.class, beanMethod = "deleteById", operation =
            @Operation(operationId = "deleteById", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(mediaType = "Boolean")), @ApiResponse(responseCode = "400", description = "Invalid Country ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Country not found")}, parameters = {
                    @Parameter(in = PATH, name = "id")}))})

    /*
     * Routes Configuration - Country
     *
     * @param handler CountryHandler
     *
     * @return RouterFunction ServerResponse
     */
    @NotNull
    public RouterFunction<ServerResponse> countriesRoutes(CountryHandler handler) {
        return route(RequestPredicates.GET(HANDLER_COUNTRIES_PATH).and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(RequestPredicates.GET(HANDLER_COUNTRIES_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::findById)
                .andRoute(RequestPredicates.GET(HANDLER_COUNTRIES_SEARCH_PATH).and(accept(APPLICATION_JSON)), handler::search)
                .andRoute(RequestPredicates.GET(HANDLER_COUNTRIES_STREAM_PATH).and(accept(APPLICATION_JSON)), handler::stream)
                .andRoute(RequestPredicates.POST(HANDLER_COUNTRIES_PATH).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(RequestPredicates.PUT(HANDLER_COUNTRIES_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::updateById)
                .andRoute(RequestPredicates.DELETE(HANDLER_COUNTRIES_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::deleteById);
    }
}
