package pl.lab.znk.service.mapper;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import pl.lab.znk.domain.Consultation;
import pl.lab.znk.domain.User;
import pl.lab.znk.service.dto.ConsultationDTO;
import pl.lab.znk.service.dto.UserDTO;

import javax.inject.Inject;

@Component
public class ConsultationMapper {

    @Inject
    private UserMapper userMapper;


    public ConsultationDTO consultationToConsultationDTO(Consultation consultation) {
        if ( consultation == null ) {
            return null;
        }

        ConsultationDTO consultationDTO = new ConsultationDTO();

        consultationDTO.setTeacherLogin( consultationTeacherLogin( consultation ) );
        consultationDTO.setTeacherId( consultationTeacherId( consultation ) );
        consultationDTO.setId( consultation.getId() );
        consultationDTO.setDateTime( consultation.getDateTime().toString() );
        consultationDTO.setCancelled( consultation.isCancelled() );
        consultationDTO.setRegisteredStudents( userSetToUserDTOSet( consultation.getRegisteredStudents() ) );

        return consultationDTO;
    }


    public List<ConsultationDTO> consultationsToConsultationDTOs(List<Consultation> consultations) {
        if ( consultations == null ) {
            return null;
        }

        List<ConsultationDTO> list = new ArrayList<ConsultationDTO>();
        for ( Consultation consultation : consultations ) {
            list.add( consultationToConsultationDTO( consultation ) );
        }

        return list;
    }


    public Consultation consultationDTOToConsultation(ConsultationDTO consultationDTO) {
        if ( consultationDTO == null ) {
            return null;
        }

        Consultation consultation = new Consultation();

        consultation.setTeacher( userMapper.userFromId( consultationDTO.getTeacherId() ) );
        consultation.setId( consultationDTO.getId() );
        consultation.setDateTime(ZonedDateTime.now());
        consultation.setCancelled( consultationDTO.getCancelled() );
        consultation.setRegisteredStudents( userDTOSetToUserSet( consultationDTO.getRegisteredStudents() ) );

        return consultation;
    }


    public List<Consultation> consultationDTOsToConsultations(List<ConsultationDTO> consultationDTOs) {
        if ( consultationDTOs == null ) {
            return null;
        }

        List<Consultation> list = new ArrayList<Consultation>();
        for ( ConsultationDTO consultationDTO : consultationDTOs ) {
            list.add( consultationDTOToConsultation( consultationDTO ) );
        }

        return list;
    }

    private String consultationTeacherLogin(Consultation consultation) {

        if ( consultation == null ) {
            return null;
        }
        User teacher = consultation.getTeacher();
        if ( teacher == null ) {
            return null;
        }
        String login = teacher.getLogin();
        if ( login == null ) {
            return null;
        }
        return login;
    }

    private Long consultationTeacherId(Consultation consultation) {

        if ( consultation == null ) {
            return null;
        }
        User teacher = consultation.getTeacher();
        if ( teacher == null ) {
            return null;
        }
        Long id = teacher.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Set<UserDTO> userSetToUserDTOSet(Set<User> set) {
        if ( set == null ) {
            return null;
        }

        Set<UserDTO> set_ = new HashSet<UserDTO>();
        for ( User user : set ) {
            UserDTO userDTO = new UserDTO(user);
            set_.add( userDTO );
        }

        return set_;
    }

    protected Set<User> userDTOSetToUserSet(Set<UserDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<User> set_ = new HashSet<User>();
        for ( UserDTO userDTO : set ) {
            set_.add( userMapper.userDTOToUser( userDTO ) );
        }

        return set_;
    }
}
