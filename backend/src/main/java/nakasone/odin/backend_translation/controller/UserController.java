package nakasone.odin.backend_translation.controller;

import nakasone.odin.backend_translation.exceptions.ResourceNotFoundException;
import nakasone.odin.backend_translation.model.User;
import nakasone.odin.backend_translation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userRepo.save(user);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id: " + id + " does not exist."));

        return ResponseEntity.ok(user);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userDetails){
        User userToUpdate = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id: " + id + " does not exist."));

        userToUpdate.setPassword(userDetails.getPassword());
        userToUpdate.setEmail(userDetails.getEmail());
        userToUpdate.setProfilePic(userDetails.getProfilePic());

        userRepo.save(userToUpdate);

        return ResponseEntity.ok(userToUpdate);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id){
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id: " + id + " does not exist."));

        userRepo.delete(user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}