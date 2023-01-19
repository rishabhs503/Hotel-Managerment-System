package com.stackroute.hotelbookingservice.payloads;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class HotelDTO {
	private Integer id;
	private String name;
	private int noOfRooms;
	private List<RoomDto> rooms;

	private HotelContactDto contacts;
	private HotelAddressDto address;

}