package pl.lot.flightsearcher.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.resources.FlightOfferSearch;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class AmadeusConnect {

    private static Amadeus amadeus;

    public AmadeusConnect(@Value(value = "${amadeus.client-id}") String clientId,
                          @Value(value = "${amadeus.client-secret}") String clientSecret) {
        amadeus = Amadeus
                .builder(clientId, clientSecret)
                .build();
    }

    @SneakyThrows
    public List<FlightOfferSearch> flightOffersSearch(String departureAirport,
                                                      String arrivalAirport,
                                                      LocalDate departureDate,
                                                      LocalDate arrivalDate,
                                                      int numberOfAdultPassengers,
                                                      int numberOfChildren,
                                                      int numberOfInfants,
                                                      int maxNumOfFlights) {
        return Arrays.asList(amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", departureAirport)
                        .and("destinationLocationCode", arrivalAirport)
                        .and("departureDate", departureDate)
                        .and("returnDate", arrivalDate)
                        .and("adults", numberOfAdultPassengers)
                        .and("children", numberOfChildren)
                        .and("infants", numberOfInfants)
                        .and("max", maxNumOfFlights)
        ));
    }

}
