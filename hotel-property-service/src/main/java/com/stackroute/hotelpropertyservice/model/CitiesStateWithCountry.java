package com.stackroute.hotelpropertyservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CitiesStateWithCountry {

        private String city;
        private String lat;
        private String lng;
        private String country;
        private String iso2;
        private String admin_name;
        private String capital;
        private String population;
        private String population_proper;
}
