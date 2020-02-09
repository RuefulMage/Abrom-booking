package ru.kpfu.itis.Services;

import ru.kpfu.itis.Transfer.CottageDTO;
import ru.kpfu.itis.Models.Cottage;

public interface CottageService {
    void update(CottageDTO cottageDTO, Long id);

    void addCottage(String userName, CottageDTO cottageDTO);

    Cottage getCottageByID(Long id);

}
