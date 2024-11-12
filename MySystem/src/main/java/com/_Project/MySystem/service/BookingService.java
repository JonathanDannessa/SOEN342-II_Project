package com._Project.MySystem.service;

import com._Project.MySystem.model.Booking;
import com._Project.MySystem.model.Client;
import com._Project.MySystem.model.Offering;
import com._Project.MySystem.repository.BookingRepository;
import com._Project.MySystem.repository.ClientRepository;
import com._Project.MySystem.repository.OfferingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final OfferingRepository offeringRepository;
    public Booking makeBooking(Long clientId, Long offeringId, LocalTime startTime, LocalTime endTime) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        Optional<Offering> offeringOptional = offeringRepository.findById(offeringId);

        if (clientOptional.isPresent() && offeringOptional.isPresent()) {
            Client client = clientOptional.get();
            Offering offering = offeringOptional.get();

            if (offering.getIsAvailable()) {
                Booking booking = new Booking();
                booking.setClient(client);
                booking.setOffering(offering);
                booking.setStartTime(startTime);
                booking.setEndTime(endTime);
                booking.setIsCancelled(false);

                //offering.setIsAvailable(false);

                Booking savedBooking = bookingRepository.save(booking);



                clientRepository.save(client);

                //offeringRepository.save(offering);

                return savedBooking;
            } else {
                throw new RuntimeException("Offering is not available");
            }
        } else {
            throw new RuntimeException("Client or Offering not found");
        }
    }


    public void cancelBooking(Long bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);

        if (bookingOptional.isPresent()) {
            Client client = bookingOptional.get().getClient();
            Booking booking = bookingOptional.get();
            booking.getOffering().setIsAvailable(true);
            client.getBookings().remove(booking);
            clientRepository.save(client);
            bookingRepository.delete(booking);
        } else {
            throw new RuntimeException("Booking not found");
        }
    }
}
