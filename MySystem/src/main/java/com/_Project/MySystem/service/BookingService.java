package com._Project.MySystem.service;

import com._Project.MySystem.model.Booking;
import com._Project.MySystem.model.Client;
import com._Project.MySystem.model.Offering;
import com._Project.MySystem.repository.BookingRepository;
import com._Project.MySystem.repository.ClientRepository;
import com._Project.MySystem.repository.OfferingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

                offering.setIsAvailable(false);

                return bookingRepository.save(booking);
            } else {
                throw new RuntimeException("Offering is not available");
            }
        } else {
            throw new RuntimeException("Client or Offering not found");
        }
    }

    public List<Booking> getClientBookings(Long clientId) {
        return bookingRepository.findByClient_ClientId(clientId);
    }

    public void cancelBooking(UUID bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);

        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            booking.setIsCancelled(true);
            booking.getOffering().setIsAvailable(true);

            bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Booking not found");
        }
    }
}
