package pl.lab.znk.service.mapper;

import pl.lab.znk.domain.*;
import pl.lab.znk.service.dto.ConsultationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Consultation and its DTO ConsultationDTO.
 */

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ConsultationMapper {

    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "teacher.login", target = "teacherLogin")
    ConsultationDTO consultationToConsultationDTO(Consultation consultation);

    List<ConsultationDTO> consultationsToConsultationDTOs(List<Consultation> consultations);

    @Mapping(source = "teacherId", target = "teacher")
    Consultation consultationDTOToConsultation(ConsultationDTO consultationDTO);

    List<Consultation> consultationDTOsToConsultations(List<ConsultationDTO> consultationDTOs);
}
