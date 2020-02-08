package ru.kpfu.itis.Services;

import ru.kpfu.itis.Forms.CottageForm;
import ru.kpfu.itis.Models.Cottage;

public interface CottageService {
    void update(CottageForm cottageForm, Long id);

    void addCottage(String userName, CottageForm cottageForm);

    Cottage getCottageByID(Long id);

}
