package ru.kpfu.itis.Services;

import ru.kpfu.itis.Models.Cottage;

public interface CottageService {
    void updateDescription(String description, Long id);

    void updateAddress(String address, Long id);

    void addCottage(Cottage cottage);

}
