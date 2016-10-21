package pl.lab.znk.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.lab.znk.domain.User;
import pl.lab.znk.repository.UserRepository;
import pl.lab.znk.web.rest.vm.LoginVm;

@RestController
public class AccountController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody LoginVm loginVm) {
        User user = userRepository.findByLogin(loginVm.getLogin());

        if (user != null && loginVm.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
