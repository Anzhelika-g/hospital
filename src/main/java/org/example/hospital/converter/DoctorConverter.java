package org.example.hospital.converter;

import org.example.hospital.dto.DoctorDTO;
import org.example.hospital.entity.Doctor;
import org.springframework.stereotype.Component;

public class  {
}

@Component
public class DoctorConverter implements Converter<Doctor, DoctorDTO>{
    @Override
    public Doctor convertToEntity(DoctorDTO model, Doctor entity) {
        entity.setDoctorId(model.getDoctorId());
        entity.setName(model.getName());
        entity.setRoomNumber(model.getRoomNumber());
        entity.setPhoneNumber(model.getPhoneNumber());
        entity.setInfo(model.getInfo());
        entity
        return entity;
    }

    @Override
    public DoctorDTO convertToModel(Doctor entity, DoctorDTO model) {

        return model;
    }
}
