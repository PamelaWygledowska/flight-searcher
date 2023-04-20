package pl.lot.flightsearcher.controller;

import com.amadeus.resources.FlightOfferSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.lot.flightsearcher.dto.SearchCriteria;
import pl.lot.flightsearcher.service.AmadeusConnect;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class FlightSearcherController {

    private final AmadeusConnect amadeusConnect;

    private static List<FlightOfferSearch> lastOffers;

    @GetMapping("/")
    public String defaultPage(SearchCriteria searchCriteria) {
        return "home";
    }

    @GetMapping("/home")
    public String homePage(SearchCriteria searchCriteria) {
        return "home";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") String id, Model model) {
        model.addAttribute("flightOfferSearch", lastOffers.stream().filter(offer -> offer.getId().equals(id)).findAny().get());
        return "details";
    }

    @GetMapping("/searchFlight")
    public String searchFlight(SearchCriteria searchCriteria, Model model) {

        final List<FlightOfferSearch> offers = amadeusConnect.flightOffersSearch(
                searchCriteria.getDepartureAirport(),
                searchCriteria.getArrivalAirport(), searchCriteria.getDepartureDate(),
                searchCriteria.getArrivalDate(),
                searchCriteria.getNumberOfAdultPassengers(),
                searchCriteria.getNumberOfChildren(),
                searchCriteria.getNumberOfInfants(),
                searchCriteria.getMaxNumOfFlights());
        lastOffers = offers;
        model.addAttribute("offers", offers);

        return "flight-list";
    }

}
