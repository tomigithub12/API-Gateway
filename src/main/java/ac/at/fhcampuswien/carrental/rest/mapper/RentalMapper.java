package ac.at.fhcampuswien.carrental.rest.mapper;


import ac.at.fhcampuswien.carrental.entity.models.Rental;
import ac.at.fhcampuswien.carrental.rest.models.RentalRequestDto;
import ac.at.fhcampuswien.carrental.rest.models.RentalResponseDto;
import ac.at.fhcampuswien.carrental.rest.models.RentalUpdateResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public class RentalMapper {

    public Rental BookingRequestToRental(RentalRequestDto rentalBooking, Long id){
        return Rental.builder()
                .customerId(id)
                .carId(rentalBooking.getCarId())
                .startDay(rentalBooking.getStartDay())
                .endDay(rentalBooking.getEndDay())
                .totalCost(rentalBooking.getTotalCost())
                .build();
    }

    public RentalUpdateResponseDto RentalToUpdateResponse(Rental rentalUpdate) {
        return RentalUpdateResponseDto.builder()
                .id(rentalUpdate.getId())
                .carId(rentalUpdate.getCarId())
                .startDay(rentalUpdate.getStartDay())
                .endDay(rentalUpdate.getEndDay())
                .totalCost(rentalUpdate.getTotalCost())
                .build();
    }
}
